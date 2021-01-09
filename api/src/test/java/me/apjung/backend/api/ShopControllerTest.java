package me.apjung.backend.api;

import me.apjung.backend.api.advisor.AuthExceptionHandler;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.dto.vo.Thumbnail;
import me.apjung.backend.mock.WithMockCustomUser;
import me.apjung.backend.MvcTest;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.service.shop.ShopService;
import me.apjung.backend.service.shop.search.ShopSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static me.apjung.backend.util.ApiDocumentUtils.getDocumentRequest;
import static me.apjung.backend.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
public class ShopControllerTest extends MvcTest {
    @MockBean ShopService shopService;
    @MockBean ShopSearchService shopSearchService;
    @MockBean AuthExceptionHandler authExceptionHandler;

    @Test
    @WithMockCustomUser
    public void shopCreateTest() throws Exception {
        // given
        given(shopService.create(any())).willReturn(ShopResponse.Create.builder().id(1L).build());

        InputStream is = new ClassPathResource("mock/images/440x440.jpg").getInputStream();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("thumbnail", "mock_thumbnail.jpg", "image/jpg", is.readAllBytes());

        // when
        ResultActions results = mockMvc.perform(
                fileUpload("/shop")
                        .file(mockMultipartFile)
                        .param("name", "테스트 쇼핑몰")
                        .param("overview", "테스트로만들어본 쇼핑몰입니다")
                        .param("url", "https://www.naver.com")
                        .param("tags", "tag1", "tag2", "tag3")
                        .header("Authorization", "Bearer accessToken")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        //then
        results.andExpect(status().isCreated())
                .andDo(document("shop-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access Token")
                        ),
                        requestParts(
                                partWithName("thumbnail").description("썸네일 이미지 파일")
                        ),
                        requestParameters(
                                parameterWithName("name").description("쇼핑몰 이름"),
                                parameterWithName("overview").description("쇼핑몰 소개"),
                                parameterWithName("url").description("쇼핑몰 Url"),
                                parameterWithName("tags").description("쇼핑몰 태그")

                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("쇼핑몰 아이디")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    public void shopGetTest() throws Exception {
        // given
        given(shopService.get(anyLong(), any(User.class))).willReturn(ShopResponse.GET.builder()
                .id(1L)
                .name("테스트 쇼핑몰")
                .overview("쇼핑몰의 간단한 소개")
                .thumbnail(Thumbnail.from(File.builder()
                        .id(1L)
                        .name("test.jpg")
                        .extension("jpg")
                        .height(440)
                        .width(440)
                        .size(0L)
                        .isImage(true)
                        .originalExtension("jpg")
                        .originalName("test.jpg")
                        .publicUrl("http://loremflickr.com/440/440")
                        .prefix("mock/test")
                        .build()
                ))
                .url("https://www.naver.com")
                .build()
        );

        // when
        ResultActions results = mockMvc.perform(
                get("/shop/{shop_id}", 1L)
                    .header("Authorization", "Bearer accessToken")
        );

        // then
        results.andExpect(status().isOk())
                .andDo(document("shop-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("shop_id").description("쇼핑몰 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("쇼핑몰 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("쇼핑몰 이름"),
                                fieldWithPath("overview").type(JsonFieldType.STRING).description("쇼핑몰 소개"),
                                fieldWithPath("url").type(JsonFieldType.STRING).description("쇼핑몰 Url"),
                                fieldWithPath("thumbnail").type(JsonFieldType.OBJECT).description("쇼핑몰 썸네일"),
                                fieldWithPath("thumbnail.publicUrl").description("쇼핑몰 썸네일 url"),
                                fieldWithPath("thumbnail.prefix").description("쇼핑몰 썸네일 파일 prefix"),
                                fieldWithPath("thumbnail.name").description("쇼핑몰 썸네일 파일 이름"),
                                fieldWithPath("thumbnail.extension").description("쇼핑몰 썸네일 파일 확장자"),
                                fieldWithPath("thumbnail.originalName").description("쇼핑몰 썸네일 파일 원본 이름"),
                                fieldWithPath("thumbnail.originalExtension").description("쇼핑몰 썸네일 파일 원본 확장자"),
                                fieldWithPath("thumbnail.width").description("쇼핑몰 썸네일 이미지 가로 길이"),
                                fieldWithPath("thumbnail.height").description("쇼핑몰 썸네일 이미지 세로 길이"),
                                fieldWithPath("thumbnail.size").description("쇼핑몰 썸네일 이미지 파일 크기")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("쇼핑몰 검색 api 테스트")
    public void shopSearchTest() throws Exception {
        // given
        given(shopSearchService.search(any()))
                .willReturn(List.of(
                        ShopResponse.SearchResult.builder()
                                .id(1L)
                                .name("쇼핑몰 test")
                                .overview("이 쇼핑몰은 말입니다...")
                                .url("www.apjung.xyz/shop/1")
                                .pv(0L)
                                .uv(0L)
                                .build(),
                        ShopResponse.SearchResult.builder()
                                .id(7L)
                                .name("무명의 쇼핑몰")
                                .overview("test라는 태그도 있어요~")
                                .url("www.apjung.xyz/shop/7")
                                .pv(8L)
                                .uv(4L)
                                .thumbnailUrl("http://loremflickr.com/440/440")
                                .build(),
                        ShopResponse.SearchResult.builder()
                                .id(3L)
                                .name("test 쇼핑몰")
                                .overview("이쁜 옷 많아요")
                                .url("www.apjung.xyz/shop/3")
                                .pv(6L)
                                .uv(6L)
                                .build()));

        // when
        ResultActions results = mockMvc.perform(
                get("/shop/search")
                        .param("keyword", "test")
                        .param("orderType", "NAME")
                        .param("pageSize", "10")
                        .param("pageNum", "1")
                        .header("Authorization", "Bearer accessToken"));

        results.andExpect(status().isOk())
                .andDo(document("shop-search",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("pageNum").optional().description("쇼핑몰 리스트 페이지 번호"),
                                parameterWithName("pageSize").optional().description("한 번에 가져올 쇼핑몰 리스트 크기"),
                                parameterWithName("orderType").optional().description("정렬 기준[popularity, name, recently(기본값)]"),
                                parameterWithName("keyword").description("검색 키워드")),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("쇼핑몰 리스트"),
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("쇼핑몰 아이디"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("쇼핑몰 이름"),
                                fieldWithPath("[].overview").type(JsonFieldType.STRING).description("쇼핑몰 소개"),
                                fieldWithPath("[].url").type(JsonFieldType.STRING).description("쇼핑몰 Url"),
                                fieldWithPath("[].pv").type(JsonFieldType.NUMBER).description("쇼핑몰 뷰어수"),
                                fieldWithPath("[].uv").type(JsonFieldType.NUMBER).description("쇼핑몰 단일 뷰어수(1일)"),
                                fieldWithPath("[].thumbnailUrl").optional().type(JsonFieldType.STRING).description("쇼핑몰 썸네일 url")
                        )));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("쇼핑몰 인증 등록")
    public void shopSafeTest() throws Exception {
        // given
        given(shopService.safe(anyLong(), any())).willReturn(
            ShopResponse.Safe.builder()
                .id(1L)
                .safeAt(LocalDateTime.now())
                .safeLevel(ShopSafeLevel.DANGEROUS)
                .build()
        );

        HashMap<String, Object> request = new HashMap<>();
        request.put("safeLevel", "DANGEROUS");

        // when
        ResultActions results = mockMvc.perform(
                put("/shop/{shop_id}/safe", 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer accessToken")
        );

        // then
        results.andExpect(status().isOk())
                .andDo(document("shop-safe",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("safeLevel").description("설정할 안전 레벨 (SAFE, NORMAL, DANGEROUS, FAKE)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("변경된 쇼핑몰 ID"),
                                fieldWithPath("safeAt").description("안전 레벨 변경 시각"),
                                fieldWithPath("safeLevel").description("변경된 안전 레벨")
                        )
                        ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("쇼핑몰 즐겨찾기 등록")
    public void createShopPinTest() throws Exception {
        // given
        given(shopService.createPin(any(), any())).willReturn(
            ShopResponse.CreatePin.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .build()
        );

        // when
        ResultActions results = mockMvc.perform(
                post("/shop/{shop_id}/pin", 1L)
        );

        // then
        results.andExpect(status().isCreated())
                .andDo(document("shop-create-pin",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("shop_id").description("쇼핑몰 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("즐겨찾기한 쇼핑몰 아이디"),
                                fieldWithPath("createdAt").description("즐겨찾기한 시각")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("쇼핑몰 즐겨찾기 해제(삭제)")
    public void deleteShopPinTest() throws Exception {
        // given
        given(shopService.deletePin(any(), any())).willReturn(
            ShopResponse.DeletePin.builder()
                .id(1L)
                .build()
        );

        // when
        ResultActions results = mockMvc.perform(
                delete("/shop/{shop_id}/pin", 1L)
        );

        // then
        results.andExpect(status().isOk())
                .andDo(document("shop-delete-pin",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("shop_id").description("쇼핑몰 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").description("즐겨찾기 해제한 쇼핑몰 아이디")
                        )
                ));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("즐겨찾기한 쇼핑몰 목록 가져오기")
    public void getMyPinnedShopsTest() throws Exception {
        // given
        given(shopService.getMyPinnedShops(any())).willReturn(
            List.of(
                    ShopResponse.GET.builder()
                            .id(1L)
                            .name("테스트 쇼핑몰")
                            .overview("쇼핑몰의 간단한 소개")
                            .thumbnail(Thumbnail.from(File.builder()
                                    .id(1L)
                                    .name("test.jpg")
                                    .extension("jpg")
                                    .height(440)
                                    .width(440)
                                    .size(0L)
                                    .isImage(true)
                                    .originalExtension("jpg")
                                    .originalName("test.jpg")
                                    .publicUrl("http://loremflickr.com/440/440")
                                    .prefix("mock/test")
                                    .build()
                            ))
                            .url("https://www.naver.com")
                            .build()
            )
        );

        // when
        ResultActions results = mockMvc.perform(
                get("/shop/pinned")
        );

        // then
        results.andExpect(status().isOk())
                .andDo(document("shop-get-pinned",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("쇼핑몰 아이디"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("쇼핑몰 이름"),
                                fieldWithPath("[].overview").type(JsonFieldType.STRING).description("쇼핑몰 이름"),
                                fieldWithPath("[].url").type(JsonFieldType.STRING).description("쇼핑몰 이름"),
                                fieldWithPath("[].thumbnail").type(JsonFieldType.OBJECT).description("쇼핑몰 썸네일"),
                                fieldWithPath("[].thumbnail.publicUrl").description("쇼핑몰 썸네일 url"),
                                fieldWithPath("[].thumbnail.prefix").description("쇼핑몰 썸네일 파일 prefix"),
                                fieldWithPath("[].thumbnail.name").description("쇼핑몰 썸네일 파일 이름"),
                                fieldWithPath("[].thumbnail.extension").description("쇼핑몰 썸네일 파일 확장자"),
                                fieldWithPath("[].thumbnail.originalName").description("쇼핑몰 썸네일 파일 원본 이름"),
                                fieldWithPath("[].thumbnail.originalExtension").description("쇼핑몰 썸네일 파일 원본 확장자"),
                                fieldWithPath("[].thumbnail.width").description("쇼핑몰 썸네일 이미지 가로 길이"),
                                fieldWithPath("[].thumbnail.height").description("쇼핑몰 썸네일 이미지 세로 길이"),
                                fieldWithPath("[].thumbnail.size").description("쇼핑몰 썸네일 이미지 파일 크기")
                        )
                    ));
    }
}
