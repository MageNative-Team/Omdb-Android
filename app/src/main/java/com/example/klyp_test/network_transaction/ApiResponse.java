package com.example.klyp_test.network_transaction;

public interface ApiResponse {
    void onResponse(String response);
    void onError(String error);
}
