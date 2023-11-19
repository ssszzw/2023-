package com.example20231016.RBAC_server.Controller;


import com.example20231016.RBAC_server.CommonResult;
import com.example20231016.RBAC_server.Service.User_CharacterService;
import com.example20231016.RBAC_server.Table.User_Character;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class User_CharacterController {
    private final User_CharacterService user_CharacterService;

    public User_CharacterController(User_CharacterService user_CharacterService){
        this.user_CharacterService=user_CharacterService;
    }


    @PostMapping("/user/postUser_Character")
    public CommonResult<?> postUser_Character(@RequestBody List<User_Character> user_characterList) {
        return CommonResult.success(user_CharacterService.postUser_Character(user_characterList));
    }
    @DeleteMapping("/user/deleteUser_Character")
    public CommonResult<?> deleteUser_Character(@RequestBody User_Character user_character){
        return CommonResult.success(user_CharacterService.deleteUser_Character(user_character));
    }
//    @PutMapping("/user/putUser_Character")
//    public CommonResult<?> putUser_Character(@RequestBody User_Character user_character){
//        return CommonResult.success(user_CharacterService.putUser_Character(user_character));
//    }
    @GetMapping("/user/getAllUser_Characters")
    public CommonResult<?> getAllUser_Characters(@RequestParam("user_id")int user_id){

        return CommonResult.success(user_CharacterService.getAllUser_Characters(new User_Character(user_id,0)));
    }
    @GetMapping("/user/searchUser_Character")
    public CommonResult<?> searchUser_Character(@RequestParam(value = "user_id",defaultValue = "") String user_idStr,
                                                @RequestParam(value = "character_id",defaultValue = "") String character_idStr
//                                            @RequestParam("permission_name") String permission_name,
//                                            @RequestParam("description") String description,
//                                            @RequestParam("module") String module,
//                                            @RequestParam("status") String statusStr
    ){
        int user_id = user_idStr.isEmpty() ? 0 : Integer.parseInt(user_idStr);
        int character_id = character_idStr.isEmpty() ? 0 : Integer.parseInt(character_idStr);

//        Status status;
//        if(statusStr.equals("ACTIVE"))
//            status=Status.ACTIVE;
//        else
//            status=Status.INACTIVE;
        System.out.println(user_id);
        System.out.println(character_id);

        User_Character temp=new User_Character(user_id,character_id);

        System.out.println(temp);
        return CommonResult.success(user_CharacterService.searchUser_Characters(temp));

    }
}
