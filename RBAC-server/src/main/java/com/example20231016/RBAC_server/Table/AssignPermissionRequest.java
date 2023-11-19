package com.example20231016.RBAC_server.Table;

import lombok.Data;

import java.util.List;

@Data
public class AssignPermissionRequest {
    private List<Integer> selectedPermissionIds;
    private Integer characterId;

    // 添加构造函数和 getter/setter 方法
}