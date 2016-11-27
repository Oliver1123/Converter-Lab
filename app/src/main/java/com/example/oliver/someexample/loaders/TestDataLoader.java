package com.example.oliver.someexample.loaders;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;

import com.example.oliver.someexample.db.FinanceDBContract;

/**
 * Created by zolotar on 27/11/16.
 */

public class TestDataLoader extends CursorLoader {
    private static final String TAG = TestDataLoader.class.getSimpleName();

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public TestDataLoader(Context context) {
        super(context);
        Log.d(TAG, "TestDataLoader: constructor");
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.d(TAG, "onForceLoad: ");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Cursor cursor = getContext().getContentResolver().query(FinanceDBContract.OrganizationsEntry.CONTENT_URI,
                null, null, null, null);
//        Cursor cursor = getContext().getContentResolver().query(
//                FinanceDBContract.CurrenciesInfoEntry.buildCurrencyInfoUri("USD"),
//                null, null, null, null);
        parseCursor(cursor);
//        deliverResult(cursor);
    }

    private void parseCursor(Cursor cursor) {
        if (cursor.moveToFirst()){
            do{
                String data0 = cursor.getString(0);
                String data1 = cursor.getString(1);
                String data2 = cursor.getString(2);
//                String data3 = cursor.getString(3);
//                String data4 = cursor.getString(4);
//                String data5 = cursor.getString(5);
                // do what ever you want here
                Log.d(TAG, "parseCursor: "
                        + " d0: " + data0
                        + " d1: " + data1
                        + " d2: " + data2
//                        + "d3: " + data3
//                        + "d4: " + data4
//                        + "d5: " + data5
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
