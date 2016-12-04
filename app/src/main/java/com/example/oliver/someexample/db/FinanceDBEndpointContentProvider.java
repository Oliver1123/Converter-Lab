package com.example.oliver.someexample.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.oliver.someexample.db.FinanceDBContract.CitiesEntry;
import com.example.oliver.someexample.db.FinanceDBContract.CurrenciesDataEntry;
import com.example.oliver.someexample.db.FinanceDBContract.CurrenciesInfoEntry;
import com.example.oliver.someexample.db.FinanceDBContract.OrganizationsEntry;
import com.example.oliver.someexample.db.FinanceDBContract.RegionsEntry;
import com.example.oliver.someexample.models.pojo.CurrencyPOJO;
import com.example.oliver.someexample.models.pojo.FinanceSnapshotPOJO;
import com.example.oliver.someexample.models.pojo.OrganizationPOJO;

import java.util.ArrayList;
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
            value.put(RegionsEntry._ID, pair.getKey());
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
            value.put(CitiesEntry._ID, pair.getKey());
            value.put(CitiesEntry.COLUMN_TITLE, pair.getValue());
            values[i++] = value;
        }
        mContext.getContentResolver().bulkInsert(CitiesEntry.CONTENT_URI, values);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertCities: total " + cities.size() + " take: " + (endTime - startTime) + " ms");
    }
    @Override
    public void insertCurrenciesInfo(Map<String, String> currenciesInfoMap) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] values = new ContentValues[currenciesInfoMap.size()];
        int i = 0;
        for (Map.Entry<String, String> pair : currenciesInfoMap.entrySet()) {
            ContentValues value = new ContentValues();
            value.put(CurrenciesInfoEntry._ID, pair.getKey());
            value.put(CurrenciesInfoEntry.COLUMN_TITLE, pair.getValue());
            values[i++] = value;
        }
        mContext.getContentResolver().bulkInsert(CurrenciesInfoEntry.CONTENT_URI, values);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertCurrenciesInfo: total " + currenciesInfoMap.size() + " take: " + (endTime - startTime) + " ms");
    }

    @Override
    public void insertOrganizations(List<OrganizationPOJO> organizationsList) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] values = new ContentValues[organizationsList.size()];
        int i = 0;

        for (OrganizationPOJO model : organizationsList) {
            ContentValues value = new ContentValues();
            value.put(OrganizationsEntry._ID,         model.id);
            value.put(OrganizationsEntry.COLUMN_TITLE,      model.title);
            value.put(OrganizationsEntry.COLUMN_REGION_ID,  model.regionId);
            value.put(OrganizationsEntry.COLUMN_CITY_ID,    model.cityId);
            value.put(OrganizationsEntry.COLUMN_PHONE,      model.phone);
            value.put(OrganizationsEntry.COLUMN_ADDRESS,    model.address);
            value.put(OrganizationsEntry.COLUMN_LINK,   model.link);
            values[i++] = value;
            Log.d(TAG, "insertOrganizations: value : " + value + " id: " + model.id);
            mContext.getContentResolver().insert(OrganizationsEntry.CONTENT_URI, value);
        }
//        mContext.getContentResolver().bulkInsert(OrganizationsEntry.CONTENT_URI, values);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertOrganizations: total " + organizationsList.size() + " take: " + (endTime - startTime) + " ms");
    }

    @Override
    public void insertCurrenciesData(String orgID, Map<String, CurrencyPOJO> currencies, String date) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] currencyValues = new ContentValues[currencies.size()];
        int i = 0;
        for (Map.Entry<String, CurrencyPOJO> currency : currencies.entrySet()) {
            ContentValues value = new ContentValues();
            value.put(CurrenciesDataEntry.COLUMN_ORG_ID, orgID);
            value.put(CurrenciesDataEntry.COLUMN_CURRENCY_ABB, currency.getKey());
            value.put(CurrenciesDataEntry.COLUMN_ASK, currency.getValue().ask);
            value.put(CurrenciesDataEntry.COLUMN_BID,  currency.getValue().bid);
            value.put(CurrenciesDataEntry.COLUMN_DATE, date);
            currencyValues[i++] = value;
        }
        mContext.getContentResolver().bulkInsert(CurrenciesDataEntry.CONTENT_URI, currencyValues);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertCurrenciesData for org: " + orgID + " total " + currencies.size() + " take: " + (endTime - startTime) + " ms");
    }

    @Override
    public void insertFinanceSnapshot(FinanceSnapshotPOJO financeSnapshot) {
        insertRegions(financeSnapshot.regions);
        insertCities(financeSnapshot.cities);
        insertCurrenciesInfo(financeSnapshot.currencies);

        insertOrganizationsWithCurrencies(financeSnapshot);
    }

    /**
     * Insert data in table organizations info and currencies data on a single pass
     * @param financeSnapshot
     */
    private void insertOrganizationsWithCurrencies(FinanceSnapshotPOJO financeSnapshot) {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        ContentValues[] orgValues = new ContentValues[financeSnapshot.organizations.size()];
        int i = 0;

        ArrayList<ContentValues> currenciesDataValues = new ArrayList<>();
        for (OrganizationPOJO model : financeSnapshot.organizations) {
            ContentValues value = new ContentValues();
            value.put(OrganizationsEntry._ID,         model.id);
            value.put(OrganizationsEntry.COLUMN_TITLE,      model.title);
            value.put(OrganizationsEntry.COLUMN_REGION_ID,  model.regionId);
            value.put(OrganizationsEntry.COLUMN_CITY_ID,    model.cityId);
            value.put(OrganizationsEntry.COLUMN_PHONE,      model.phone);
            value.put(OrganizationsEntry.COLUMN_ADDRESS,    model.address);
            value.put(OrganizationsEntry.COLUMN_LINK,   model.link);
            orgValues[i++] = value;
//            Log.d(TAG, "insertOrganizationsWithCurrencies: " + model.title + " currencies: " + model.currencies);
            updateCurrenciesContentValuesList(currenciesDataValues, model.id, model.currencies, financeSnapshot.date.getTime());
        }
        mContext.getContentResolver().bulkInsert(OrganizationsEntry.CONTENT_URI, orgValues);

        ContentValues[] currenciesValuesArray = new ContentValues[currenciesDataValues.size()];
        currenciesValuesArray = currenciesDataValues.toArray(currenciesValuesArray);
        mContext.getContentResolver().bulkInsert(CurrenciesDataEntry.CONTENT_URI, currenciesValuesArray);
        endTime = System.currentTimeMillis();
        Log.d(TAG, "insertOrganizationsWithCurrencies: total " + financeSnapshot.organizations.size() + " take: " + (endTime - startTime) + " ms");
    }

    private void updateCurrenciesContentValuesList(List<ContentValues> currenciesList, String orgID, Map<String, CurrencyPOJO> currencies, long date) {
        for (Map.Entry<String, CurrencyPOJO> currency : currencies.entrySet()) {
            ContentValues value = new ContentValues();
            value.put(CurrenciesDataEntry.COLUMN_ORG_ID, orgID);
            value.put(CurrenciesDataEntry.COLUMN_CURRENCY_ABB, currency.getKey());
            value.put(CurrenciesDataEntry.COLUMN_ASK, currency.getValue().ask);
            value.put(CurrenciesDataEntry.COLUMN_BID,  currency.getValue().bid);
            value.put(CurrenciesDataEntry.COLUMN_DATE, date);
            currenciesList.add(value);
        }
    }
}
