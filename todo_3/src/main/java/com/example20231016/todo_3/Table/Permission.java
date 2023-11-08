package com.example20231016.todo_3.Table;

import lombok.Data;

@Data
public class Permission {
    private int permission_id;
    private String permission_name;
    private String description;
    private String module;
    private Status status;

    public Permission(int permission_id, String permission_name, String description, String module, Status status) {
        this.permission_id = permission_id;
        this.permission_name = permission_name;
        this.description = description;
        this.module = module;
        this.status = status;
    }
}
