package com.example20231016.RBAC_server.Table;


import lombok.Data;

@Data
public class Character_Permission {
    private int character_id;
    private int permission_id;

    public Character_Permission(int character_id,int permission_id){
        this.character_id=character_id;
        this.permission_id=permission_id;
    }
}
