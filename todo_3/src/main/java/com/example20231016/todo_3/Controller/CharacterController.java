package com.example20231016.todo_3.Controller;

import com.example20231016.todo_3.CommonResult;
import com.example20231016.todo_3.Service.CharacterService;
import com.example20231016.todo_3.Table.Character;
import org.springframework.web.bind.annotation.*;

@RestController
public class CharacterController {
    private final CharacterService characterService;
    public CharacterController(CharacterService characterService){
        this.characterService=characterService;
    }

    @PostMapping("/character/postCharacter")
    public CommonResult<?> postCharacter(@RequestBody Character character) {
        return CommonResult.success(characterService.postCharacter(character));
    }
    @DeleteMapping("/character/deleteCharacter")
    public CommonResult<?> deleteCharacter(@RequestBody Character character){
        return CommonResult.success(characterService.deleteCharacter(character));
    }
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

        Character temp=new Character(character_id,"","",null);
        System.out.println(temp);
        return CommonResult.success(characterService.searchCharacters(temp));

    }

}
