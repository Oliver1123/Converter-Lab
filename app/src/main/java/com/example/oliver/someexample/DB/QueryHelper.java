package com.example.oliver.someexample.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.Model.MoneyModel;
import com.example.oliver.someexample.Model.ObjectModel;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.Model.OrganizationModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        clearCurrenciesDesc();
        clearCurrencies4ORG();
        clearOrganizations();
    }


    ////////////////////////    CURRENCIES DESCRIPTION
    private void clearCurrenciesDesc() {
        mDataBase.delete(DBHelper.CURRENCIES_TABLE_NAME, null, null);
    }

    private void insertCurrencyDesc(Map.Entry<String, String> currency) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.CURRENCY_ABB,   currency.getKey());
            cv.put(DBHelper.CURRENCY_TITLE, capWord(currency.getValue()));

            mDataBase.insert(DBHelper.CURRENCIES_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted currency abb: " + currency.getKey() + ", title: " + currency.getValue();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.DB_TAG, resultTAG + " == endTransaction");
        }
    }

    private void insertCurrenciesDesc(Map<String, String> currencies) {
        clearCurrenciesDesc();
        for (Map.Entry<String, String> entry: currencies.entrySet()) {
            insertCurrencyDesc(entry);
        }
        Log.d(Constants.DB_TAG, "Total inserted " + currencies.size() + "currencies");
    }

    public String getCurrencyDescription(String currencyABB) {
        String result = null;

        Cursor c = mDataBase.query(DBHelper.CURRENCIES_TABLE_NAME,
                null,
                DBHelper.CURRENCY_ABB + " = ?",
                new String[]{currencyABB},
                null,
                null,
                null);

        if (c != null) {
            while (c.moveToNext()) {
                result = c.getString(c.getColumnIndex(DBHelper.CURRENCY_TITLE));
            }
            c.close();
        }
        return  result;
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
            Log.d(Constants.DB_TAG, resultTAG + " == endTransaction");
        }
    }

    private void insertRegions(Map<String, String> regions) {
        clearRegions();
        for (Map.Entry<String, String> entry: regions.entrySet()) {
            insertRegion(entry);
        }
        Log.d(Constants.DB_TAG, "Total inserted " + regions.size() + "regions");
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
            Log.d(Constants.DB_TAG, resultTAG + " == endTransaction");
        }
    }

    private void insertCities(Map<String, String> cities) {
        clearCities();
        for (Map.Entry<String, String> entry : cities.entrySet()) {
            insertCity(entry);
        }
        Log.d(Constants.DB_TAG, "Total inserted " + cities.size() + "cities");
    }
///////////////////////// CURRENCIES4ORG

    private void clearCurrencies4ORG() {
        mDataBase.delete(DBHelper.CURRENCIES4ORG_TABLE_NAME, null, null);
    }

    private void deleteCurrencies4ORG(String organizationID) {
        int deleted = mDataBase.delete(DBHelper.CURRENCIES4ORG_TABLE_NAME,
                                       DBHelper.CURRENCIES4ORG_ID + " = ?",
                                       new String[]{organizationID});
        Log.d(Constants.DB_TAG, "Currencies4ORG id: " + organizationID + " deleted " + deleted + "records");
    }

    private void insertCurrency4ORG(String orgID, String currencyABB, MoneyModel money) {
        String resultTAG = "";
        mDataBase.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.CURRENCIES4ORG_ID,  orgID);
            cv.put(DBHelper.CURRENCIES4ORG_ABB, currencyABB);
            cv.put(DBHelper.CURRENCIES4ORG_ASK, money.ask);
            cv.put(DBHelper.CURRENCIES4ORG_BID, money.bid);
            cv.put(DBHelper.CURRENCIES4ORG_ASK_RATE, money.ask_rate);
            cv.put(DBHelper.CURRENCIES4ORG_BID_RATE, money.bid_rate);

            mDataBase.insert(DBHelper.CURRENCIES4ORG_TABLE_NAME, null, cv);

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted currency4ORG id: " + orgID + ", ABB: " + currencyABB + "[" + money + "]";
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.DB_TAG, resultTAG + " == endTransaction");
        }
    }

    public Map<String, MoneyModel> getCurrencies4ORG(String orgID) {
        Map<String, MoneyModel> result = new LinkedHashMap<>();

        Cursor c = mDataBase.query(DBHelper.CURRENCIES4ORG_TABLE_NAME,
                null,
                DBHelper.CURRENCIES4ORG_ID + " = ?",
                new String[]{orgID},
                null,
                null,
                null);

        if (c != null) {
            while (c.moveToNext()) {

                String  _ABB = c.getString(c.getColumnIndex(DBHelper.CURRENCIES4ORG_ABB));
                String  _ask = c.getString(c.getColumnIndex(DBHelper.CURRENCIES4ORG_ASK));
                String  _bid = c.getString(c.getColumnIndex(DBHelper.CURRENCIES4ORG_BID));
                int _ask_rate = c.getInt(c.getColumnIndex(DBHelper.CURRENCIES4ORG_ASK_RATE));
                int _bid_rate = c.getInt(c.getColumnIndex(DBHelper.CURRENCIES4ORG_BID_RATE));

                MoneyModel value = new MoneyModel(_ask, _bid, _ask_rate, _bid_rate);
                result.put(_ABB, value);

                Log.d(Constants.DB_TAG, "get for id: " + orgID + " "
                        + _ABB+ ": " + value);
            }
            c.close();
        }
        return  result;
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
                Map<String, MoneyModel> prevValues = getCurrencies4ORG(org.getId());
                deleteCurrencies4ORG(org.getId());

                for (Map.Entry<String, MoneyModel> currency : org.getCurrencies().entrySet()) {
                    String abb = currency.getKey();
                    MoneyModel currentMoney = currency.getValue();
                    MoneyModel prevMoney = prevValues.get(abb);
                    currentMoney.setRate(prevMoney);
                    insertCurrency4ORG(org.getId(), currency.getKey(), currency.getValue());
                }

            mDataBase.setTransactionSuccessful();
            resultTAG = "inserted organization id: " + org.getId() + ", title " + org.getTitle();
        } finally {
            mDataBase.endTransaction();
            Log.d(Constants.DB_TAG, resultTAG + " == endTransaction");
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    public void insertObjectModel(ObjectModel _objectModel) {
//        clearAll();
        String lastUpdate = Constants.getLastUpgradeDate(mContext);
        if (!_objectModel.date.equals(lastUpdate)) {
            Log.d(Constants.TAG, "Update db date: " + _objectModel.date);
            Constants.setLastUpgradeDate(mContext, _objectModel.date);
            insertCities(_objectModel.cities);
            insertRegions(_objectModel.regions);
            insertCurrenciesDesc(_objectModel.currencies);
            clearOrganizations();
            for (OrganizationModel org : _objectModel.organizations) {
                insertOrganization(org);
            }
        } else {
            Log.d(Constants.TAG, "Nothing to update lastUpdate: " + lastUpdate + ", current data date: " + _objectModel.date);
        }
    }

    public Map<String, String> getCurrenciesDescription(){
        Map<String, String> result = new LinkedHashMap<>();

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
                Log.d(Constants.DB_TAG, "get " + currency_ABB+ ": " + currency_Title);
            }
            c.close();
        }
        Log.d(Constants.DB_TAG, "------------get from DB :" + result.size() + " items(currencies)");
        return result;
    }


    public List<OrgInfoModel> getOrganizations() {
        List<OrgInfoModel> result = new ArrayList<>();

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
                Log.d(Constants.DB_TAG, "get org id: " + orgID + ", title: " + orgTitle);
            }
            c.close();
        }
        return result;
    }

    // some WORD in text -> Some WORD In Text

    /**
     * Capitalize first letter in each word the given string
     * @param value string value that need to capitalize
     * @return string with capitalized first letter in each word
     */
    private String capWord(String value) {
        StringBuilder res = new StringBuilder();

        String[] strArr = value.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }
}
