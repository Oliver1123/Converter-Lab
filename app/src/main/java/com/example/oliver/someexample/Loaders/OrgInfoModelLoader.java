package com.example.oliver.someexample.Loaders;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.DB.QueryHelper;
import com.example.oliver.someexample.Model.MoneyModel;
import com.example.oliver.someexample.Model.OrgInfoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 17.09.15.
 */
public class OrgInfoModelLoader extends Loader<List<OrgInfoModel>> {
    private final Context mContext;
    private GetOrgModelsTask mTask;

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
    public OrgInfoModelLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        if (mTask != null) {
            mTask.cancel(true);
        }
        mTask = new GetOrgModelsTask(mContext);
        mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void getResult( List<OrgInfoModel> _result) {
        deliverResult(_result);
    }

    class GetOrgModelsTask extends AsyncTask<Void, Void, List<OrgInfoModel>> {

        private final Context mContext;

        public GetOrgModelsTask(Context _context) {
            mContext = _context;
        }
        @Override
        protected List<OrgInfoModel>doInBackground(Void... params) {
            QueryHelper helper = new QueryHelper(mContext);
            helper.open();

            List<OrgInfoModel> result = helper.getOrganizations();

            helper.close();
            return result;
        }

        @Override
        protected void onPostExecute( List<OrgInfoModel> result) {
            super.onPostExecute(result);
            getResult(result);
        }
    }

}