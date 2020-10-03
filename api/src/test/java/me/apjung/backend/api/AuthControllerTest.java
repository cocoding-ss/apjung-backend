package me.apjung.backend.api;

import me.apjung.backend.MvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static me.apjung.backend.util.ApiDocumentUtils.getDocumentRequest;
import static me.apjung.backend.util.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends MvcTest {

    @Test
    public void 회원가입_테스트() throws Exception {
        // given
        Map<String, Object> request = new HashMap<>();
        request.put("email", "test@test.com");
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

        results.andExpect(status().isCreated())
                .andDo(document("auth-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("가입할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("가입할 비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("가입할 이름"),
                                fieldWithPath("mobile").type(JsonFieldType.STRING).description("가입할 전화번호")
                        )
                        ));
    }
}
