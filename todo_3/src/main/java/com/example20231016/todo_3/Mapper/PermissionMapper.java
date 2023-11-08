package com.example20231016.todo_3.Mapper;

import com.example20231016.todo_3.Table.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    Permission check(Permission permission);

    int post(Permission permission);
    int delete(Permission permission);
    int put(Permission permission);
    List<Permission> getAllPermissions();
    List<Permission> search(Permission permission);
}
