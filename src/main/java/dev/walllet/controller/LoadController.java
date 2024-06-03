package dev.wallet.controller;

import dev.wallet.model.Amount;
import dev.wallet.model.DebitCredit;
import dev.wallet.model.LoadRequest;
import dev.wallet.model.LoadResponse;
import dev.wallet.model.ServerError;
import dev.wallet.data.UserBalance;
import dev.wallet.model.Error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/load/{messageId}")
public class LoadController {

    private UserBalance userBalance;

    public LoadController(UserBalance userBalance) {
        this.userBalance = userBalance;
    }

    // using messageId as key to store processed messages to ensure idempotency
    private Map<String, LoadResponse> processedMessages = new HashMap<>();

    @PutMapping
    public ResponseEntity<?> addFundsToAccount(@PathVariable String messageId, @RequestBody LoadRequest loadRequest) {

        if (processedMessages.containsKey(messageId)) {
            return ResponseEntity.status(201).body(processedMessages.get(messageId));
        }

        try {
            Amount userAmount = userBalance.getUserBalances().getOrDefault(loadRequest.getUserId(),
                    new Amount("0", loadRequest.getTransactionAmount().getCurrency(), DebitCredit.CREDIT));
            Amount newAmount = userAmount.add(loadRequest.getTransactionAmount());
            newAmount.setDebitOrCredit(DebitCredit.CREDIT);
            userBalance.getUserBalances().put(loadRequest.getUserId(), newAmount);

            LoadResponse loadResponse = new LoadResponse(loadRequest.getUserId(), messageId, newAmount);
            processedMessages.put(messageId, loadResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(loadResponse);
        } catch (Exception e) {
            ServerError serverError = new ServerError(new Error("500", "An error occurred while adding funds."));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverError);
        }
    }

}
