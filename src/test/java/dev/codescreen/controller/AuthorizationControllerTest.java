package dev.wallet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.wallet.data.UserBalance;
import dev.wallet.model.Amount;
import dev.wallet.model.DebitCredit;

import org.springframework.http.MediaType;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBalance mockUserBalance;

    @Test
    public void testAuthorizationApprove() throws Exception {

        String userId = "testUserId2";
        Amount amt = new Amount("100", "USD", DebitCredit.CREDIT);
        Map<String, Amount> mockBalance = new HashMap<>();
        mockBalance.put(userId, amt);
        Mockito.when(mockUserBalance.getUserBalances()).thenReturn(mockBalance);

        String requestPayload = "{"
        + "\"userId\": \"testUserId2\","
        + "\"transactionAmount\": {"
        + "\"amount\": \"10\","
        + "\"currency\": \"USD\","
        + "\"debitOrCredit\": \"DEBIT\""
        + "}"
        + "}";
        mockMvc.perform(put("/authorization/testMessageId")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.messageId").exists())
            .andExpect(jsonPath("$.userId").exists())
            .andExpect(jsonPath("$.responseCode").value("APPROVED"))
            .andExpect(jsonPath("$.balance.amount").exists())
            .andExpect(jsonPath("$.balance.currency").value("USD"))
            .andExpect(jsonPath("$.balance.debitOrCredit").value("DEBIT"));
    }

    @Test
    public void testAuthorizationDecline() throws Exception {
        String userId = "testUserId1";
        Amount amt = new Amount("10", "USD", DebitCredit.CREDIT);
        Map<String, Amount> mockBalance = new HashMap<>();
        mockBalance.put(userId, amt);
        Mockito.when(mockUserBalance.getUserBalances()).thenReturn(mockBalance);

        String requestPayload = "{"
        + "\"userId\": \"testUserId1\","
        + "\"transactionAmount\": {"
        + "\"amount\": \"100\","
        + "\"currency\": \"USD\","
        + "\"debitOrCredit\": \"DEBIT\""
        + "}"
        + "}";
        mockMvc.perform(put("/authorization/testMessageId2")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.messageId").exists())
            .andExpect(jsonPath("$.userId").exists())
            .andExpect(jsonPath("$.responseCode").value("DECLINED"))
            .andExpect(jsonPath("$.balance.amount").exists());
    }


    @Test
    public void testAuthServerError() throws Exception {

        String userId = "testUserId";
        Amount amt = new Amount("10", "USD", DebitCredit.CREDIT);
        Map<String, Amount> mockBalance = new HashMap<>();
        mockBalance.put(userId, amt);
        Mockito.when(mockUserBalance.getUserBalances()).thenReturn(mockBalance);

        String requestPayload = "{"
        + "\"userId\": \"testUserId\","
        + "\"transactionAmount\": {"
        + "\"amount\": \"aaa\","
        + "\"currency\": \"USD\","
        + "\"debitOrCredit\": \"CREDIT\""
        + "}"
        + "}";
        mockMvc.perform(put("/authorization/testMessageId3")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestPayload))
            .andExpect(status().isInternalServerError());
    }
}
