package com.game.playparcels.Expandable;

/**
 * Created by anupamchugh on 22/12/17.
 */

public class MenuModel {

    public String menuName;
    public boolean hasChildren, isGroup;
    public int iconmenu;
    public String url;

    public MenuModel(int iconmenu, String menuName, boolean isGroup, boolean hasChildren, String url) {
        this.iconmenu = iconmenu;
        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
