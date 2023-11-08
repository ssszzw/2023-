package com.example20231016.todo_3.Table;


import lombok.Data;

import java.util.List;

;

@Data
public class Menu {
    private int menu_id;
    private String menu_name;
    private int parent_id;
    private String url;
    private String component;
    private int order_num;
    private transient List<Menu> children;

    public Menu(int menu_id, String menu_name, int parent_id, String url, String component,int order_num) {
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.parent_id = parent_id;
        this.url = url;
        this.component=component;
        this.order_num = order_num;
    }

}
