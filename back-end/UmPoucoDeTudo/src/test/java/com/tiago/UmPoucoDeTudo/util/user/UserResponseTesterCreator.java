package com.tiago.UmPoucoDeTudo.util.user;

import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.responses.UserResponse;

public class UserResponseTesterCreator {

    static public UserResponse convertToUserResponse(User user) {
        return new UserResponse(user.getName());
    }

}
