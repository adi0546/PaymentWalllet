package dev.wallet.model;

public class ServerError {
    private Error error;
    
    public ServerError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}