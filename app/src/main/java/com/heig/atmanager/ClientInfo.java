package com.heig.atmanager;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class ClientInfo {

    public static String personName;
    public static String personGivenName;
    public static String personFamilyName;
    public static String personEmail;
    public static String personId;
    public static Uri personPhoto;

    public static void updateInfo(GoogleSignInAccount acct){
        personName = acct.getDisplayName();
        personGivenName = acct.getGivenName();
        personFamilyName = acct.getFamilyName();
        personEmail = acct.getEmail();
        personId = acct.getId();
        personPhoto = acct.getPhotoUrl();
    }
}
