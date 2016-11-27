package com.example.oliver.someexample.loaders;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by zolotar on 27/11/16.
 */

public class TestDataLoader extends Loader<Cursor> {
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
    }
}
