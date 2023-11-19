package com.example20231016.RBAC_server.Mapper;

import com.example20231016.RBAC_server.Table.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    Permission check(Permission permission);

    int post(Permission permission);
    int delete(Integer permission_id);
    int put(Permission permission);
    List<Permission> getAllPermissions();
    List<Permission> search(Permission permission);
    List<Integer>getPermissionIdsByCharacterId(Integer character_id);




}
