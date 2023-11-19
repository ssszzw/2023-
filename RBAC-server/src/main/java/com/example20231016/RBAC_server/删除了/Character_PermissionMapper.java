package com.example20231016.RBAC_server.Mapper;


import com.example20231016.RBAC_server.Table.Character_Permission;
import com.example20231016.RBAC_server.Table.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Character_PermissionMapper {


    int post(Character_Permission characterPermission);
    int delete(Character_Permission characterPermission);

    List<Permission> getAllCharacter_Permissions(Character_Permission characterPermission);
    List<Character_Permission> search(Character_Permission characterPermission);
}
