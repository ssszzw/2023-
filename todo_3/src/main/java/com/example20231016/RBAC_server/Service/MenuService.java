package com.example20231016.RBAC_server.Service;


import com.example20231016.RBAC_server.Mapper.CharacterMapper;
import com.example20231016.RBAC_server.Mapper.MenuMapper;
import com.example20231016.RBAC_server.Mapper.UserMapper;
import com.example20231016.RBAC_server.Table.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CharacterMapper characterMapper;
    @Autowired
    MenuMapper menuMapper;


    public List<Menu> getMenuByUserId(int user_id)
    {
        List<Integer> characterIdList=characterMapper.getCharacterIdByUserId(user_id);
//        System.out.println("characterIdList:"+characterIdList);
        List<Menu> menuList = new ArrayList<>();

        for (Integer character_Id : characterIdList) {
            List<Integer> menuIdList = menuMapper.getMenuIdsByCharacterId(character_Id);
//            System.out.println("menuIdList:"+menuIdList);
            for(Integer menu_id:menuIdList){
//                System.out.println("menu_id:"+menu_id);
                Menu menu = menuMapper.getMenuByMenuId(menu_id);
                if (!menuList.contains(menu)) {
                    menuList.add(menu);
                }
            }
        }

//        System.out.println(menuList);
        handleMenu(menuList);
//        System.out.println(menuList);

        return menuList;
    }
    public void handleMenu(List<Menu> menuList){
//        System.out.println(menuList.size());
//        System.out.println(menuList);
        for (Menu menu : menuList) {
            int menu_Id=menu.getMenu_id();
            List<Menu> children = menuMapper.getChildrenByParentId(menu_Id);

            menu.setChildren(children);
        }

        Iterator<Menu> iterator = menuList.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            if (menu.getParent_id() != 0) {
                iterator.remove();
            }
        }
    }

}
