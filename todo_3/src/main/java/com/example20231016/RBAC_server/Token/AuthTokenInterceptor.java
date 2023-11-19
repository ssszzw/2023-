package com.example20231016.RBAC_server.Token;

import com.example20231016.RBAC_server.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(request.getRequestURI()+"被拦截了");
        // 检查注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AuthToken authTokenClass = handlerMethod.getBeanType().getAnnotation(AuthToken.class);
            AuthToken authTokenMethod = handlerMethod.getMethod().getAnnotation(AuthToken.class);

            // 如果方法或类上标记了注解
            if (authTokenMethod != null || authTokenClass != null) {
                // 从请求中获取Access Token
                String accessToken = request.getHeader("Authorization").substring(7);


                System.out.print("request.getPathInfo()"+request.getRequestURI());

                // 验证Access Token
                if (accessToken != null && redisTemplate.hasKey(accessToken)) {

                    System.out.println("-------success!!!");
                    return true;
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    System.out.println("-------failure!!!");

                    addDataObjectToResponse(response , CommonResult.error(401,"accessToken认证失败了乐乐乐乐"));
                    response.setCharacterEncoding("UTF-8");

                    return false;
                }
            }
        }
        return true;
    }
    private void addDataObjectToResponse(HttpServletResponse response,CommonResult<?> dataObject) throws IOException {
//         使用ObjectMapper将数据对象转换为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(dataObject);


//        JSONObject res = new JSONObject();
//        res.put("status","-1");
//        res.put("msg","need login");


        System.out.println("jsonData: " + jsonData);


        // 设置响应内容类型为JSON
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentType("application/json; charset=utf-8");

        // 获取响应的Writer并将JSON数据写入响应体
        PrintWriter writer = response.getWriter();
        writer.write(jsonData);

//        System.out.println("response:---"+response);

        // 刷新并关闭Writer
        writer.flush();
        writer.close();
    }
}
