package com.example20231016.todo_3.Controller;

import com.example20231016.todo_3.CommonResult;
import com.example20231016.todo_3.Service.PermissionService;
import com.example20231016.todo_3.Table.Permission;
import org.springframework.web.bind.annotation.*;

@RestController
public class PermissionController {
    private final PermissionService permissionService;
    public PermissionController(PermissionService permissionService){
        this.permissionService=permissionService;
    }

    @PostMapping("/permission/postPermission")
    public CommonResult<?> postPermission(@RequestBody Permission permission) {
        return CommonResult.success(permissionService.postPermission(permission));
    }
    @DeleteMapping("/permission/deletePermission")
    public CommonResult<?> deletePermission(@RequestBody Permission permission){
        return CommonResult.success(permissionService.deletePermission(permission));
    }
    @PutMapping("/permission/putPermission")
    public CommonResult<?> putPermission(@RequestBody Permission permission){
        return CommonResult.success(permissionService.putPermission(permission));
    }
    @GetMapping("/permission/getAllPermissions")
    public CommonResult<?> getAllPermissions(){
        return CommonResult.success(permissionService.getAllPermissions());
    }
    @GetMapping("/permission/searchPermission")
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

        Permission temp=new Permission(permission_id,"","","",null);

        System.out.println(temp);
        return CommonResult.success(permissionService.searchPermissions(temp));

    }

}