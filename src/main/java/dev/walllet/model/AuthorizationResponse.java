package dev.wallet.model;

public class AuthorizationResponse {
    private String userId;
    private String messageId;
    private ResponseCode responseCode;
    private Amount balance;

    public AuthorizationResponse() {
    }

    public AuthorizationResponse(String userId, String messageId, ResponseCode responseCode, Amount balance) {
        this.userId = userId;
        this.messageId = messageId;
        this.responseCode = responseCode;
        this.balance = balance;
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

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
}