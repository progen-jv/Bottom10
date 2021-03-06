package com.movies.bten.commons.util;

import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: Progen
 * Date: 27/7/13
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class AppPreferenceManager {
    private static SharedPreferences preferences;

    // The list of keys may be very huge, therefore we need to keep them sorted.
    public interface Keys {
        public final String APP_NAME = "AppName";
    }

    public static void initializeDivaPreferenceManager(SharedPreferences _preferences) {
        preferences = _preferences;
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }


    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }


    public static void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }


    public static void setLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }


    public static void setFloat(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public static String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }


    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}