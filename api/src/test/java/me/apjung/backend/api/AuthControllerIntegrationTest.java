package me.apjung.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.apjung.backend.IntegrationTest;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.concurrent.TimeUnit;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
class AuthControllerIntegrationTest extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입, 로그인 후 일정시간이 지나고 accessToken 만료로 재발급 요청, 로그인 만료 테스트")
    void authenticationScenarioTest() throws Exception {
        register().andExpect(status().isCreated());

        final var loginResponse = loginSuccess();
        var authResponseLogin = objectMapper.readValue(
                loginResponse, AuthResponse.Login.class);
        var accessToken = authResponseLogin.getAccessToken()
                .getToken();
        var accessTokenType = authResponseLogin.getAccessToken()
                .getType();
        final var refreshToken = authResponseLogin.getRefreshToken()
                .getToken();
        final var refreshTokenType = authResponseLogin.getRefreshToken()
                .getType();

        // first me
        me(accessToken, accessTokenType).andExpect(status().isOk());

        // after 2 seconds
        TimeUnit.SECONDS.sleep(2);

        // second me
        me(accessToken, accessTokenType).andExpect(status().isForbidden());

        // reissue access token
        final var reissueTokenResponse = reissueAccessTokenSuccess(refreshToken, refreshTokenType);
        final var authResponseTokenIssuance = objectMapper.readValue(
                reissueTokenResponse, AuthResponse.TokenIssuance.class);

        accessToken = authResponseTokenIssuance.getAccessToken()
                .getToken();
        accessTokenType = authResponseTokenIssuance.getAccessToken()
                .getType();

        // third me
        me(accessToken, accessTokenType).andExpect(status().isOk());

        // after 4 seconds
        TimeUnit.SECONDS.sleep(4);

        // fourth me
        me(accessToken, accessTokenType).andExpect(status().isForbidden());

        // reissue access token (failure because refresh_token expired)
        reissueAccessTokenFail(refreshToken, refreshTokenType).andExpect(status().isBadRequest());

        // Try login again.
    }

    private String reissueAccessTokenSuccess(String refreshToken, String refreshTokenType) throws Exception {
        final var tokenIssuanceRequest = new AuthRequest.TokenIssuance();
        tokenIssuanceRequest.setGrantType("refresh_token");
        tokenIssuanceRequest.setRefreshToken(refreshTokenType + " " + refreshToken);

        return mockMvc.perform(
                post("/auth/token")
                        .content(objectMapper.writeValueAsString(tokenIssuanceRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private ResultActions reissueAccessTokenFail(String refreshToken, String refreshTokenType) throws Exception {
        final var tokenIssuanceRequest = new AuthRequest.TokenIssuance();
        tokenIssuanceRequest.setGrantType("refresh_token");
        tokenIssuanceRequest.setRefreshToken(refreshTokenType + " " + refreshToken);

        return mockMvc.perform(
                post("/auth/token")
                        .content(objectMapper.writeValueAsString(tokenIssuanceRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions me(String token, String tokenType) throws Exception {
        return mockMvc.perform(
                get("/auth/me")
                        .header("Authorization", tokenType + " " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private String loginSuccess() throws Exception {
        final var loginRequest = new AuthRequest.Login();
        loginRequest.setEmail("test@apjung.me");
        loginRequest.setPassword("test1234");

        return mockMvc.perform(
                post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private ResultActions register() throws Exception {
        final var registerRequest = new AuthRequest.Register();
        registerRequest.setEmail("test@apjung.me");
        registerRequest.setPassword("test1234");
        registerRequest.setName("testName");
        registerRequest.setMobile("01012345678");

        return mockMvc.perform(
                post("/auth/register")
                        .content(objectMapper.writeValueAsString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print());
    }
}
