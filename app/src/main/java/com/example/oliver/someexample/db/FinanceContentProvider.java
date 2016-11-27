package com.example.oliver.someexample.db;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.oliver.someexample.db.FinanceDBContract.CitiesEntry;
import com.example.oliver.someexample.db.FinanceDBContract.CurrenciesDataEntry;
import com.example.oliver.someexample.db.FinanceDBContract.CurrenciesInfoEntry;
import com.example.oliver.someexample.db.FinanceDBContract.OrganizationsEntry;
import com.example.oliver.someexample.db.FinanceDBContract.RegionsEntry;

/**
 * Created by zolotar on 26/11/16.
 */

public class FinanceContentProvider extends ContentProvider {
    // Use an int for each URI we will run, this represents the different queries
    private static final int ORGANIZATIONS      = 100;
    private static final int ORGANIZATION_ID    = 101;

    private static final int REGIONS            = 200;
    private static final int REGION_ID          = 201;

    private static final int CITIES             = 300;
    private static final int CITY_ID            = 301;

    private static final int CURRENCIES_INFO    = 400;
    private static final int CURRENCY_INFO_ABB   = 401;

    private static final int CURRENCIES_DATA            = 500;
//    private static final int CURRENCY_DATA_BY_ORG_ID    = 501;
//    private static final int CURRENCY_DATA_BY_DATE      = 502;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FinanceDBHelper mFinanceDBHelper;


    @Override
    public boolean onCreate() {
        mFinanceDBHelper = new FinanceDBHelper(getContext());
        return false;
    }

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */
    public static UriMatcher buildUriMatcher(){
        String content = FinanceDBContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, FinanceDBContract.PATH_ORGANIZATION, ORGANIZATIONS);
        matcher.addURI(content, FinanceDBContract.PATH_ORGANIZATION + "/*", ORGANIZATION_ID);
        matcher.addURI(content, FinanceDBContract.PATH_REGION, REGIONS);
        matcher.addURI(content, FinanceDBContract.PATH_REGION + "/*", REGION_ID);
        matcher.addURI(content, FinanceDBContract.PATH_CITY, CITIES);
        matcher.addURI(content, FinanceDBContract.PATH_CITY + "/*", CITY_ID);
        matcher.addURI(content, FinanceDBContract.PATH_CURRENCY_INFO, CURRENCIES_INFO);
        matcher.addURI(content, FinanceDBContract.PATH_CURRENCY_INFO + "/*", CURRENCY_INFO_ABB);
        matcher.addURI(content, FinanceDBContract.PATH_CURRENCY_DATA, CURRENCIES_DATA);
//        matcher.addURI(content, FinanceDBContract.PATH_CURRENCY_DATA + "/byOrg/*", CURRENCY_DATA_BY_ORG_ID);
//        matcher.addURI(content, FinanceDBContract.PATH_CURRENCY_DATA + "/byDate/*", CURRENCY_DATA_BY_DATE);

        return matcher;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch(sUriMatcher.match(uri)){
            case ORGANIZATIONS:
                return OrganizationsEntry.CONTENT_TYPE;
            case ORGANIZATION_ID:
                return OrganizationsEntry.CONTENT_ITEM_TYPE;
            case REGIONS:
                return RegionsEntry.CONTENT_TYPE;
            case REGION_ID:
                return RegionsEntry.CONTENT_ITEM_TYPE;
            case CITIES:
                return CitiesEntry.CONTENT_TYPE;
            case CITY_ID:
                return CitiesEntry.CONTENT_ITEM_TYPE;
            case CURRENCIES_INFO:
                return CurrenciesInfoEntry.CONTENT_TYPE;
            case CURRENCY_INFO_ABB:
                return CurrenciesInfoEntry.CONTENT_ITEM_TYPE;
            case CURRENCIES_DATA:
                return CurrenciesDataEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }


    // todo handle queries
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mFinanceDBHelper.getWritableDatabase();
        Cursor retCursor;
        switch(sUriMatcher.match(uri)) {
            case ORGANIZATIONS:
                retCursor = db.query(
                        OrganizationsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ORGANIZATION_ID:
                String orgID = OrganizationsEntry.getOrgIDFromUri(uri);
                retCursor = getOrganizationByID(db, orgID, projection, selection, selectionArgs, sortOrder);
                break;
            case REGIONS:
                retCursor = db.query(
                        RegionsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case REGION_ID:
                String regionID = RegionsEntry.getRegionIDFromUri(uri);
                retCursor = db.query(
                        RegionsEntry.TABLE_NAME,
                        projection,
                        RegionsEntry.COLUMN_REGION_ID + " = ?",
                        new String[]{regionID},
                        null,
                        null,
                        sortOrder
                );
                break;

            case CITIES:
                retCursor = db.query(
                        CitiesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CITY_ID:
                String cityID = CitiesEntry.getCityIDFromUri(uri);
                retCursor = db.query(
                        CitiesEntry.TABLE_NAME,
                        projection,
                        CitiesEntry.COLUMN_CITY_ID + " = ?",
                        new String[]{cityID},
                        null,
                        null,
                        sortOrder
                );
                break;
            case CURRENCIES_INFO:
                retCursor = db.query(
                        CurrenciesInfoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CURRENCY_INFO_ABB:
                String currencyAbb = CurrenciesInfoEntry.getCurrencyABBFromUri(uri);
                retCursor = db.query(
                        CurrenciesInfoEntry.TABLE_NAME,
                        projection,
                        CurrenciesInfoEntry.COLUMN_ABB + " = ?",
                        new String[]{currencyAbb},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        db.close();
        return retCursor;

    }

    private Cursor getOrganizationByID(SQLiteDatabase db, String orgID, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        return db.query(
//                OrganizationsEntry.TABLE_NAME,
//                projection,
//                OrganizationsEntry._ID + " = ?",
//                new String[]{String.valueOf(_id)},
//                null,
//                null,
//                sortOrder
//        );
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mFinanceDBHelper.getWritableDatabase();
        long _id;
        Uri returnUri;
        switch(sUriMatcher.match(uri)){
            case ORGANIZATIONS:
                _id = db.insert(OrganizationsEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  OrganizationsEntry.buildOrganizationUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case REGIONS:
                _id = db.insert(RegionsEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  RegionsEntry.buildRegionUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case CITIES:
                _id = db.insert(CitiesEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  CitiesEntry.buildCityUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case CURRENCIES_INFO:
                _id = db.insert(CurrenciesInfoEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  CurrenciesInfoEntry.buildCurrencyInfoUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case CURRENCIES_DATA:
                _id = db.insert(CurrenciesDataEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  CurrenciesDataEntry.buildCurrencyDataUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;

    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int numInserted = 0;
        String table;

        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case ORGANIZATIONS:
                table = OrganizationsEntry.TABLE_NAME;
                break;
            case REGIONS:
                table = RegionsEntry.TABLE_NAME;
                break;
            case CITIES:
                table = CitiesEntry.TABLE_NAME;
                break;
            case CURRENCIES_INFO:
                table = CurrenciesInfoEntry.TABLE_NAME;
                break;
             case CURRENCIES_DATA:
                table = CurrenciesDataEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        SQLiteDatabase sqlDB = mFinanceDBHelper.getWritableDatabase();
        sqlDB.beginTransaction();
        try {
            for (ContentValues cv : values) {
                long newID = sqlDB.insertOrThrow(table, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            sqlDB.endTransaction();
        }
        return numInserted;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mFinanceDBHelper.getWritableDatabase();
        int rows; // Number of rows effected
        String table;
        switch(sUriMatcher.match(uri)){
            case ORGANIZATIONS:
                table = OrganizationsEntry.TABLE_NAME;
                break;
            case REGIONS:
                table = RegionsEntry.TABLE_NAME;
                break;
            case CITIES:
                table = CitiesEntry.TABLE_NAME;
                break;
            case CURRENCIES_INFO:
                table = CitiesEntry.TABLE_NAME;
                break;
            case CURRENCIES_DATA:
                table = CitiesEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        rows = db.delete(table, selection, selectionArgs);
        // Because null could delete all rows:
        if(selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rows;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mFinanceDBHelper.getWritableDatabase();
        int rows;
        String table;
        switch(sUriMatcher.match(uri)){
            case ORGANIZATIONS:
                table = OrganizationsEntry.TABLE_NAME;
                break;
            case REGIONS:
                table = RegionsEntry.TABLE_NAME;
                break;
            case CITIES:
                table = CitiesEntry.TABLE_NAME;
                break;
            case CURRENCIES_INFO:
                table = CitiesEntry.TABLE_NAME;
                break;
            case CURRENCIES_DATA:
                table = CitiesEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        rows = db.update(table, values, selection, selectionArgs);
        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rows;
    }


    @Override
    @TargetApi(11)
    public void shutdown() {
        mFinanceDBHelper.close();
        super.shutdown();
    }
}
