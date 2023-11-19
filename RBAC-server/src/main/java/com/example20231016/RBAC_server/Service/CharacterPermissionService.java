package com.example20231016.RBAC_server.Service;

import com.example20231016.RBAC_server.Mapper.CharacterMapper;
import com.example20231016.RBAC_server.Mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterPermissionService {

    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public int assignCharacterPermissions(List<Integer> selectedPermissionIds,Integer character_id) {
        // 查询数据库中已经存在的角色权限列表
        List<Integer> existingPermissionIds = permissionMapper.getPermissionIdsByCharacterId(character_id);

        // 找出需要新增的权限
        List<Integer> permissionsToAddList = selectedPermissionIds.stream()
                .filter(permissionId -> !existingPermissionIds.contains(permissionId))
                .collect(Collectors.toList());

        // 找出需要删除的权限
        List<Integer> permissionsToRemoveList = existingPermissionIds.stream()
                .filter(existingPermissionId -> !selectedPermissionIds.contains(existingPermissionId))
                .collect(Collectors.toList());

        // 执行数据库操作
        addCharacterPermissions(character_id, permissionsToAddList);
        removeCharacterPermissions(character_id, permissionsToRemoveList);

        return 1;
    }

    private void addCharacterPermissions(Integer character_id, List<Integer> permissionIds) {
        // 将需要新增的权限插入数据库
        permissionIds.forEach(permissionId -> characterMapper.saveCharacterPermission(character_id, permissionId));
    }

    private void removeCharacterPermissions(Integer character_id, List<Integer> permissionIds) {
        // 将需要删除的权限从数据库中删除
        permissionIds.forEach(permissionId -> characterMapper.deleteCharacterPermission(character_id, permissionId));
    }
}
