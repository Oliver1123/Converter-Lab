package com.example.oliver.someexample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.oliver.someexample.db.FinanceDBContract.CitiesEntry;
import com.example.oliver.someexample.db.FinanceDBContract.CurrenciesDataEntry;
import com.example.oliver.someexample.db.FinanceDBContract.CurrenciesInfoEntry;
import com.example.oliver.someexample.db.FinanceDBContract.OrganizationsEntry;
import com.example.oliver.someexample.db.FinanceDBContract.RegionsEntry;


/**
 * Created by oliver on 13.09.15.
 */
public class FinanceDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_finance";

    private static final int DB_VERSION = 5;

    private static final String ORGANIZATIONS_TABLE_CREATE =
            "CREATE TABLE " + OrganizationsEntry.TABLE_NAME + " (" +
                    OrganizationsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    OrganizationsEntry.COLUMN_ORG_ID     + " TEXT UNIQUE NOT NULL, " +
                    OrganizationsEntry.COLUMN_TITLE      + " TEXT, " +
                    OrganizationsEntry.COLUMN_REGION_ID  + " TEXT NOT NULL, " +
                    OrganizationsEntry.COLUMN_CITY_ID    + " TEXT NOT NULL, " +
                    OrganizationsEntry.COLUMN_PHONE      + " TEXT, " +
                    OrganizationsEntry.COLUMN_ADDRESS    + " TEXT, " +
                    OrganizationsEntry.COLUMN_LINK       + " TEXT," +
                    " UNIQUE (" + OrganizationsEntry.COLUMN_ORG_ID + ") ON CONFLICT REPLACE);";

    private static final String REGIONS_TABLE_CREATE =
            "CREATE TABLE " + RegionsEntry.TABLE_NAME + " (" +
                    RegionsEntry._ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RegionsEntry.COLUMN_REGION_ID + " TEXT UNIQUE NOT NULL, " +
                    RegionsEntry.COLUMN_TITLE    + " TEXT," +
                    " UNIQUE (" + RegionsEntry.COLUMN_REGION_ID + ") ON CONFLICT REPLACE);";


    private static final String CITIES_TABLE_CREATE =
            "CREATE TABLE " + CitiesEntry.TABLE_NAME + " (" +
                    CitiesEntry._ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CitiesEntry.COLUMN_CITY_ID + " TEXT UNIQUE NOT NULL, " +
                    CitiesEntry.COLUMN_TITLE     + " TEXT, " +
                    " UNIQUE (" + CitiesEntry.COLUMN_CITY_ID + ") ON CONFLICT REPLACE);";

    private static final String CURRENCIES_INFO_TABLE_CREATE =
            "CREATE TABLE " + CurrenciesInfoEntry.TABLE_NAME + " (" +
                    CurrenciesInfoEntry._ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CurrenciesInfoEntry.COLUMN_ABB       + " TEXT UNIQUE NOT NULL, " +
                    CurrenciesInfoEntry.COLUMN_TITLE     + " TEXT, " +
                    " UNIQUE (" + CurrenciesInfoEntry.COLUMN_ABB + ") ON CONFLICT REPLACE);";

    private static final String CURRENCIES_DATA_TABLE_CREATE =
            "CREATE TABLE " + CurrenciesDataEntry.TABLE_NAME + " (" +
                    CurrenciesDataEntry._ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CurrenciesDataEntry.COLUMN_ORG_ID        + " TEXT, " +
                    CurrenciesDataEntry.COLUMN_CURRENCY_ABB  + " TEXT," +
                    CurrenciesDataEntry.COLUMN_ASK           + " TEXT," +
                    CurrenciesDataEntry.COLUMN_BID           + " TEXT," +
                    CurrenciesDataEntry.COLUMN_DATE          + " TEXT," +

                    // it's created a UNIQUE constraint with REPLACE strategy
                    " UNIQUE (" + CurrenciesDataEntry.COLUMN_ORG_ID + ", " +
                    CurrenciesDataEntry.COLUMN_CURRENCY_ABB + ", " +
                    CurrenciesDataEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";



    public FinanceDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ORGANIZATIONS_TABLE_CREATE);
        db.execSQL(REGIONS_TABLE_CREATE);
        db.execSQL(CITIES_TABLE_CREATE);
        db.execSQL(CURRENCIES_INFO_TABLE_CREATE);
        db.execSQL(CURRENCIES_DATA_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OrganizationsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RegionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CitiesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CurrenciesInfoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CurrenciesDataEntry.TABLE_NAME);
        onCreate(db);
    }
}
