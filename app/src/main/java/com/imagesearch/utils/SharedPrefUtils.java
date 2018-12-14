package com.imagesearch.utils;

import android.content.SharedPreferences;

import com.imagesearch.ImageSearchApp;

/**
 * Created by Varun on 27,July,2018
 */

public class SharedPrefUtils {

    public static final String COLUMNS_NO_SELECTED = "columns_selected";

    public static void setIntVal(String key, int value) {

        SharedPreferences prefs = ImageSearchApp.getSharedPrefs();
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(key, value);

        prefsEditor.commit();
    }

    public static int getIntVal(String key) {

        SharedPreferences prefs = ImageSearchApp.getSharedPrefs();
        int json = prefs.getInt(key, 0);
        return json;
    }

}
