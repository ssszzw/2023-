package com.example20231016.RBAC_server.Table;

import lombok.Data;

import java.util.List;


@Data
public class AssignCharacterRequest {
    private List<Long> selectedCharacters;
    private Long userId;

    // 添加构造函数和 getter/setter 方法
}
