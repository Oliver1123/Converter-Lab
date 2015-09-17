package com.example.oliver.someexample.Activities;

import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.oliver.someexample.Adapters.CurrencyAdapter;
import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.Dialogs.ShareDialog;
import com.example.oliver.someexample.Loaders.Currencies4ORGLoader;
import com.example.oliver.someexample.CustomView.CurrencyDescriptionCardView;
import com.example.oliver.someexample.CustomView.OrgInfoCardView;
import com.example.oliver.someexample.FABMenuController;
import com.example.oliver.someexample.Listener.FABMenuActionListener;
import com.example.oliver.someexample.Model.MoneyModel;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        FABMenuActionListener,
        LoaderManager.LoaderCallbacks<Pair<Map<String, String>, Map<String, MoneyModel>>>{
    private final int LOADER_CURRENCIES_ID = 0;
    private OrgInfoModel mModel;
    private Toolbar mToolbar;

    private ListView mCurrenciesList;
    private SwipeRefreshLayout mSwipeLayout;
    private FABMenuController mFABMenuController;
    private Map<String, String> mCurrenciesDescription;
    private ProgressBar mProgressBar;
    private CurrencyAdapter mAdapter;
    private LinearLayout mContainer;

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

        setHeaders();
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_AD);
        setSupportActionBar(mToolbar);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_AD);
        mSwipeLayout.setOnRefreshListener(this);

        mFABMenuController = new FABMenuController();
        mFABMenuController.register(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back_my);
            actionBar.setTitle(mModel.getTitle());
            actionBar.setSubtitle(mModel.getCityTitle());
        }

        mCurrenciesList = (ListView) findViewById(R.id.lvCurrencies_AD);

        mAdapter = new CurrencyAdapter(this);
        mCurrenciesList.setAdapter(mAdapter);
        mProgressBar = (ProgressBar) findViewById(R.id.pbLoading);
        mContainer = (LinearLayout) findViewById(R.id.llContainer_AD);
    }

    private void setHeaders() {
        OrgInfoCardView orgInfoCardView = new OrgInfoCardView(this);
        orgInfoCardView.setOrgInfo(mModel);
        mContainer.addView(orgInfoCardView, 0);
//        mCurrenciesList.addHeaderView(orgInfoCardView);

        CurrencyDescriptionCardView descriptionCardView = new CurrencyDescriptionCardView(this);
        mContainer.addView(descriptionCardView, 1);
        mSwipeLayout.setMinimumHeight(mContainer.getHeight());
//        mCurrenciesList.addHeaderView(descriptionCardView);
    }

    private void setData() {

        List<Pair<String, MoneyModel>> mData = new ArrayList<>(mModel.getCurrencies().size());
        for (Map.Entry<String, MoneyModel> entry :mModel.getCurrencies().entrySet()) {
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
    public void menuItemSelected(int itemID) {
        Intent intent;
        switch (itemID) {
            case Constants.MENU_ITEM_LINK:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mModel.getLink()));
                startActivity(intent);
                break;
            case Constants.MENU_ITEM_PHONE:
                if (!mModel.getPhone().isEmpty()) {
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mModel.getPhone()));
                    startActivity(intent);
                }
                break;
            case Constants.MENU_ITEM_MAP:
                //TODO show map
                showOnMap(mModel);
                break;
        }
    }
    private void showOnMap(OrgInfoModel _currentModel) {
        String address = _currentModel.getRegionTitle();
        if (!address.equals(_currentModel.getCityTitle())) {
            address = address + " " + _currentModel.getCityTitle();
        }
        address = address + " " + _currentModel.getAddress();
        Uri uri = Uri.parse("geo:0,0?q=" + address);
        Log.d(Constants.TAG, "Show on map uri: " + uri.toString());

        startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
        mSwipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
        setData();
    }

    @Override
    public void onLoaderReset(Loader<Pair<Map<String, String>, Map<String, MoneyModel>>> loader) {

    }
}
