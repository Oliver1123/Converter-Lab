package com.example.oliver.someexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.oliver.someexample.DB.QueryHelper;
import com.example.oliver.someexample.Model.ObjectModel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "tag";
    TextView mResult;
    ProgressBar mLoadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity onCreate");
        findViewById(R.id.btnGetData).setOnClickListener(this);

        mResult = (TextView) findViewById(R.id.tvResult);
        mLoadProgress = (ProgressBar) findViewById(R.id.loadProgress);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "MainActivity onClick");
        mLoadProgress.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://resources.finance.ua")
                .build();

        RetrofitInterface retrofitInterface= restAdapter.create(RetrofitInterface.class);
        retrofitInterface.getObjectModel(new Callback<ObjectModel>() {
            @Override
            public void success(ObjectModel t, Response response) {
                Log.d(TAG, "success");
                mResult.setText(t.toString());
                new AsyncDBInsert(getApplicationContext(), mLoadProgress).execute(t);
//                StringBuilder text = new StringBuilder();
//                text.append(t.getOrganization(1));
//                mResult.setText(text.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure");
            }
        });
    }

    public class AsyncDBInsert extends AsyncTask<ObjectModel, Void, Void> {
        Context mContext;
        ProgressBar mProgressBar;

        AsyncDBInsert(Context _context, ProgressBar _progressBar) {
            mContext = _context;
            mProgressBar = _progressBar;
        }

        @Override
        protected Void doInBackground(ObjectModel... params) {
            QueryHelper helper = new QueryHelper(mContext);
            if (params != null) {
                helper.open();
                helper.insertObjectModel(params[0]);
                helper.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.GONE);
            Log.d(Constants.TAG, "END ASYNCTASK");
        }
    }
}
