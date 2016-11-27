package com.example.oliver.someexample.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.oliver.someexample.db.FinanceDBContract.CitiesEntry;
import com.example.oliver.someexample.db.FinanceDBContract.OrganizationsEntry;
import com.example.oliver.someexample.db.FinanceDBContract.RegionsEntry;
import com.example.oliver.someexample.models.pojo.OrganizationPOJOModel;

import java.util.List;
import java.util.Map;


/**
 * Created by zolotar on 26/11/16.
 */

public class FinanceDBEndpointContentProvider implements FinanceDBEndpoint{

    private static final String TAG = FinanceDBEndpointContentProvider.class.getSimpleName();
    private final Context mContext;

    public FinanceDBEndpointContentProvider(Context context) {
        mContext = context;
    }

    @Override
    public void insertRegions(Map<String, String> regions) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] values = new ContentValues[regions.size()];
        int i = 0;
        for (Map.Entry<String, String> pair : regions.entrySet()) {
            ContentValues value = new ContentValues();
            value.put(RegionsEntry.COLUMN_ID, pair.getKey());
            value.put(RegionsEntry.COLUMN_TITLE, pair.getValue());
            values[i++] = value;

        }
        mContext.getContentResolver().bulkInsert(RegionsEntry.CONTENT_URI, values);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertRegions: total " + regions.size() + " take: " + (endTime - startTime) + " ms");
    }

    @Override
    public void insertCities(Map<String, String> cities) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] values = new ContentValues[cities.size()];
        int i = 0;
        for (Map.Entry<String, String> pair : cities.entrySet()) {
            ContentValues value = new ContentValues();
            value.put(CitiesEntry.COLUMN_ID, pair.getKey());
            value.put(CitiesEntry.COLUMN_TITLE, pair.getValue());
            values[i++] = value;
        }
        mContext.getContentResolver().bulkInsert(CitiesEntry.CONTENT_URI, values);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertCities: total " + cities.size() + " take: " + (endTime - startTime) + " ms");
    }

    @Override
    public void insertOrganizations(List<OrganizationPOJOModel> organizationsList) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] values = new ContentValues[organizationsList.size()];
        int i = 0;

        for (OrganizationPOJOModel model : organizationsList) {
            ContentValues value = new ContentValues();
            value.put(OrganizationsEntry.COLUMN_ID,         model.id);
            value.put(OrganizationsEntry.COLUMN_TITLE,      model.title);
            value.put(OrganizationsEntry.COLUMN_REGION_ID,  model.regionId);
            value.put(OrganizationsEntry.COLUMN_CITY_ID,    model.cityId);
            value.put(OrganizationsEntry.COLUMN_PHONE,      model.phone);
            value.put(OrganizationsEntry.COLUMN_ADDRESS,    model.address);
            value.put(OrganizationsEntry.COLUMN_LINK,   model.link);
            values[i++] = value;
        }
        mContext.getContentResolver().bulkInsert(OrganizationsEntry.CONTENT_URI, values);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertOrganizations: total " + organizationsList.size() + " take: " + (endTime - startTime) + " ms");
    }

    //    @Override
//    public void insertRegions(Map<String, String> regions) {
//        int regionsSize = regions.size();
//        ContentValues[] values = new ContentValues[regionsSize];
//        int i = 0;
//        for (Map.Entry<String, String> pair : regions.entrySet()) {
//            values[i++] = regionsToContentValue(pair.getKey(), pair.getValue());
//        }
//
//        long startTime, endTime;
//
//        startTime = System.currentTimeMillis();
//        for (i = 0; i < regionsSize; i++) {
//            mContext.getContentResolver().insert(RegionsEntry.CONTENT_URI, values[i]);
//        }
//        endTime = System.currentTimeMillis();
//        Log.d(TAG, "insertRegions: cycle : " + (endTime - startTime) + " ms");
//
//        startTime = System.currentTimeMillis();
//        mContext.getContentResolver().bulkInsert(RegionsEntry.CONTENT_URI, values);
//        endTime = System.currentTimeMillis();
//
//        Log.d(TAG, "insertRegions: bulkInser : " + (endTime - startTime) + " ms");
//    }

//    private ContentValues regionsToContentValue(String regionID, String regionTitle) {
//        ContentValues values = new ContentValues();
//        values.put(RegionsEntry.COLUMN_ID, regionID);
//        values.put(RegionsEntry.COLUMN_TITLE, regionTitle);
//        return values;
//    }
}
