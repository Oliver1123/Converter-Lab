package com.example.oliver.someexample.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.oliver.someexample.BuildConfig;

/**
 * Created by zolotar on 26/11/16.
 */

public class FinanceDBContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_ORGANIZATION    = "organization";
    public static final String PATH_REGION          = "region";
    public static final String PATH_CITY            = "city";
    public static final String PATH_CURRENCY_INFO   = "currency.info";
    public static final String PATH_CURRENCY_DATA   = "currency.data";

    public static final class OrganizationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "organization";

        public static final String COLUMN_ID              = "org_id";
        public static final String COLUMN_TITLE           = "title";
        public static final String COLUMN_REGION_ID       = "region_id";
        public static final String COLUMN_CITY_ID         = "city_id";
        public static final String COLUMN_PHONE           = "phone";
        public static final String COLUMN_ADDRESS         = "address";
        public static final String COLUMN_LINK            = "link";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ORGANIZATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORGANIZATION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORGANIZATION;

        public static Uri buildOrganizationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildOrganizationUri(String orgID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(orgID).build();
        }

        public static String getOrgIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class RegionsEntry implements BaseColumns {
        public static final String TABLE_NAME = "regions";

        public static final String COLUMN_REGION_ID = "region_id";
        public static final String COLUMN_TITLE           = "title";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REGION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REGION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REGION;

        public static Uri buildRegionUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRegionUri(String regionID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(regionID).build();
        }

        public static String getRegionIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class CitiesEntry implements BaseColumns {
        public static final String TABLE_NAME = "cities";


        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_TITLE           = "title";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITY;

        public static Uri buildCityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCityUri(String cityID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(cityID).build();
        }

        public static String getCityIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class CurrenciesInfoEntry implements BaseColumns {

        public static final String TABLE_NAME    = "currencies_info";

        public static final String COLUMN_ABB             = "abbreviation";
        public static final String COLUMN_TITLE           = "title";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENCY_INFO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY_INFO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY_INFO;

        public static Uri buildCurrencyInfoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCurrencyInfoUri(String currencyABB) {
            return CONTENT_URI.buildUpon().appendEncodedPath(currencyABB).build();
        }

        public static String getCurrencyABBFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class CurrenciesDataEntry implements BaseColumns {
        public static final String TABLE_NAME    = "currencies_data";

        public static final String COLUMN_ORG_ID        = "org_id";
        public static final String COLUMN_CURRENCY_ABB  = "abb";
        public static final String COLUMN_ASK           = "ask";
        public static final String COLUMN_BID           = "bid";
        public static final String COLUMN_DATE          = "date";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENCY_DATA).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY_DATA;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY_DATA;


        public static Uri buildCurrencyDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCurrencyDataUri(String orgID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(orgID).build();
        }

        public static String getOrganizaionIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
