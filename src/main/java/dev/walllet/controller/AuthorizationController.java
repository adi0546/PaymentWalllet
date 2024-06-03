package dev.wallet.controller;

import dev.wallet.model.*;
import dev.wallet.data.UserBalance;

import java.util.HashMap;
import java.util.Map;

import dev.wallet.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {
    private UserBalance userBalance;
    private Map<String, AuthorizationResponse> processedMessages = new HashMap<>();

    public AuthorizationController(UserBalance userBalance) {
        this.userBalance = userBalance;
    }

    @PutMapping("/authorization/{messageId}")
    public ResponseEntity<?> authorizeTransaction(@PathVariable String messageId,
            @RequestBody AuthorizationRequest authorizationRequest) {

        // Check if the message has already been processed to ensure idempotency
        if (processedMessages.containsKey(messageId)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(processedMessages.get(messageId));
        }

        try {

            String userId = authorizationRequest.getUserId();
            Amount transactionAmount = (authorizationRequest.getTransactionAmount());

            Amount currentBalance = userBalance.getUserBalances().get(userId);

            if(currentBalance == null){
                ServerError serverError = new ServerError(new Error("404", "User not found."));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(serverError);
            }

            if (currentBalance.compareTo(transactionAmount) < 0) {
                AuthorizationResponse response = new AuthorizationResponse();
                response.setUserId(userId);
                response.setMessageId(messageId);
                response.setResponseCode(ResponseCode.DECLINED);
                response.setBalance(currentBalance);
                processedMessages.put(messageId, response);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {

                Amount userAmount = userBalance.getUserBalances().get(userId);
                Amount newBalance = userAmount.subtract(transactionAmount);
                newBalance.setDebitOrCredit(DebitCredit.DEBIT);

                userBalance.getUserBalances().put(authorizationRequest.getUserId(), newBalance);

                AuthorizationResponse response = new AuthorizationResponse();
                response.setUserId(userId);
                response.setMessageId(messageId);
                response.setResponseCode(ResponseCode.APPROVED);
                response.setBalance(newBalance);
                processedMessages.put(messageId, response);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (Exception e) {
            ServerError serverError = new ServerError(new Error("500", "An error occurred while deducting funds."));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverError);
        }
    }
}