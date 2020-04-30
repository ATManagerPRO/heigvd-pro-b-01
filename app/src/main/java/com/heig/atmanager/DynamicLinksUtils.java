package com.heig.atmanager;

import android.net.Uri;

import com.heig.atmanager.BuildConfig;

/**
 * Author : Chau Ying Kot
 * Date   : 30.04.2020
 **/
public class DynamicLinksUtils {

    public static Uri generateContentLink() {


        String appCode = "id";
        final Uri deepLink = Uri.parse("http://heig.com/share?task");

        String packageName = BuildConfig.APPLICATION_ID;

        // Build the link with all required parameters
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(appCode + ".atmanger")
                .path("/")
                .appendQueryParameter("link", deepLink.toString())
                .appendQueryParameter("apn", packageName);

        return builder.build();

    }
}
