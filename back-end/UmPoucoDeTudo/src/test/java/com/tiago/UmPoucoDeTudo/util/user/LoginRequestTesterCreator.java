package com.tiago.UmPoucoDeTudo.util.user;

import com.tiago.UmPoucoDeTudo.requests.AuthRequests.LoginRequest;

public class LoginRequestTesterCreator {
    private final static String DEFAULT_EMAIL = "teste@teste.com";
    private final static String DEFAULT_PASSWORD = "teste@teste.com";

    static public LoginRequest createLoginRequest() {

        return LoginRequest.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .build();

    }
}
