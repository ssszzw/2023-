package com.example20231016.RBAC_server.Mapper;


import com.example20231016.RBAC_server.Table.User_Character;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface User_CharacterMapper {

    int post(User_Character user_character);
    int delete(User_Character user_character);
//    int put(User_Character user_character);
    List<Character> getAllUser_Characters(User_Character user_character);
    List<User_Character> search(User_Character user_character);

}
