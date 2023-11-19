package com.example20231016.RBAC_server.Controller;

import com.example20231016.RBAC_server.CommonResult;
import com.example20231016.RBAC_server.Mapper.UserMapper;
import com.example20231016.RBAC_server.Service.MenuService;
import com.example20231016.RBAC_server.Service.UserService;
import com.example20231016.RBAC_server.Table.User;
import com.example20231016.RBAC_server.Token.AuthToken;
import com.example20231016.RBAC_server.Token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AuthToken
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

    @PostMapping("/user/postUser")
    public CommonResult<?> postUser(@RequestBody User user) {
        return CommonResult.success(userService.postUser(user));
    }
    @DeleteMapping("/user/deleteUser")
    public CommonResult<?> deleteUser(@RequestBody User user) {
        System.out.println("user:"+user);
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
    public CommonResult<?> searchUser(@RequestParam("id") String idStr) {
        long id = idStr.isEmpty() ? 0 : Long.parseLong(idStr);
        User temp=new User(id,"","","","","");

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
