package com.skoy.bootcamp_microservices.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;

}
