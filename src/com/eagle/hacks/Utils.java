
package com.eagle.hacks;

import android.util.Log;

public class Utils {
    public static final String TAG_ITEMS = "items";
    public static final String TAG_ITEM = "item";

    public static final String ITEM_ATTR_ENABLED = "enabled";
    public static final String ITEM_ATTR_CLASS = "className";
    public static final String ITEM_ATTR_PACKAGE = "packageName";
    public static final String ITEM_ATTR_TITLE = "title";

    public static void logd(String tag, String msg) {
        Log.d(tag, msg);
    }
}
