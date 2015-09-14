package com.example.oliver.someexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.oliver.someexample.DB.DBHelper;
import com.example.oliver.someexample.DB.QueryHelper;
import com.example.oliver.someexample.Model.OrgInfoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mOrgList;
    private OrgAdapter mAdapter;
    private QueryHelper mHelper;
    private List<OrgInfoModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new QueryHelper(this);
        mHelper.open();
        initUI();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.close();
    }

    private void initUI() {
         mToolbar = (Toolbar) findViewById(R.id.toolbar_AM);
        setSupportActionBar(mToolbar);


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
                //TODO: Search
                List<OrgInfoModel> searchData = new ArrayList<>();
                for (OrgInfoModel model : mData) {
                    if (model.containStr(newText))
                        searchData.add(model);
                }
                mAdapter.setData(searchData);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
