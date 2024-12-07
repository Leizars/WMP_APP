package com.apaaja.eventapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String Key_Username = "user";
    static final String Key_Password = "pass";
    static final String Key_Status_Login = "status";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUsername(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key_Username, username);
        editor.apply();
    }

    public static String getUsername(Context context) {
        return getSharedPreference(context).getString(Key_Username, "");
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key_Password, password);
        editor.apply();
    }

    public static String getPassword(Context context) {
        return getSharedPreference(context).getString(Key_Password, "");
    }

    public static void setStatusLogin(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Key_Status_Login, status);
        editor.apply();
    }

    public static boolean getStatusLogin(Context context) {
        return getSharedPreference(context).getBoolean(Key_Status_Login, false);
    }

    public static void clearLoginStatus(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(Key_Username);
        editor.remove(Key_Password);
        editor.remove(Key_Status_Login);
        editor.apply();
    }
}
