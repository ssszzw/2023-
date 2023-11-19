package com.example20231016.RBAC_server.Controller;

import com.example20231016.RBAC_server.CommonResult;
import com.example20231016.RBAC_server.Service.CharacterPermissionService;
import com.example20231016.RBAC_server.Service.PermissionService;
import com.example20231016.RBAC_server.Table.AssignPermissionRequest;
import com.example20231016.RBAC_server.Table.Permission;
import com.example20231016.RBAC_server.Token.AuthToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AuthToken
@RestController
public class PermissionController {
    private final PermissionService permissionService;
    private final CharacterPermissionService characterPermissionService;
    public PermissionController(PermissionService permissionService,CharacterPermissionService characterPermissionService){
        this.permissionService=permissionService;
        this.characterPermissionService=characterPermissionService;
    }

    @PostMapping("/system/permission/postPermission")
    public CommonResult<?> postPermission(@RequestBody Permission permission) {
        return CommonResult.success(permissionService.postPermission(permission));
    }
//    @DeleteMapping("/system/permission/deletePermission")
//    public CommonResult<?> deletePermission(@RequestBody Permission permission){
//        return CommonResult.success(permissionService.deletePermission(permission));
//    }
    @PutMapping("/system/permission/putPermission")
    public CommonResult<?> putPermission(@RequestBody Permission permission){
        return CommonResult.success(permissionService.putPermission(permission));
    }
    @GetMapping("/system/permission/getAllPermissions")
    public CommonResult<?> getAllPermissions(){
        return CommonResult.success(permissionService.getAllPermissions());
    }
    @GetMapping("/system/permission/searchPermission")
    public CommonResult<?> searchPermission(@RequestParam("permission_id") String permission_idStr
//                                            @RequestParam("permission_name") String permission_name,
//                                            @RequestParam("description") String description,
//                                            @RequestParam("module") String module,
//                                            @RequestParam("status") String statusStr
                                                                                    ){
        int permission_id = permission_idStr.isEmpty() ? 0 : Integer.parseInt(permission_idStr);

//        Status status;
//        if(statusStr.equals("ACTIVE"))
//            status=Status.ACTIVE;
//        else
//            status=Status.INACTIVE;
        System.out.println(permission_id);

        Permission temp=new Permission(permission_id);

        System.out.println(temp);
        return CommonResult.success(permissionService.searchPermissions(temp));

    }
    @GetMapping("/system/permission/getPermissionIdsByCharacterId")
    public CommonResult<?> getPermissionIdsByCharacterId(@RequestParam("character_id") Integer character_id){

        return CommonResult.success(permissionService.getPermissionIdsByCharacterId(character_id));
    }
    @PostMapping("/system/permission/assignCharacterPermissions")
    public CommonResult<?> assignCharacterPermissions(@RequestBody AssignPermissionRequest request) {
        List<Integer> selectedPermissionIds=request.getSelectedPermissionIds();
        Integer characterId=request.getCharacterId();
//        System.out.println("request:"+request);
        return CommonResult.success(characterPermissionService.assignCharacterPermissions(selectedPermissionIds,characterId));
    }
    @PutMapping("/system/permission/editPermission")
    public CommonResult<?> editPermission(@RequestBody Permission permission){
        return CommonResult.success(permissionService.putPermission(permission));
    }
    @PostMapping("/system/permission/addPermission")
    public CommonResult<?> addPermission(@RequestBody Permission permission) {
        System.out.println("permission:---------"+permission);
        return CommonResult.success(permissionService.postPermission(permission));
    }
    @DeleteMapping("/system/permission/deletePermission")
    public CommonResult<?> deletePermission(@RequestBody Map<String, Integer> requestBody){
        Integer permission_id = requestBody.get("permission_id");

        return CommonResult.success(permissionService.deletePermission(permission_id));
    }


}