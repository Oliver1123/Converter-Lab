package com.example.oliver.someexample.loaders;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.models.MoneyModel;

import java.util.Map;

/**
 * Created by oliver on 16.09.15.
 */
public class Currencies4ORGLoader extends Loader<Pair<Map<String, String>, Map<String, MoneyModel>>> {
    private String mOrgID;
    private GetCurrencies4ORGTask mTask;

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
    public Currencies4ORGLoader(Context context, Bundle args) {
        super(context);

        if (args != null) {
            mOrgID = args.getString(Constants.ORG_ID_ARG);
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        if (mTask != null) {
            mTask.cancel(true);
        }
        mTask = new GetCurrencies4ORGTask(getContext());
        mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mOrgID);
    }


    private void getResult(Pair<Map<String, String>, Map<String, MoneyModel>> _result) {
        deliverResult(_result);
    }
    class GetCurrencies4ORGTask extends AsyncTask<String, Void,Pair<Map<String, String>, Map<String, MoneyModel>>> {

        private final Context mContext;

        public GetCurrencies4ORGTask(Context _context) {
            mContext = _context;
        }
        @Override
        protected Pair<Map<String, String>, Map<String, MoneyModel>> doInBackground(String... params) {
//            QueryHelper helper = new QueryHelper(mContext);
//            helper.open();
//
//            Map<String, String> resultFirst = helper.getCurrenciesDescription();
//            Map<String, MoneyModel> resultSecond = null;
//
//            if (params != null) {
//                 resultSecond = helper.getCurrencies4ORG(params[0]);
//            }
//            helper.close();
//            return new Pair<>(resultFirst, resultSecond);
            return null;
        }

        @Override
        protected void onPostExecute(Pair<Map<String, String>, Map<String, MoneyModel>> result) {
            super.onPostExecute(result);
            getResult(result);
        }
    }

}
