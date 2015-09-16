package com.example.oliver.someexample.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.DB.QueryHelper;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.Adapters.OrgAdapter;
import com.example.oliver.someexample.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Toolbar mToolbar;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mOrgList;
    private OrgAdapter mAdapter;
    private QueryHelper mHelper;
    private List<OrgInfoModel> mData;
    private SwipeRefreshLayout mSwipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new QueryHelper(this);
        mHelper.open();
        initUI();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        mHelper.close();
        super.onStop();
    }

    private void initUI() {
         mToolbar = (Toolbar) findViewById(R.id.toolbar_AM);
        setSupportActionBar(mToolbar);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_AM);
        mSwipeLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mOrgList = (RecyclerView) findViewById(R.id.rvOrganizations_AM);
        mOrgList.setLayoutManager(mLayoutManager);

        mData= mHelper.getOrganizations();
        mAdapter = new OrgAdapter(this, mData);
        mOrgList.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // SearchView options
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(Constants.TAG, "search query : " + newText);
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
        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        mSwipeLayout.setRefreshing(false);
    }
}
