package com.example.oliver.someexample.loaders;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.db.QueryHelper;
import com.example.oliver.someexample.models.OrgInfoModel;

import java.util.List;

/**
 * Created by oliver on 17.09.15.
 */
public class OrgInfoModelLoader extends Loader<List<OrgInfoModel>> {
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

    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.d(Constants.TAG, "OrgInfoModelLoader onForceLoad");

        if (mTask != null) {
            mTask.cancel(true);
        }
        mTask = new GetOrgModelsTask(getContext());
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