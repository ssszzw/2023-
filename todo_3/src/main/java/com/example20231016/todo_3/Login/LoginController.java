package com.example20231016.todo_3.Login;


import com.example20231016.todo_3.CommonResult;
import com.example20231016.todo_3.Mapper.UserMapper;
import com.example20231016.todo_3.Table.User;
import com.example20231016.todo_3.Token.JwtTokenUtil;
import com.example20231016.todo_3.Token.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;




    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody LoginRequest loginUser) {

        User user = userMapper.findByUsername(loginUser.getUsername());

        if (user == null) {
            return CommonResult.error(50007,"登录失败，账号密码不正确null");
        }

        if (!loginUser.getPassword().equals(user.getPassword())) {
            return CommonResult.error(50007,"登录失败，账号密码不正确error");
        }

//        String username = loginUser.getUsername();
        long user_id=user.getId();
        String usr_idStr=String.valueOf(user_id);

        // 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(usr_idStr);
        String refreshToken = jwtTokenUtil.generateRefreshToken(usr_idStr);
        TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);

        CommonResult<TokenResponse> result = CommonResult.success(token_resp);

        return result;
    }

}
