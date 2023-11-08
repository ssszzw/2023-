package com.example20231016.todo_3.Controller;


import com.example20231016.todo_3.CommonResult;
import com.example20231016.todo_3.Service.Character_PermissionService;
import com.example20231016.todo_3.Table.Character_Permission;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Character_PermissionController {
    private final Character_PermissionService character_permissionService;

    public Character_PermissionController(Character_PermissionService character_permissionService) {
        this.character_permissionService = character_permissionService;
    }

    @PostMapping("/character/postCharacter_Permission")
    public CommonResult<?> postCharacter_Permission(@RequestBody List<Character_Permission> character_permissionsList) {
        return CommonResult.success(character_permissionService.postCharacter_Permission(character_permissionsList));
    }

    @DeleteMapping("/character/deleteCharacter_Permission")
    public CommonResult<?> deleteCharacter_Permission(@RequestBody Character_Permission characterPermission) {
        return CommonResult.success(character_permissionService.deleteCharacter_Permission(characterPermission));
    }

    @GetMapping("/characer/getAllCharacter_Permission")
    public CommonResult<?> getAllCharacter_Permission(@RequestParam("character_id") int character_id) {
        return CommonResult.success(character_permissionService.getAllCharacter_Permissions(new Character_Permission(character_id, 0)));
    }

    @GetMapping("/character/searchCharacter_Permission")
    public CommonResult<?> searchCharacter_Permission(@RequestParam(value = "character_id", defaultValue = "") String character_idStr,
                                                      @RequestParam(value = "permission_id", defaultValue = "") String permission_idStr
    ) {
        int character_id = character_idStr.isEmpty() ? 0 : Integer.parseInt(character_idStr);
        int permission_id = permission_idStr.isEmpty() ? 0 : Integer.parseInt(permission_idStr);
        Character_Permission characterPermission=new Character_Permission(character_id,permission_id);

        return CommonResult.success(character_permissionService.searchCharacter_Permission(characterPermission));
    }
}
