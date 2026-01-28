package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.User;

import java.util.ArrayList;

public class UserTesterCreator {

    private static final String DEFAULT_EMAIL = "teste@teste";
    private static final String DEFAULT_NAME = "teste";
    private static final String DEFAULT_PASSWORD = "password";

    static public User createUser() {
        return User.builder()
                .email(DEFAULT_EMAIL)
                .name(DEFAULT_NAME)
                .tags(new ArrayList<>())
                .password(DEFAULT_PASSWORD)
                .build();
    }

}
