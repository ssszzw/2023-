package com.example20231016.RBAC_server.Controller;

import com.example20231016.RBAC_server.CommonResult;
import com.example20231016.RBAC_server.Mapper.CharacterMapper;
import com.example20231016.RBAC_server.Service.CharacterService;
import com.example20231016.RBAC_server.Service.UserCharacterService;
import com.example20231016.RBAC_server.Table.AssignCharacterRequest;
import com.example20231016.RBAC_server.Table.Character;
import com.example20231016.RBAC_server.Token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AuthToken
@RestController
public class CharacterController {
    private final CharacterService characterService;
    private final UserCharacterService userCharacterService;
    public CharacterController(CharacterService characterService,UserCharacterService userCharacterService){
        this.characterService=characterService;
        this.userCharacterService=userCharacterService;
    }
    @Autowired
    CharacterMapper characterMapper;

    @PostMapping("/character/postCharacter")
    public CommonResult<?> postCharacter(@RequestBody Character character) {
        return CommonResult.success(characterService.postCharacter(character));
    }
//    @DeleteMapping("/character/deleteCharacter")
//    public CommonResult<?> deleteCharacter(@RequestBody Character character){
//        return CommonResult.success(characterService.deleteCharacter(character));
//    }
    @PutMapping("/character/putCharacter")
    public CommonResult<?> putCharacter(@RequestBody Character character){
        return CommonResult.success(characterService.putCharacter(character));
    }
    @GetMapping("/character/getAllCharacters")
    public CommonResult<?> getAllCharacters(){
        return CommonResult.success(characterService.getAllCharacters());
    }
    @GetMapping("/character/searchCharacter")
    public CommonResult<?> searchCharacter(@RequestParam("character_id") String character_idStr
//                                           @RequestParam("character_name") String character_name,
//                                           @RequestParam("character_type") String character_type,
//                                           @RequestParam("character_status") String character_statusStr
                                                                                                    ){
        System.out.println(character_idStr);
        long character_id = character_idStr.isEmpty() ? 0 : Long.parseLong(character_idStr);

//        Status status;
//        if(character_statusStr.equals("ACTIVE"))
//            status= Status.ACTIVE;
//        else
//            status=Status.INACTIVE;

        Character temp=new Character(character_id,"","",null,"","");
        System.out.println(temp);
        return CommonResult.success(characterService.searchCharacters(temp));

    }
    @GetMapping("/character/getCharactersByUserId")
    public CommonResult<?> getCharactersByUserId(@RequestParam("userId") Integer user_id){
        System.out.println("getCharactersByUserId---userId:"+user_id);

        return CommonResult.success(characterService.getCharactersByUserId(user_id));

    }
    @PostMapping("/character/assignCharacter")
    public CommonResult<?> assignCharacter(@RequestBody AssignCharacterRequest request) {
        // 根据需要处理 request 中的数据
        List<Long> selectedCharacterIds = request.getSelectedCharacters();
        Long userId = request.getUserId();

        // 执行相应的逻辑

        return CommonResult.success(userCharacterService.assignCharacters(userId,selectedCharacterIds));
    }
    @PutMapping("/system/character/editCharacter")
    public CommonResult<?> editCharacter(@RequestBody Character character) {
//        System.out.println("character:"+character);
        return CommonResult.success(characterService.putCharacter(character));
    }
    @PostMapping("/system/character/addCharacter")
    public CommonResult<?> addCharacter(@RequestBody Character character){
        return CommonResult.success(characterService.postCharacter(character));
    }
    @DeleteMapping("/system/character/deleteCharacter")
    public CommonResult<?> deleteCharacter(@RequestBody Map<String, Integer> requestBody){
        Integer character_id = requestBody.get("character_id");
        System.out.println("deleteCharacter---character_idStr:"+character_id);

        return CommonResult.success(characterService.deleteCharacter(character_id));
    }

}
