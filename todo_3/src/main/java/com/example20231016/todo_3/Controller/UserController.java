package com.example20231016.todo_3.Controller;

import com.example20231016.todo_3.CommonResult;
import com.example20231016.todo_3.Mapper.UserMapper;
import com.example20231016.todo_3.Service.MenuService;
import com.example20231016.todo_3.Service.UserService;
import com.example20231016.todo_3.Table.User;
import com.example20231016.todo_3.Token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;
    private final MenuService menuService;

    public UserController(UserService userService ,MenuService menuService) {
        this.userService = userService;
        this.menuService=menuService;
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



//    @PostMapping("/tasks")
//    public CommonResult<?> addUser(@RequestBody User user) {
//        return CommonResult.success(userService.addUser(user));
//    }

    //新方法

//    @PostMapping("/user/login")
//    public CommonResult<?> checkLogin(@RequestBody User user) {
//        User temp=userService.checkLogin(user);
//        if(temp==null)
//        {
//            return CommonResult.error(444,"wrong info");
//        }
//        else {
//            if(temp.getPassword().equals(user.getPassword())&&temp.getUsername().equals(user.getUsername()))
//                return CommonResult.success(temp);
//            else
//                return CommonResult.error(444,"wrong info");
//        }
//    }

    @PostMapping("/user/postUser")
    public CommonResult<?> postUser(@RequestBody User user) {
        return CommonResult.success(userService.postUser(user));
    }
    @DeleteMapping("/user/deleteUser")
    public CommonResult<?> deleteUser(@RequestBody User user) {
        return CommonResult.success(userService.deleteUser(user));
    }
    @PutMapping("/user/putUser")
    public CommonResult<?> putUser(@RequestBody User user) {
        return CommonResult.success(userService.putUser(user));
    }
    @GetMapping("/user/getInfo")
    public CommonResult<?> getAllUsers() {
        return CommonResult.success(userService.getAllUsers());
    }
    @GetMapping("/user/searchUser")
    public CommonResult<?> searchUser(@RequestParam("id") String idStr
//                                      @RequestParam("username") String username,
//                                      @RequestParam("password") String password,
//                                      @RequestParam("name") String name,
//                                      @RequestParam("gender") String gender,
//                                      @RequestParam("phone") String phone
                                                                            ) {
        long id = idStr.isEmpty() ? 0 : Long.parseLong(idStr);
        User temp=new User(id,"","","","","");
        System.out.println(temp);
//        System.out.println(temp.getName());
//        List<User> tempServeice=userService.searchUser(temp);
//        System.out.println(tempServeice);
        return CommonResult.success(userService.searchUser(temp));
    }
    @PostMapping("/user/postCharacter")
    public CommonResult<?> postCharacter(@RequestBody User user) {
        return CommonResult.success(userService.postUser(user));
    }


    @GetMapping("/user/profile/get")
    public CommonResult<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {

        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        long usr_id=jwtTokenUtil.getUserIdFromToken(token);

        User foundUser = userMapper.findByUserId(usr_id);

        CommonResult<User> result = CommonResult.success(foundUser);
        return result;
    }
    @GetMapping("/user/getMenu")
    public CommonResult<?> getMenuByUser(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        int user_id = jwtTokenUtil.getUserIdFromToken(token);

        return CommonResult.success(menuService.getMenuByUserId(user_id));

    }

}
