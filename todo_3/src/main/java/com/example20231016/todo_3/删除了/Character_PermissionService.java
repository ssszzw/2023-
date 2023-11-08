package com.example20231016.todo_3.Service;

import com.example20231016.todo_3.Mapper.Character_PermissionMapper;
import com.example20231016.todo_3.Table.Character_Permission;
import com.example20231016.todo_3.Table.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Character_PermissionService {
    @Autowired
    private  Character_PermissionMapper character_permissionMapper;

    public int postCharacter_Permission(List<Character_Permission>character_permissionList){
        Character_Permission deleteTemp=new Character_Permission(character_permissionList.get(0).getCharacter_id(),0);
        List<Character_Permission>deleteList=character_permissionMapper.search(deleteTemp);
        for(Character_Permission x:deleteList){
            character_permissionMapper.delete(x);
        }
        for(Character_Permission x:character_permissionList){
            character_permissionMapper.post(x);
        }
        return 1;

    }
    public int deleteCharacter_Permission(Character_Permission characterPermission){
        return character_permissionMapper.delete(characterPermission);
    }

    public List<Permission>getAllCharacter_Permissions(Character_Permission character_permission){
        return character_permissionMapper.getAllCharacter_Permissions(character_permission);
    }
    public List<Character_Permission>searchCharacter_Permission(Character_Permission characterPermission){
        return character_permissionMapper.search(characterPermission);
    }
}
