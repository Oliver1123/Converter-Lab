package com.example.oliver.someexample.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;

public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private OrgInfoModel mModel;
    private Toolbar mToolbar;
    private TextView mOrgTitle, mOrgAddress, mOrgPhone, mOrgLink;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mCurrenciesList;
    private SwipeRefreshLayout mSwipeLayout;
//    private CurrencyAdapter mAdapter;
//    Map<String, MoneyModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mModel = getIntent().getParcelableExtra(Constants.ORG_INFO_MODEL_ARG);
        initUI();

        setData();
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_AD);
        setSupportActionBar(mToolbar);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_AD);
        mSwipeLayout.setOnRefreshListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back_my);
            actionBar.setTitle(mModel.getTitle());
            actionBar.setSubtitle(mModel.getCityTitle());
        }

        mOrgTitle = (TextView) findViewById(R.id.tvTitleInfo_AD);
        mOrgAddress = (TextView) findViewById(R.id.tvAddressInfo_AD);
        mOrgPhone = (TextView) findViewById(R.id.tvPhoneInfo_AD);
        mOrgLink = (TextView) findViewById(R.id.tvLink_Info_AD);
//        mCurrenciesList = (RecyclerView) findViewById(R.id.rvCurrencies_AD);
    }

    private void setData() {
        mOrgTitle.setText(mModel.getTitle());
        String fullAddress =((mModel.getRegionTitle().equals(mModel.getCityTitle())) ?
                                        mModel.getRegionTitle():
                                        mModel.getRegionTitle() + "\n" + mModel.getCityTitle())
                             + "\n" + mModel.getAddress();
        mOrgAddress.setText(fullAddress);
        mOrgPhone.setText(mModel.getPhone());

        mOrgLink.setClickable(true);
        mOrgLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + mModel.getLink() + "'> " + mModel.getTitle() + "</a>";
        mOrgLink.setText(Html.fromHtml(text));
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
              //TODO show share dialog
              return true;
      }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        // TODO refresh Data
        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        mSwipeLayout.setRefreshing(false);
    }
}
