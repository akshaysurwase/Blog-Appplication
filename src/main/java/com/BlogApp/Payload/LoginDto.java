package com.BlogApp.Payload;

import lombok.Data;

@Data
public class LoginDto {

    private String usernameOrEmail;

    private String password;

    @Override
    public String toString() {
        return "LoginDto{" +
                "usernameOrEmail='" + usernameOrEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
