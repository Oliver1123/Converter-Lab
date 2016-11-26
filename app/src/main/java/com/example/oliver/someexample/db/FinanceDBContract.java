package com.example.oliver.someexample.db;

import android.provider.BaseColumns;

/**
 * Created by zolotar on 26/11/16.
 */

public class FinanceDBContract {

    public static final class OrganizationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "organization";

        public static final String COLUMN_ID              = "org_id";
        public static final String COLUMN_TITLE           = "title";
        public static final String COLUMN_REGION_ID       = "region_id";
        public static final String COLUMN_CITY_ID         = "city_id";
        public static final String COLUMN_PHONE           = "phone";
        public static final String COLUMN_ADDRESS         = "address";
        public static final String COLUMN_LINK            = "link";

        //todo add content provider uri
    }

    public static final class RegionsEntry implements BaseColumns {
        public static final String TABLE_NAME = "regions";

        public static final String COLUMN_ID              = "region_id";
        public static final String COLUMN_TITLE           = "title";
        //todo add content provider uri
    }

    public static final class CitiesEntry implements BaseColumns {
        public static final String TABLE_NAME = "cities";


        public static final String COLUMN_ID              = "city_id";
        public static final String COLUMN_TITLE           = "title";
        //todo add content provider uri
    }

    public static final class CurrenciesInfoEntry implements BaseColumns {

        public static final String TABLE_NAME    = "currencies_info";

        public static final String COLUMN_ABB             = "abbreviation";
        public static final String COLUMN_TITLE           = "title";
        //todo add content provider uri
    }

    public static final class CurrenciesDataEntry implements BaseColumns {
        public static final String TABLE_NAME    = "currencies_data";

        public static final String COLUMN_ORG_ID        = "org_id";
        public static final String COLUMN_CURRENCY_ABB  = "abb";
        public static final String COLUMN_ASK           = "ask";
        public static final String COLUMN_BID           = "bid";
        public static final String COLUMN_DATE          = "date";

        //todo add content provider uri
    }
}
