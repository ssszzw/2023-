package com.example20231016.RBAC_server.Service;

import com.example20231016.RBAC_server.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCharacterService {

    @Autowired
    private UserMapper userMapper;

    public int assignCharacters(Long userId, List<Long> selectedCharacterIds) {
        // 查询数据库中已经存在的用户角色列表
        List<Long> existingCharacterIds = userMapper.findCharacterIdsByUserId(userId);

        // 找出需要新增的角色
        List<Long> charactersToAddList = selectedCharacterIds.stream()
                .filter(characterId -> !existingCharacterIds.contains(characterId))
                .collect(Collectors.toList());

        // 找出需要删除的角色
        List<Long> charactersToRemoveList = existingCharacterIds.stream()
                .filter(existingCharacterId -> !selectedCharacterIds.contains(existingCharacterId))
                .collect(Collectors.toList());

        // 执行数据库操作
        addCharacters(userId, charactersToAddList);
        removeCharacters(userId, charactersToRemoveList);
        return 1;
    }

    private void addCharacters(Long userId, List<Long> characterIds) {
        // 将需要新增的角色插入数据库
        characterIds.forEach(characterId -> userMapper.saveUserCharacter(userId, characterId));
    }

    private void removeCharacters(Long userId, List<Long> characterIds) {
        // 将需要删除的角色从数据库中删除
        characterIds.forEach(characterId -> userMapper.deleteUserCharacter(userId, characterId));
    }
}

