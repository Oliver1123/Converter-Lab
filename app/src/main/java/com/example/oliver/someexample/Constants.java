package com.example.oliver.someexample;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by oliver on 14.09.15.
 */
public class Constants {
    public static final String ORG_INFO_MODEL_ARG = "org_info_model_arg";
    public static final String SHARE_FILE_NAME_ARG = "currencies_map_arg";
    public static final String ACTION_LOADING_CALLBACK = "loading_callback";

    public static final String LAST_DATE_UPGRADE = "lastDate";
    public static final String ORG_ID_ARG = "org_id_arg";
    public static final String TAG = "tag";
    public static final String DB_TAG = "db";
    public static final int MENU_ITEM_MAP   = 100;
    public static final int MENU_ITEM_LINK  = 101;
    public static final int MENU_ITEM_PHONE = 102;
    public static final int MENU_ITEM_MORE  = 103;
    public static final int NOTIFICATION_ID = 111;
    public static final String UNKNOWN_VALUE = "-";

    public static String getLastUpgradeDate(Context _context) {
        SharedPreferences sPref = _context.getSharedPreferences(_context.getPackageName(), Context.MODE_PRIVATE);
        return sPref.getString(Constants.LAST_DATE_UPGRADE, "");
    }

    public static void setLastUpgradeDate(Context _context, String lastDate) {
        SharedPreferences sPref = _context.getSharedPreferences(_context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constants.LAST_DATE_UPGRADE, lastDate);
        ed.apply();
    }
}
