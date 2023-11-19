package com.example20231016.RBAC_server.Table;

import lombok.Data;

@Data
public class User {
    private long id;
    private String name;
    private String gender;
    private String username;
    private String password;
    private String phone;

    public User(long id, String name,String gender,String username, String password,String phone) {
        this.id = id;
        this.name=name;
        this.gender=gender;
        this.username = username;
        this.password = password;
        this.phone=phone;
    }

}
