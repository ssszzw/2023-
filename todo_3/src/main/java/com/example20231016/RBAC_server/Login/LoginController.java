package com.example20231016.RBAC_server.Login;


import com.example20231016.RBAC_server.CommonResult;
import com.example20231016.RBAC_server.Mapper.UserMapper;
import com.example20231016.RBAC_server.Table.User;
import com.example20231016.RBAC_server.Token.JwtTokenUtil;
import com.example20231016.RBAC_server.Token.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final AuthService authService;
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody LoginRequest loginUser) {

        User user = userMapper.findByUsername(loginUser.getUsername());

        if (user == null) {
            return CommonResult.error(50007,"登录失败，账号密码不正确--无该用户");
        }
        if (!loginUser.getPassword().equals(user.getPassword())) {
            return CommonResult.error(50007,"登录失败，账号密码不正确--密码错误");
        }
        long user_id=user.getId();
        String usr_idStr=String.valueOf(user_id);

        // 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(usr_idStr);
        String refreshToken = jwtTokenUtil.generateRefreshToken(usr_idStr);

        System.out.println("accessToken:---"+accessToken+"----------usr_idStr:---"+usr_idStr);

        redisTemplate.opsForValue().set(accessToken, usr_idStr, 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(refreshToken, usr_idStr, 1, TimeUnit.DAYS);

//        redisTemplate.opsForValue().set("pppp111", "123str");
//        redisTemplate.opsForValue().set("pppp2222", 123);
//        System.out.println("从redis中取出的str："+(String)redisTemplate.opsForValue().get("pppp111"));
//        System.out.println("从redis中取出的int："+(Integer)redisTemplate.opsForValue().get("pppp2222"));



        TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);


        CommonResult<TokenResponse> result = CommonResult.success(token_resp);
        return result;
    }

    @PostMapping("/system/auth/refreshToken")
    public CommonResult<?> refreshToken(@RequestBody Map<String, String> requestBody) {

        System.out.println("进入了refreshToken");
        String refreshToken = requestBody.get("refreshToken");

        System.out.println("refreshToken:-----"+refreshToken);
        // 检查 refreshToken 是否在 Redis 中存在
        if (redisTemplate.hasKey(refreshToken)) {
            // 获取用户 ID
            String userId = (String)redisTemplate.opsForValue().get(refreshToken);
            System.out.println("userId:-------"+userId);

            // 刷新 Token 逻辑，这里只是示例，你需要根据实际情况生成新的 Token
            String newAccessToken = jwtTokenUtil.generateAccessToken(userId);


            // 设置 Token 的有效时间，newToken设置为1小时，refreshToken设置为一天
            redisTemplate.opsForValue().set(newAccessToken, userId, 1, TimeUnit.HOURS);
            redisTemplate.opsForValue().set(refreshToken, userId, 1, TimeUnit.DAYS);

            // 返回新的 Token
            TokenResponse tokenResponse=new TokenResponse(newAccessToken,refreshToken);
            return CommonResult.success(tokenResponse);
        } else {
            // 如果 refreshToken 不存在，抛出异常
            throw new MyCustomException("RefreshToken未找到");
        }

    }

    @PostMapping("/system/auth/logout")
    public CommonResult<?> logout(@RequestBody TokenResponse tokenResponse) {
        if(tokenResponse.getAccessToken() != null&&!tokenResponse.getAccessToken().isEmpty()){
            if (redisTemplate.hasKey(tokenResponse.getAccessToken())) {
                redisTemplate.delete(tokenResponse.getAccessToken());
            }
        }
        if(tokenResponse.getAccessToken() != null&&!tokenResponse.getRefreshToken().isEmpty()) {
            if (redisTemplate.hasKey(tokenResponse.getRefreshToken())) {
                redisTemplate.delete(tokenResponse.getRefreshToken());
            }
        }

        return CommonResult.success("登出成功了乐乐乐乐");

    }


}
