package com.example.oliver.someexample.Activities;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
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

import com.example.oliver.someexample.Adapters.OrgAdapter;
import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.DataLoadService;
import com.example.oliver.someexample.Loaders.OrgInfoModelLoader;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<List<OrgInfoModel>>{
    private static final int ORG_INFO_LOADER = 33;
    private Toolbar mToolbar;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mOrgList;
    private OrgAdapter mAdapter;

    private List<OrgInfoModel> mData;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressDialog mProgressDialog;
    private MenuItem searchItem;
    private BroadcastReceiver br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.TAG, "MainActivity onCreate");
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(ORG_INFO_LOADER, null, this);

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
        mProgressDialog.setMessage(getResources().getString(R.string.notification_ticker));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(true);

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
        // SearchView options
        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
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
        // TODO refresh Data
        Log.d(Constants.TAG, "Start refreshing");
        getLoaderManager().getLoader(ORG_INFO_LOADER).forceLoad();
    }

    @Override
    public Loader<List<OrgInfoModel>> onCreateLoader(int id, Bundle args) {
        Loader<List<OrgInfoModel>> loader = null;
        if (id == ORG_INFO_LOADER) {
            loader = new OrgInfoModelLoader(this, args);
            Log.d(Constants.TAG, "Loader created");
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<OrgInfoModel>> loader, List<OrgInfoModel> data) {
        mProgressDialog.dismiss();
        mData = data;
        mAdapter.setData(mData);
        mSwipeLayout.setRefreshing(false);
        if (searchItem.isActionViewExpanded()) {
            searchItem.collapseActionView();
        }
        Log.d(Constants.TAG, "MainActivity onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<OrgInfoModel>> loader) {

    }

}
