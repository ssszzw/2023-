package com.example20231016.RBAC_server.Table;

import lombok.Data;

@Data
public class Permission {
    private int permission_id;
    private int parent_id;
    private String permission_name;
    private String path;
    private int order_num;
    private String description;
    private Status status;
    private String create_time;
    private String update_time;


    public Permission(int permission_id, int parent_id, String permission_name, String path, int order_num, String description, Status status, String create_time, String update_time) {
        this.permission_id = permission_id;
        this.parent_id = parent_id;
        this.permission_name = permission_name;
        this.path = path;
        this.order_num = order_num;
        this.description = description;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;
    }
    public Permission(int permission_id) {
        this.permission_id = permission_id;
        this.parent_id = 0;
        this.permission_name = "";
        this.path = "";
        this.order_num = 0;
        this.description = "";
        this.status = Status.ACTIVE;
        this.create_time = "";
        this.update_time = "";
    }

}
