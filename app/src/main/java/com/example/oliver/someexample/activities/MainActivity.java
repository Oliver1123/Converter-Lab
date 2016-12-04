package com.example.oliver.someexample.activities;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.DataLoadService;
import com.example.oliver.someexample.R;
import com.example.oliver.someexample.adapters.OrgAdapter;
import com.example.oliver.someexample.db.FinanceDBContract;
import com.example.oliver.someexample.models.OrgInfoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final int ORG_INFO_LOADER = 33;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mOrgList;
    private OrgAdapter mAdapter;

    private List<OrgInfoModel> mData;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressDialog mProgressDialog;
    private MenuItem searchItem;
    private BroadcastReceiver br;
    private LoaderManager.LoaderCallbacks<Cursor> mLoadersCallBack = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(
                    MainActivity.this,
//                    FinanceDBContract.OrganizationsEntry.buildOrganizationUri("7oiylpmiow8iy1sma7h"),
//                    FinanceDBContract.OrganizationsEntry.CONTENT_URI_READABLE,
//                    FinanceDBContract.CurrenciesDataEntry.CONTENT_URI,
                    FinanceDBContract.CurrenciesDataEntry.buildCurrencyDataUri("7oiylpmiow8iy1sma7h"),
//                    FinanceDBContract.CurrenciesDataEntry.buildCurrencyDataUri("7oiylpmiow8iy1sma7h", 1480847316000L),
                    null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "MainActivity onLoadFinished");
            onOrganizationsLoaded(data);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private void onOrganizationsLoaded(Cursor cursor) {
        if (cursor.moveToFirst()){
            do{
                String data0 = cursor.getString(0);
                String data1 = cursor.getString(1);
                String data2 = cursor.getString(2);
                String data3 = cursor.getString(3);
                String data4 = cursor.getString(4);
//                String data5 = cursor.getString(5);
//                String data6 = cursor.getString(6);

                // do what ever you want here
                Log.d(TAG, "parseCursor: "
                        + " d0: " + data0
                        + " d1: " + data1
                        + " d2: " + data2
                        + " d3: " + data3
                        + " d4: " + data4
//                        + " d5: " + data5
//                        + " d6: " + data6
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG, "MainActivity onCreate");

        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(ORG_INFO_LOADER, null, mLoadersCallBack);

        initUI();

    }

    private void createReceiver() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(Constants.TAG, "MainActivity onReceive callback from DataLoadService");
                mProgressDialog.show();
                String action = intent.getAction();
                if (Constants.ACTION_LOADING_CALLBACK.equals(action)) {
                    getLoaderManager().getLoader(ORG_INFO_LOADER).forceLoad();
                }
            }
        };
        // Create filter
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_LOADING_CALLBACK);
        registerReceiver(br, intentFilter);
        Log.d(Constants.TAG, "MainActivity Receiver created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "MainActivity onStart");
        createReceiver();

        startService(new Intent(this, DataLoadService.class));

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "MainActivity onStop");
        unregisterReceiver(br);
    }

    private void initUI() {
         mToolbar = (Toolbar) findViewById(R.id.toolbar_AM);
        setSupportActionBar(mToolbar);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.notification_ticker));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.show();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_AM);
        mSwipeLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mOrgList = (RecyclerView) findViewById(R.id.rvOrganizations_AM);
        mOrgList.setLayoutManager(mLayoutManager);


        mAdapter = new OrgAdapter(this);
        mOrgList.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_settings).setIntent(new Intent(this, SettingsActivity.class));
        // SearchView options
        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(Constants.TAG, "search query : '" + newText + "'");
                List<OrgInfoModel> searchData = new ArrayList<>();
                for (OrgInfoModel model : mData) {
                    if (model.containStr(newText))
                        searchData.add(model);
                }
                mAdapter.setData(searchData);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onRefresh() {
        Log.d(Constants.TAG, "Start refreshing");
        getLoaderManager().getLoader(ORG_INFO_LOADER).forceLoad();
    }


}
