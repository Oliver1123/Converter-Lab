package com.example.oliver.someexample.db;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

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

    private static final String TAG = FinanceContentProvider.class.getSimpleName();

    private static final int ORGANIZATIONS      = 100;
    private static final int ORGANIZATION_ID    = 101;
    private static final int ORGANIZATIONS_READABLE = 102;
    private static final int ORGANIZATION_READABLE_ID    = 103;

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

    SQLiteQueryBuilder sOrganizationsQueryBuilder;
    {
        sOrganizationsQueryBuilder = new SQLiteQueryBuilder();
        sOrganizationsQueryBuilder.setTables(
                OrganizationsEntry.TABLE_NAME + " INNER JOIN " + RegionsEntry.TABLE_NAME
                        + " ON " + OrganizationsEntry.TABLE_NAME + "." + OrganizationsEntry.COLUMN_REGION_ID + " = "
                        + RegionsEntry.TABLE_NAME + "." + RegionsEntry._ID
                        + " INNER JOIN " + CitiesEntry.TABLE_NAME
                        + " ON " + OrganizationsEntry.TABLE_NAME + "." + OrganizationsEntry.COLUMN_CITY_ID + " = "
                        + CitiesEntry.TABLE_NAME + "." + CitiesEntry._ID

        );
    }


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
        matcher.addURI(content, OrganizationsEntry.PATH_READABLE, ORGANIZATIONS_READABLE);
        matcher.addURI(content, OrganizationsEntry.PATH_READABLE_ID, ORGANIZATION_READABLE_ID);
        matcher.addURI(content, OrganizationsEntry.PATH_ID, ORGANIZATION_ID);
        matcher.addURI(content, OrganizationsEntry.PATH, ORGANIZATIONS);

        matcher.addURI(content, RegionsEntry.PATH, REGIONS);
        matcher.addURI(content, RegionsEntry.PATH_ID, REGION_ID);

        matcher.addURI(content, CitiesEntry.PATH, CITIES);
        matcher.addURI(content, CitiesEntry.PATH_ID, CITY_ID);

        matcher.addURI(content, CurrenciesInfoEntry.PATH, CURRENCIES_INFO);
        matcher.addURI(content, CurrenciesInfoEntry.PATH_ID, CURRENCY_INFO_ABB);

        matcher.addURI(content, CurrenciesDataEntry.PATH, CURRENCIES_DATA);
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
            case ORGANIZATIONS_READABLE:
                return OrganizationsEntry.CONTENT_TYPE_READABLE;
            case ORGANIZATION_READABLE_ID:
                return OrganizationsEntry.CONTENT_ITEM_TYPE_READABLE;
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

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: uri: " + uri + " match: " + sUriMatcher.match(uri));
        final SQLiteDatabase db = mFinanceDBHelper.getReadableDatabase();
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
                String orgID = OrganizationsEntry.getOrgIDFromRawUri(uri);
                retCursor = db.query(
                        OrganizationsEntry.TABLE_NAME,
                        projection,
                        OrganizationsEntry._ID + " = ?",
                        new String[]{orgID},
                        null,
                        null,
                        sortOrder
                );
                break;
            case ORGANIZATIONS_READABLE:
                retCursor = getOrganizationsReadable(projection, selection, selectionArgs, sortOrder);
                break;
            case ORGANIZATION_READABLE_ID:
                String organizationID = OrganizationsEntry.getOrgIDFromUri(uri);
                retCursor = getOrganizationByIDReadable(organizationID, projection, selection, selectionArgs, sortOrder);
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
                        RegionsEntry._ID + " = ?",
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
                        CitiesEntry._ID + " = ?",
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
                        CurrenciesInfoEntry._ID + " = ?",
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
//        db.close();
        return retCursor;

    }

    private Cursor getOrganizationByIDReadable(String organizationID, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "getOrganizationByIDReadable: id: " + organizationID);
        if (projection == null || projection.length == 0) {
            projection = OrganizationsEntry.DEFAULT_PROJECTION;
        }

        if (TextUtils.isEmpty(selection)) {
            selection = OrganizationsEntry.ORG_ID_WHERE_ARG;
            selectionArgs = new String[]{organizationID};
        } else {
            selection += " AND " + OrganizationsEntry.ORG_ID_WHERE_ARG;
            int n = selectionArgs.length + 1;
            String[] oldSelectionArgs = selectionArgs;
            selectionArgs = new String[n];
            System.arraycopy(oldSelectionArgs, 0, selectionArgs, 0, oldSelectionArgs.length);
            selectionArgs[n - 1] = organizationID;
        }
        return sOrganizationsQueryBuilder.query(mFinanceDBHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getOrganizationsReadable(String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if (projection == null || projection.length == 0) {
            projection = OrganizationsEntry.DEFAULT_PROJECTION;
        }
        return sOrganizationsQueryBuilder.query(mFinanceDBHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
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
                    returnUri =  OrganizationsEntry.buildOrganizationRawUri(values.getAsString(OrganizationsEntry._ID));
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case REGIONS:
                _id = db.insert(RegionsEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  RegionsEntry.buildRegionUri(values.getAsString(RegionsEntry._ID));
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case CITIES:
                _id = db.insert(CitiesEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  CitiesEntry.buildCityUri(values.getAsString(CitiesEntry._ID));
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case CURRENCIES_INFO:
                _id = db.insert(CurrenciesInfoEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri =  CurrenciesInfoEntry.buildCurrencyInfoUri(values.getAsString(CurrenciesInfoEntry._ID));
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
