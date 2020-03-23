package com.heig.atmanager;

import java.util.ArrayList;

public class User {

    private String userName;
    private static ArrayList<String> directories;
    private String googleToken;

    public String getUserName() {
        return userName;
    }

    public static ArrayList<String> getDirectories() {
        return directories;
    }

    public String getGoogleToken() {
        return googleToken;
    }
}
