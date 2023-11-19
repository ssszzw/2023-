package com.example20231016.RBAC_server.Service;

import com.example20231016.RBAC_server.Table.User;
import com.example20231016.RBAC_server.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public int addUser(User user) {
        return userMapper.post(user);
    }
//    public int updateUser(User user) {
//        return userMapper.update(user);
//    }
//    public int deleteUser(long id) {
//        return userMapper.delete(id);
//    }

    //checkLogin
    public User checkLogin(User user) {
        return userMapper.check(user);
    }

    //postUser
    public int postUser(User user) {
        return userMapper.post(user);
    }
    public int deleteUser(User user) {
        return userMapper.delete(user);
    }
    public int putUser(User user) {
        return userMapper.put(user);
    }

    //getInfo
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
    public List<User> searchUser(User user) {
        return userMapper.search(user);
    }

}

