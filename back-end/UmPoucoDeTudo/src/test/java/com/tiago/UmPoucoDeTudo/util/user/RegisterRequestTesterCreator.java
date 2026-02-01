package com.tiago.UmPoucoDeTudo.util.user;

import com.tiago.UmPoucoDeTudo.requests.AuthRequests.RegisterRequest;

public class RegisterRequestTesterCreator {

    private final static String DEFAULT_EMAIL = "teste@teste.com";
    private final static String DEFAULT_NAME = "teste";
    private final static String DEFAULT_PASSWORD = "teste@teste.com";

    static public RegisterRequest createRegisterRequest() {

        return RegisterRequest.builder()
                .email(DEFAULT_EMAIL)
                .name(DEFAULT_NAME)
                .password(DEFAULT_PASSWORD)
                .build();

    }

}
