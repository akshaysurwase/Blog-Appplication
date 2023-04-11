package com.BlogApp.Payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private  String username;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "SignUpDto{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
