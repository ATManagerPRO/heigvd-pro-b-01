package com.heig.atmanager;

/**
 * Author : Stéphane Bottin
 * Date   : 12.04.2020
 *
 * Abstract object that can be displayed in the drawer menu
 */
public abstract class DrawerObject {
    String name;

    protected DrawerObject(String name) {
        this.name = name;
    }

    public abstract boolean isFolder();

    public String getName() {
        return name;
    }
}
