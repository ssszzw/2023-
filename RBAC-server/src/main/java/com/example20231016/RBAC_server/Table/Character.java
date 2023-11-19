package com.example20231016.RBAC_server.Table;

import lombok.Data;


@Data
public class Character {
    private long character_id;
    private String character_name;
    private String character_type;
//    @Enumerated(EnumType.STRING)
    private Status status;
    private String create_time;
    private String update_time;

    public Character(long character_id, String character_name, String character_type, Status status, String create_time, String update_time) {
        this.character_id = character_id;
        this.character_name = character_name;
        this.character_type = character_type;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;
    }
}

