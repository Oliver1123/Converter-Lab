package com.example.oliver.someexample.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oliver on 13.09.15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_finance";

    private static final int DB_VERSION = 1;
    public static final String PRIMARY_KEY = "_id";
//                  ORGANIZATION TABLE INFO
    public static final String ORGANIZATIONS_TABLE_NAME = "organization";

    public static final String ORGANIZATION_ID              = "org_id";
    public static final String ORGANIZATION_TITLE           = "title";
    public static final String ORGANIZATION_REGION_ID       = "region_id";
    public static final String ORGANIZATION_CITY_ID         = "city_id";
    public static final String ORGANIZATION_PHONE           = "phone";
    public static final String ORGANIZATION_ADDRESS         = "address";
    public static final String ORGANIZATION_LINK            = "link";

    private static final String ORGANIZATIONS_TABLE_CREATE =
            "CREATE TABLE " + ORGANIZATIONS_TABLE_NAME + " (" +
                    PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORGANIZATION_ID         + " TEXT, " +
                    ORGANIZATION_TITLE      + " TEXT, " +
                    ORGANIZATION_REGION_ID  + " TEXT, " +
                    ORGANIZATION_CITY_ID    + " TEXT, " +
                    ORGANIZATION_PHONE      + " TEXT, " +
                    ORGANIZATION_ADDRESS    + " TEXT, " +
                    ORGANIZATION_LINK       + " TEXT);";

//                  REGIONS TABLE INFO
    public static final String REGIONS_TABLE_NAME = "regions";

    public static final String REGION_ID              = "region_id";
    public static final String REGION_TITLE           = "title";

    private static final String REGIONS_TABLE_CREATE =
            "CREATE TABLE " + REGIONS_TABLE_NAME + " (" +
                    PRIMARY_KEY         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    REGION_ID           + " TEXT, " +
                    REGION_TITLE        + " TEXT);";

//                  CITIES TABLE INFO
    public static final String CITIES_TABLE_NAME = "cities";

    public static final String CITY_ID              = "city_id";
    public static final String CITY_TITLE           = "title";

    private static final String CITIES_TABLE_CREATE =
            "CREATE TABLE " + CITIES_TABLE_NAME + " (" +
                    PRIMARY_KEY     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITY_ID         + " TEXT, " +
                    CITY_TITLE      + " TEXT);";

//                  CURRENCIES TABLE INFO
    public static final String CURRENCIES_TABLE_NAME    = "currencies";

    public static final String CURRENCY_ABB             = "abbreviation";
    public static final String CURRENCY_TITLE           = "title";

    private static final String CURRENCIES_TABLE_CREATE =
            "CREATE TABLE " + CURRENCIES_TABLE_NAME + " (" +
                    PRIMARY_KEY          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CURRENCY_ABB         + " TEXT, " +
                    CURRENCY_TITLE       + " TEXT);";

//                  CURRENCIES for ORGANIZATIONS  TABLE INFO
    public static final String CURRENCIES4ORG_TABLE_NAME    = "currencies4ORG";

    public static final String CURRENCIES4ORG_ID            = "org_id";
    public static final String CURRENCIES4ORG_ABB           = "abb";
    public static final String CURRENCIES4ORG_ASK           = "ask";
    public static final String CURRENCIES4ORG_BID           = "bid";
    public static final String CURRENCIES4ORG_ASK_RATE      = "ask_rate";
    public static final String CURRENCIES4ORG_BID_RATE      = "bid_rate";

    private static final String CURRENCIES4ORG_TABLE_CREATE =
            "CREATE TABLE " + CURRENCIES4ORG_TABLE_NAME + " (" +
                    PRIMARY_KEY              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CURRENCIES4ORG_ID        + " TEXT, " +
                    CURRENCIES4ORG_ABB       + " TEXT, " +
                    CURRENCIES4ORG_ASK       + " TEXT, " +
                    CURRENCIES4ORG_BID       + " TEXT, " +
                    CURRENCIES4ORG_ASK_RATE  + " INTEGER, " +
                    CURRENCIES4ORG_BID_RATE  + " INTEGER);";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ORGANIZATIONS_TABLE_CREATE);
        db.execSQL(REGIONS_TABLE_CREATE);
        db.execSQL(CITIES_TABLE_CREATE);
        db.execSQL(CURRENCIES_TABLE_CREATE);
        db.execSQL(CURRENCIES4ORG_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
