package com.example20231016.RBAC_server.Mapper;

import com.example20231016.RBAC_server.Table.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User check(User user);
//    List<User> getAllUsers();

    int post(User user);
    int delete(User user);
    int put(User user);
    List<User> getAllUsers();
    List<User> search(User user);

    User findByUsername(String username);
    User findByUserId(long usr_id);
    List<Long>findCharacterIdsByUserId(Long user_id);
    int saveUserCharacter(Long user_id, Long character_id);
    int deleteUserCharacter(Long user_id, Long character_id);



}
