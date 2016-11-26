package com.example.oliver.someexample.activities;

import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.FABController;
import com.example.oliver.someexample.MenuActionHandler;
import com.example.oliver.someexample.R;
import com.example.oliver.someexample.adapters.CurrencyAdapter;
import com.example.oliver.someexample.custom_view.CurrencyDescriptionCardView;
import com.example.oliver.someexample.custom_view.OrgInfoCardView;
import com.example.oliver.someexample.dialogs.ShareDialog;
import com.example.oliver.someexample.loaders.Currencies4ORGLoader;
import com.example.oliver.someexample.models.MoneyModel;
import com.example.oliver.someexample.models.OrgInfoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        FABController.FABActionListener,
        LoaderManager.LoaderCallbacks<Pair<Map<String, String>, Map<String, MoneyModel>>> {
    private final int LOADER_CURRENCIES_ID = 0;
    private OrgInfoModel mModel;
    private Toolbar mToolbar;

    private ListView mCurrenciesList;
    private SwipeRefreshLayout mSwipeLayout;
    private FABController mFABMenuController;
    private Map<String, String> mCurrenciesDescription;
    private ProgressBar mProgressBar;
    private CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mModel = getIntent().getParcelableExtra(Constants.ORG_INFO_MODEL_ARG);

        initUI();

        Bundle args = new Bundle();
        args.putString(Constants.ORG_ID_ARG, mModel.getId());
        getLoaderManager().initLoader(LOADER_CURRENCIES_ID, args, this);

        getLoaderManager().getLoader(LOADER_CURRENCIES_ID).forceLoad();

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_AD);
        setSupportActionBar(mToolbar);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_AD);
        mSwipeLayout.setOnRefreshListener(this);

        mFABMenuController = new FABController();
        mFABMenuController.register(this, findViewById(R.id.fabMenu));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
            actionBar.setTitle(mModel.getTitle());
            actionBar.setSubtitle(mModel.getCityTitle());
        }

        mCurrenciesList = (ListView) findViewById(R.id.lvCurrencies_AD);

        setHeaders();

        mAdapter = new CurrencyAdapter(this);
        mCurrenciesList.setAdapter(mAdapter);
        mProgressBar = (ProgressBar) findViewById(R.id.pbLoading_AD);
    }

    private void setHeaders() {
        OrgInfoCardView orgInfoCardView = new OrgInfoCardView(this);
        orgInfoCardView.setOrgInfo(mModel);
        mCurrenciesList.addHeaderView(orgInfoCardView);

        CurrencyDescriptionCardView descriptionCardView = new CurrencyDescriptionCardView(this);
        mCurrenciesList.addHeaderView(descriptionCardView);
    }

    private void setData() {

        List<Pair<String, MoneyModel>> mData = new ArrayList<>(mModel.getCurrencies().size());
        for (Map.Entry<String, MoneyModel> entry : mModel.getCurrencies().entrySet()) {
            mData.add(new Pair<>(mCurrenciesDescription.get(entry.getKey()), entry.getValue()));
        }

        mAdapter.setData(mData);
        mCurrenciesList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_share:
                Bundle args = new Bundle();
                args.putParcelable(Constants.ORG_INFO_MODEL_ARG, mModel);

                DialogFragment shareDialog = new ShareDialog();
                shareDialog.setArguments(args);
                shareDialog.show(getFragmentManager(), "");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Log.d(Constants.TAG, "Start refreshing");
        getLoaderManager().getLoader(LOADER_CURRENCIES_ID).forceLoad();
    }

    @Override
    public void onItemSelected(int itemID) {
        new MenuActionHandler(this).itemClickAction(itemID, mModel);
    }

    @Override
    public Loader<Pair<Map<String, String>, Map<String, MoneyModel>>> onCreateLoader(int id, Bundle args) {
        Loader<Pair<Map<String, String>, Map<String, MoneyModel>>> loader = null;
        if (id == LOADER_CURRENCIES_ID) {
            loader = new Currencies4ORGLoader(this, args);
            Log.d(Constants.TAG, "Loader created");
        }
        mProgressBar.setVisibility(View.VISIBLE);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Pair<Map<String, String>, Map<String, MoneyModel>>> loader,
                               Pair<Map<String, String>, Map<String, MoneyModel>> data) {
        mProgressBar.setVisibility(View.GONE);
        mCurrenciesDescription = data.first;
        mModel.setCurrencies(data.second);
        Log.d(Constants.TAG, "End refreshing");

        mSwipeLayout.setRefreshing(false);
        setData();
    }

    @Override
    public void onLoaderReset(Loader<Pair<Map<String, String>, Map<String, MoneyModel>>> loader) {

    }
}
