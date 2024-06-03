package dev.wallet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class LoadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoad() throws Exception {

        String requestPayload = "{"
        + "\"userId\": \"2226e2f9-ih09-46a8-958f-d659880asdf0\","
        + "\"transactionAmount\": {"
        + "\"amount\": \"100\","
        + "\"currency\": \"USD\","
        + "\"debitOrCredit\": \"CREDIT\""
        + "}"
        + "}";
        mockMvc.perform(put("/load/testMessageId")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.messageId").exists())
            .andExpect(jsonPath("$.userId").exists())
            .andExpect(jsonPath("$.balance.amount").exists())
            .andExpect(jsonPath("$.balance.currency").value("USD"))
            .andExpect(jsonPath("$.balance.debitOrCredit").value("CREDIT"));
    }

    @Test
    public void testLoadServerError() throws Exception {

        String requestPayload = "{"
        + "\"userId\": \"2226e2f9-ih09-46a8-958f-d659880asdf0\","
        + "\"transactionAmount\": {"
        + "\"amount\": \"aaa\","
        + "\"currency\": \"USD\","
        + "\"debitOrCredit\": \"CREDIT\""
        + "}"
        + "}";
        mockMvc.perform(put("/load/testMessageId2")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestPayload))
            .andExpect(status().isInternalServerError());
    }
}
