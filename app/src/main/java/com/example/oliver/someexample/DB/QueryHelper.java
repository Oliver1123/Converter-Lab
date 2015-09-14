package com.example.oliver.someexample.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.Model.MoneyModel;
import com.example.oliver.someexample.Model.ObjectModel;
import com.example.oliver.someexample.Model.OrganizationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 14.09.15.
 */
public class QueryHelper {
    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDataBase;

    public QueryHelper(Context context) {
        mContext = context;
    }

    public void open(){
        mDBHelper = new DBHelper(mContext);
        mDataBase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        if (mDBHelper != null) mDBHelper.close();
    }

    /**
     * Clear base
     */
    public void clearAll() {
        clearRegions();
        clearCities();
        clearCurrencies();
        clearCurrencies4ORG();
        clearOrganizations();
    }


    ////////////////////////    CURRENCIES
    private void clearCurrencies() {
        mDataBase.delete(DBHelper.CURRENCIES_TABLE_NAME, null, null);
    }

    private void insertCurrency(Map.Entry<String, String> currency) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.CURRENCY_ABB,   currency.getKey());
            cv.put(DBHelper.CURRENCY_TITLE, currency.getValue());

            mDataBase.insert(DBHelper.CURRENCIES_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted currency abb: " + currency.getKey() + ", title: " + currency.getValue();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, resultTAG + " == endTransaction");
        }
    }

    private void insertCurrencies(Map<String, String> currencies) {
        for (Map.Entry<String, String> entry: currencies.entrySet()) {
            insertCurrency(entry);
        }
        Log.d(Constants.TAG, "Total inserted " + currencies.size() + "currencies");
    }

    ////////////////////////    REGIONS
    private void clearRegions() {
        mDataBase.delete(DBHelper.REGIONS_TABLE_NAME, null, null);
    }

    private void insertRegion(Map.Entry<String, String> region) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.REGION_ID,      region.getKey());
            cv.put(DBHelper.REGION_TITLE,   region.getValue());

            mDataBase.insert(DBHelper.REGIONS_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted region id: " + region.getKey() + ", title: " + region.getValue();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, resultTAG + " == endTransaction");
        }
    }

    private void insertRegions(Map<String, String> regions) {
        for (Map.Entry<String, String> entry: regions.entrySet()) {
            insertRegion(entry);
        }
        Log.d(Constants.TAG, "Total inserted " + regions.size() + "regions");
    }
////////////////////////    CITIES

    private void clearCities () {
        mDataBase.delete(DBHelper.CITIES_TABLE_NAME, null, null);
    }

    private void insertCity(Map.Entry<String, String> city) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.CITY_ID, city.getKey());
            cv.put(DBHelper.CITY_TITLE, city.getValue());

            mDataBase.insert(DBHelper.CITIES_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted city id: " + city.getKey() + ", title: " + city.getValue();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, resultTAG + " == endTransaction");
        }
    }

    private void insertCities(Map<String, String> cities) {
        for (Map.Entry<String, String> entry : cities.entrySet()) {
            insertCity(entry);
        }
        Log.d(Constants.TAG, "Total inserted " + cities.size() + "cities");
    }
///////////////////////// CURRENCIES4ORG

    private void clearCurrencies4ORG() {
        mDataBase.delete(DBHelper.CURRENCIES4ORG_TABLE_NAME, null, null);
    }

    private void deleteCurrencies4ORG(String organizationID) {
        int deleted = mDataBase.delete(DBHelper.CURRENCIES4ORG_TABLE_NAME,
                                       DBHelper.CURRENCIES4ORG_ID + " = ?",
                                       new String[]{organizationID});
        Log.d(Constants.TAG, "Currencies4ORG id: " + organizationID + " deleted " + deleted + "records");
    }

    private void insertCurrency4ORG(String orgID, String currencyABB, MoneyModel value) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.CURRENCIES4ORG_ID,  orgID);
            cv.put(DBHelper.CURRENCIES4ORG_ABB, currencyABB);
            cv.put(DBHelper.CURRENCIES4ORG_ASK, value.ask);
            cv.put(DBHelper.CURRENCIES4ORG_BID, value.bid);

            mDataBase.insert(DBHelper.CURRENCIES4ORG_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted currency4ORG id: " + orgID + ", ABB: " + currencyABB + "[" + value + "]";
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, resultTAG + " == endTransaction");
        }
    }
//////////////////////////////ORGANIZATIONS

    private void clearOrganizations() {
        mDataBase.delete(DBHelper.ORGANIZATIONS_TABLE_NAME, null, null);
    }

    private void insertOrganization(OrganizationModel org) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.ORGANIZATION_ID,        org.getId());
            cv.put(DBHelper.ORGANIZATION_TITLE,     org.getTitle());
            cv.put(DBHelper.ORGANIZATION_REGION_ID, org.getRegionId());
            cv.put(DBHelper.ORGANIZATION_CITY_ID,   org.getCityId());
            cv.put(DBHelper.ORGANIZATION_PHONE,     org.getPhone());
            cv.put(DBHelper.ORGANIZATION_ADDRESS,   org.getAddress());
            cv.put(DBHelper.ORGANIZATION_LINK,      org.getLink());

            mDataBase.insert(DBHelper.ORGANIZATIONS_TABLE_NAME, null, cv);
            /////// insert organization currencies
                deleteCurrencies4ORG(org.getId());
                for (Map.Entry<String, MoneyModel> currency : org.getCurrencies().entrySet()) {
                    insertCurrency4ORG(org.getId(), currency.getKey(), currency.getValue());
                }

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted organization id: " + org.getId() + ", title " + org.getTitle();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.TAG, resultTAG + " == endTransaction");
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    public void insertObjectModel(ObjectModel _objectModel) {
        clearAll();
        insertCities(_objectModel.cities);
        insertRegions(_objectModel.regions);
        insertCurrencies(_objectModel.currencies);
        for (OrganizationModel org: _objectModel.organizations) {
            insertOrganization(org);
        }
    }

    public Map<String, String> getCurrenciesDescription(){
        Map<String, String> result = new HashMap<>();

        Cursor c = mDataBase.query(DBHelper.CURRENCIES_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (c != null) {
            while (c.moveToNext()) {

                String  currency_ABB = c.getString(c.getColumnIndex(DBHelper.CURRENCY_ABB));
                String  currency_Title = c.getString(c.getColumnIndex(DBHelper.CURRENCY_TITLE));

                result.put(currency_ABB, currency_Title);
                Log.d(Constants.TAG, "get " + currency_ABB+ ": " + currency_Title);
            }
            c.close();
        }
        Log.d(Constants.TAG, "------------get from DB :" + result.size() + " items(currencies)");
        return result;
    }

    public Map<String, MoneyModel> getCurrencies4ORG(String orgID) {
        Map<String, MoneyModel> result = new HashMap<>();

        Cursor c = mDataBase.query(DBHelper.CURRENCIES4ORG_TABLE_NAME,
                                    null,
                                    DBHelper.CURRENCIES4ORG_ID + " = ?",
                                    new String[]{orgID},
                                    null,
                                    null,
                                    null);

        if (c != null) {
            while (c.moveToNext()) {

                String  currency4ORG_ABB = c.getString(c.getColumnIndex(DBHelper.CURRENCIES4ORG_ABB));
                String  currency4ORG_Ask = c.getString(c.getColumnIndex(DBHelper.CURRENCIES4ORG_ASK));
                String  currency4ORG_Bid = c.getString(c.getColumnIndex(DBHelper.CURRENCIES4ORG_BID));

                result.put(currency4ORG_ABB, new MoneyModel(currency4ORG_Ask, currency4ORG_Bid));
                Log.d(Constants.TAG, "get for id: " + orgID + " "
                        + currency4ORG_ABB+ "[ask=" + currency4ORG_Ask + ", bid=" +currency4ORG_Bid);
            }
            c.close();
        }
        return  result;
    }
    public List<OrgInfoModel> getOrganizations() {
        List<OrgInfoModel> result = new ArrayList<>();
//        String select = "SELECT " +
//                DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_ID + ", " +
//                DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_TITLE + ", " +
//
//
//                DBHelper.REGIONS_TABLE_NAME + "." + DBHelper.REGION_TITLE + ", " +
//                DBHelper.CITIES_TABLE_NAME + "." + DBHelper.CITY_TITLE + ", " +
//
//                DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_PHONE + ", " +
//                DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_ADDRESS + ", " +
//                DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_LINK + " ";
//        String from = "FROM " +
//                DBHelper.ORGANIZATIONS_TABLE_NAME + ", " +
//                DBHelper.REGIONS_TABLE_NAME + ", " +
//                DBHelper.CITIES_TABLE_NAME + " ";
//        String where = "WHERE " +
//                "(" + DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_REGION_ID + " = " +
//                DBHelper.REGIONS_TABLE_NAME + "." + DBHelper.REGION_ID + ") AND " +
//                "(" + DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_CITY_ID + " = " +
//                DBHelper.CITIES_TABLE_NAME + "." + DBHelper.CITY_ID +")";
//         String sqlQuery = select + from + where;


        String table = DBHelper.ORGANIZATIONS_TABLE_NAME +
                " inner join " + DBHelper.REGIONS_TABLE_NAME + " on " +
                            DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_REGION_ID + " = " +
                            DBHelper.REGIONS_TABLE_NAME + "." + DBHelper.REGION_ID +
                " inner join " + DBHelper.CITIES_TABLE_NAME + " on " +
                            DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_CITY_ID + " = " +
                            DBHelper.CITIES_TABLE_NAME + "." + DBHelper.CITY_ID;

        String columns[] = {/*0*/DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_ID,
                            /*1*/DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_TITLE,
                            /*2*/DBHelper.REGIONS_TABLE_NAME + "." + DBHelper.REGION_TITLE,
                            /*3*/DBHelper.CITIES_TABLE_NAME + "." + DBHelper.CITY_TITLE,
                            /*4*/DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_PHONE,
                            /*5*/DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_ADDRESS,
                            /*6*/DBHelper.ORGANIZATIONS_TABLE_NAME + "." + DBHelper.ORGANIZATION_LINK};
        Cursor c = mDataBase.query(table, columns, null, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {

                String  orgID       = c.getString(0);
                String  orgTitle    = c.getString(1);
                String  orgRegion   = c.getString(2);
                String  orgCity     = c.getString(3);
                String  orgPhone    = c.getString(4);
                String  orgAddress  = c.getString(5);
                String  orgLink     = c.getString(6);

                OrgInfoModel orgInfoModel = new OrgInfoModel();
                orgInfoModel.setId(orgID)
                        .setTitle(orgTitle)
                        .setRegionTitle(orgRegion)
                        .setCityTitle(orgCity)
                        .setPhone(orgPhone)
                        .setAddress(orgAddress)
                        .setLink(orgLink);

                result.add(orgInfoModel);
                Log.d(Constants.TAG, "get org id: " + orgID + ", title: " + orgTitle);
            }
            c.close();
        }
        return result;
    }
}
