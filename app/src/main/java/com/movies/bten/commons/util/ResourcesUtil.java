package com.movies.bten.commons.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: Progen
 * Date: 23/11/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class ResourcesUtil {
    private static Resources resources;


    public static void initializeResourcesUtil(Resources res) {
        resources = res;
    }


    public static String getString(int id) {
        return resources.getString(id);
    }


    public static boolean getBoolean(int id) {
        return resources.getBoolean(id);
    }


    public static int getColor(int id) {
        return resources.getColor(id);
    }


    public static float getDimension(int id) {
        return resources.getDimension(id);
    }


    public static Drawable getDrawable(int id) {
        return resources.getDrawable(id);
    }


    public static int getInteger(int id) {
        return resources.getInteger(id);
    }


    public static int[] getIntArray(int id) {
        return resources.getIntArray(id);
    }


    public static String[] getStringArray(int id) {
        return resources.getStringArray(id);
    }


}
