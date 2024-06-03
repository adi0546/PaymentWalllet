package dev.wallet.model;

public class AuthorizationRequest {
    private String userId;
    private String messageId;
    private Amount transactionAmount;

    public AuthorizationRequest(String userId, String messageId, Amount transactionAmount) {
        this.userId = userId;
        this.messageId = messageId;
        this.transactionAmount = transactionAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}