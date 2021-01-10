package me.apjung.backend.api;

import me.apjung.backend.component.randomstringbuilder.RandomStringBuilder;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.MvcTest;
import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.mock.WithMockCustomUser;
import me.apjung.backend.service.auth.AuthService;
import me.apjung.backend.service.security.CustomUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@WebMvcTest(AuthController.class)
class AuthControllerTest extends MvcTest {
    @MockBean
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void registerTest() throws Exception {
        // given
        User user = User.builder()
                .email("labyu2020@naver.com")
                .password(passwordEncoder.encode("test1234"))
                .name("testname")
                .mobile("01012345678")
                .isEmailAuth(false)
                .emailAuthToken(Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow())
                .build();
        user.setId(1L);

        given(authService.register(any(AuthRequest.Register.class))).willReturn(user);

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
                        .characterEncoding("utf-8"));

        // then
        results.andExpect(status().isCreated())
                .andDo(document("auth-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("가입할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("가입할 비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("가입할 이름"),
                                fieldWithPath("mobile").type(JsonFieldType.STRING).description("가입할 전화번호"))));
    }

    @Test
    @DisplayName("회원가입 실패 테스트(중복된 이메일 입력)")
    void registerFailureWithDuplicatedEmailTest() throws Exception {
        // given
        given(authService.register(any(AuthRequest.Register.class)))
                .willThrow(DuplicatedEmailException.class);

        final var request = new AuthRequest.Register("testuser@gmail.com", "test1234",
                "testName", "01012345678");

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
    @DisplayName("로그인 성공")
    void loginTest() throws Exception {
        // given
        final var request = new AuthRequest.Login();
        request.setEmail("testemail@test.com");
        request.setPassword("testPassword");
        final var response = new AuthResponse.Login(
                AuthResponse.Token.from("dfsgjnesrgersg.sgnkergergerg.sregerg", "Bearer"),
                AuthResponse.Token.from("afhjkdsfhjkadf.ioqreiojsdfio.qwoieuw", "Bearer"));

        given(authService.jwtLogin(any(AuthRequest.Login.class)))
                .willReturn(response);

        // when
        final var results = mockMvc.perform(
                post("/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        results.andExpect(status().isOk())
                .andDo(document("auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인할 사용자 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 사용자 비밀번호")),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.OBJECT).description("JWT 엑세스 토큰"),
                                fieldWithPath("accessToken.token").type(JsonFieldType.STRING).description("토큰"),
                                fieldWithPath("accessToken.type").type(JsonFieldType.STRING).description("토큰 타입 (Bearer)"),
                                fieldWithPath("refreshToken").type(JsonFieldType.OBJECT).description("JWT 리프레시 토큰"),
                                fieldWithPath("refreshToken.token").type(JsonFieldType.STRING).description("토큰"),
                                fieldWithPath("refreshToken.type").type(JsonFieldType.STRING).description("토큰 타입 (Bearer)"))));
    }

    @Test
    @DisplayName("refreshToken을 통한 accessToken 재발급")
    void reissueAccessTokenTest() throws Exception {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("refresh_token");
        request.setRefreshToken("Bearer afhjkdsfhjkadf.ioqreiojsdfio.qwoieuw");

        final var response = new AuthResponse.TokenIssuance(
                AuthResponse.Token.from("dfsgjnesrgersg.sgnkergergerg.sregerg", "Bearer"));

        given(authService.reissueAccessToken(request))
                .willReturn(response);

        // when
        final var results = mockMvc.perform(
                post("/auth/token")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        results.andExpect(status().isOk())
                .andDo(document("auth-reissue-access-token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("grantType").type(JsonFieldType.STRING).description("인증 유형('refresh_token'으로 고정)"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.OBJECT).description("JWT 엑세스 토큰"),
                                fieldWithPath("accessToken.token").type(JsonFieldType.STRING).description("토큰"),
                                fieldWithPath("accessToken.type").type(JsonFieldType.STRING).description("토큰 타입 (Bearer)"))));
    }

    @Test
    @DisplayName("accessToken을 통한 본인 확인")
    @WithMockCustomUser
    void meTest() throws Exception {
        // given
        AuthResponse.Me response = new AuthResponse.Me();
        response.setEmail("testEmail");
        response.setEmailAuth(true);
        response.setMobile("01012341234");
        response.setName("testName");
        response.setRoles(List.of("USER", "ADMIN"));

        given(authService.me(any(CustomUserDetails.class))).willReturn(response);

        // when
        ResultActions results = mockMvc.perform(
                get("/auth/me")
                        .header("Authorization", "Bearer accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        results.andExpect(status().isOk())
                .andDo(document("auth-me",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 Access Token")),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("emailAuth").type(JsonFieldType.BOOLEAN).description("이메일 인증 여부"),
                                fieldWithPath("mobile").type(JsonFieldType.STRING).description("사용자 전화번호"),
                                fieldWithPath("roles").type(JsonFieldType.ARRAY).description("사용자 권한"))));
    }
}
