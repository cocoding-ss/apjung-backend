package me.apjung.backend.api;

import me.apjung.backend.Mock.MockUser;
import me.apjung.backend.MvcTest;
import me.apjung.backend.domain.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static me.apjung.backend.util.ApiDocumentUtils.getDocumentRequest;
import static me.apjung.backend.util.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShopControllerTest extends MvcTest {

    @Test
    public void shopCreateTest() throws Exception {
        String token = getJwtAccessToken();

        // given
        User user = createNewUser(MockUser.builder().build());
        String accessToken = getJwtAccessToken(user);

        // when
        ResultActions results = mockMvc.perform(
                post("/shop")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.MULTIPART_FORM_DATA)
        );

        //then
        results.andExpect(status().isOk())
                .andDo(document("auth-me",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access Token")
                        ),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("emailAuth").type(JsonFieldType.BOOLEAN).description("이메일 인증 여부"),
                                fieldWithPath("mobile").type(JsonFieldType.STRING).description("사용자 전화번호"),
                                fieldWithPath("roles").type(JsonFieldType.ARRAY).description("사용자 권한")
                        )
                ));
    }
}
