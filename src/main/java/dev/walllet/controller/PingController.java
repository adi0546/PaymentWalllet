package dev.wallet.controller;

import dev.wallet.model.Ping;
import dev.wallet.model.ServerError;
import dev.wallet.model.Error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        try {
            Ping ping = new Ping();
            return ResponseEntity.ok(ping);
        } catch (Exception e) {
            ServerError serverError = new ServerError(
                    new Error("500", "An error occurred while processing the request."));
            return ResponseEntity.status(500).body(serverError);
        }
    }
}