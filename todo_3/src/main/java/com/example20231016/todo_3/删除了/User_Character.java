package com.example20231016.todo_3.Table;

import lombok.Data;

@Data
public class User_Character {
    private int user_id;
    private int character_id;

    public User_Character(int user_id, int character_id) {
        this.user_id = user_id;
        this.character_id = character_id;
    }
}
