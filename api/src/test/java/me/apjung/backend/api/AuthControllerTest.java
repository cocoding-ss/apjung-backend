package me.apjung.backend.api;

import me.apjung.backend.mock.MockUser;
import me.apjung.backend.MvcTest;
import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.service.auth.AuthServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static me.apjung.backend.util.ApiDocumentUtils.getDocumentRequest;
import static me.apjung.backend.util.ApiDocumentUtils.getDocumentResponse;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest extends MvcTest {
    @Mock
    private AuthServiceImpl authService;

    @Test
    @Disabled
    @DisplayName("회원가입 성공 테스트")
    public void registerSuccessTest() throws Exception {
        // given
        // TODO DB에 구애받지 않도록..? email 중복 피하기
        Map<String, Object> request = new HashMap<>();
        request.put("email", "apjungbackendtest1@apjung.me");
        request.put("password", "test1234");
        request.put("name", "testName");
        request.put("mobile", "01012345678");

        // when
        ResultActions results = mockMvc.perform(
                post("/auth/register")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        );

        // then
        results.andExpect(status().isCreated())
                .andDo(document("auth-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("가입할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("가입할 비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("가입할 이름"),
                                fieldWithPath("mobile").type(JsonFieldType.STRING).description("가입할 전화번호")
                        )));
    }

    @Test
    @DisplayName("회원가입 실패 테스트(중복된 이메일 입력)")
    public void registerFailByDuplicatedEmailTest() throws Exception {
        // given
        createNewUser(MockUser.builder().name("testName").email("testuser@gmail.com").password("test1234").mobile("01012345678").build());
        final var request = new AuthRequest.Register("testuser@gmail.com", "test1234",
                "testName", "01012345678");

        given(authService.register(request))
                .willThrow(DuplicatedEmailException.class);

        // when
        ResultActions results = mockMvc.perform(
                post("/auth/register")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        results.andExpect(status().isBadRequest())
                .andExpect(r -> assertTrue(r.getResolvedException() instanceof DuplicatedEmailException))
                .andDo(document("auth-register-failure",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("가입할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("가입할 비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("가입할 이름"),
                                fieldWithPath("mobile").type(JsonFieldType.STRING).description("가입할 전화번호")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("회원가입 실패 메시지")
                        )));
    }

    @Test
    public void Login_test() throws Exception {
        // given
        String password = "smvlaml1234";
        User user = createNewUser(MockUser.builder().password(password).build());

        Map<String, Object> request = new HashMap<>();
        request.put("email", user.getEmail());
        request.put("password", password);

        // when
        ResultActions results = mockMvc.perform(
                post("/auth/login")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        );

        //then
        results.andExpect(status().isOk())
                .andDo(document("auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인할 사용자 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 사용자 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("token").type(JsonFieldType.STRING).description("JWT 액세스 토큰"),
                                fieldWithPath("tokenType").type(JsonFieldType.STRING).description("토큰 타입 (Bearer)")
                        )
                    ));
    }

    @Test
    public void Me_Test() throws Exception {
        // given
        User user = createNewUser(MockUser.builder().build());
        String accessToken = getJwtAccessToken(user);

        // when
        ResultActions results = mockMvc.perform(
                get("/auth/me")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
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
