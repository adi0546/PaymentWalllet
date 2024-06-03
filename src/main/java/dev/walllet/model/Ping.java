package dev.wallet.model;

import java.time.LocalDateTime;

public class Ping {
    private LocalDateTime serverTime;

    public Ping() {
        this.serverTime = LocalDateTime.now();
    }

    public LocalDateTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(LocalDateTime serverTime) {
        this.serverTime = serverTime;
    }
}