package com.example20231016.todo_3.Mapper;

import com.example20231016.todo_3.Table.User;
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



}
