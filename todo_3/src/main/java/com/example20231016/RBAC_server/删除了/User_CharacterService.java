package com.example20231016.RBAC_server.Service;


import com.example20231016.RBAC_server.Mapper.User_CharacterMapper;
import com.example20231016.RBAC_server.Table.User_Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_CharacterService {
    @Autowired
    private User_CharacterMapper userCharacterMapper;


    public int postUser_Character(List<User_Character> user_characterList){
        int user_id=user_characterList.get(0).getUser_id();
        List<User_Character> deleteList=userCharacterMapper.search(new User_Character(user_id,0));
        for(User_Character x:deleteList){
            userCharacterMapper.delete(x);
        }
        for(User_Character x:user_characterList)
        {
            userCharacterMapper.post(x);
        }
        return 1;
    }
    public int deleteUser_Character(User_Character user_character){
        return userCharacterMapper.delete(user_character);
    }
//    public int putUser_Character(User_Character user_character){
//        return userCharacterMapper.put(user_character);
//    }

    public List<Character> getAllUser_Characters(User_Character user_character){
        return userCharacterMapper.getAllUser_Characters(user_character);
    }
    public List<User_Character> searchUser_Characters(User_Character user_character){
        return userCharacterMapper.search(user_character);
    }
}
