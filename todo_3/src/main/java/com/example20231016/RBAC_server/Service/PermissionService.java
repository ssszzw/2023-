package com.example20231016.RBAC_server.Service;

import com.example20231016.RBAC_server.Mapper.PermissionMapper;
import com.example20231016.RBAC_server.Table.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public Permission checkPermission(Permission permission){
        return permissionMapper.check(permission);
    }

    public int postPermission(Permission permission){
        return permissionMapper.post(permission);
    }
    public int deletePermission(Integer permission_id){
        return permissionMapper.delete(permission_id);
    }
    public int putPermission(Permission permission){
        return permissionMapper.put(permission);
    }

    public List<Permission> getAllPermissions(){
        return permissionMapper.getAllPermissions();
    }
    public List<Permission> searchPermissions(Permission permission){
        return permissionMapper.search(permission);
    }
    public List<Integer>getPermissionIdsByCharacterId(Integer character_id){
        return permissionMapper.getPermissionIdsByCharacterId(character_id);
    }






}