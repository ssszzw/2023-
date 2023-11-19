package com.example20231016.RBAC_server.Login;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
