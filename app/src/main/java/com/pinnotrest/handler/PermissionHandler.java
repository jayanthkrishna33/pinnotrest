package com.pinnotrest.handler;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class PermissionHandler {

    public static boolean hasFileWritePermission(Activity activity){
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
    public static boolean hasFileReadPermission(Activity activity){
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
    public static boolean hasInternetPermission(Activity activity){
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED);
    }


    public static void requestFileWritePermission(Activity activity,int code){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},code);
    }

    public static void requestFileReadPermission(Activity activity,int code){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},code);
    }

    public static void requestInternetPermission(Activity activity, int code) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, code);
    }

}
