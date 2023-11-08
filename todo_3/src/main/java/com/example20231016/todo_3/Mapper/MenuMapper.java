package com.example20231016.todo_3.Mapper;

import com.example20231016.todo_3.Table.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenuMapper {
    List<Integer>getMenuIdsByCharacterId(int character_id);
    Menu getMenuByMenuId(int Menu_id);
    List<Menu>getChildrenByParentId(int parent_id);
}
