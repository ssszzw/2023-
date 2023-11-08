package com.example20231016.todo_3.Table;

import lombok.Data;


@Data
public class Character {
    private long character_id;
    private String character_name;
    private String character_type;
//    @Enumerated(EnumType.STRING)
    private Status status;

    public Character(long character_id, String character_name, String character_type, Status status) {
        this.character_id = character_id;
        this.character_name = character_name;
        this.character_type = character_type;
        this.status = status;
    }
}

