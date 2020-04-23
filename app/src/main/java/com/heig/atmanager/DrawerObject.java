package com.heig.atmanager;

/**
 * Author : Stephane
 * Date   : 12.04.2020
 * <p>
 * This class better be good.
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
