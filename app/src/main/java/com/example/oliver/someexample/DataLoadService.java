package com.example.oliver.someexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.oliver.someexample.DB.QueryHelper;
import com.example.oliver.someexample.Model.ObjectModel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataLoadService extends Service {
    private static final int NOTIFICATION_ID = 111;
    private static int SERVICE_TIMEOUT_MILISECOND = 1000 * 30;
    public DataLoadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "DataLoadService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "DataLoadService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "DataLoadService onStartCommand ");

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplication());
        notifBuilder.setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(getResources().getString(R.string.notification_title))
                    .setTicker(getResources().getString(R.string.notification_ticker));


        startForeground(NOTIFICATION_ID, notifBuilder.build());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://resources.finance.ua")
                .build();

        RetrofitInterface retrofitInterface = restAdapter.create(RetrofitInterface.class);
        retrofitInterface.getObjectModel(new Callback<ObjectModel>() {
            @Override
            public void success(ObjectModel t, Response response) {
                Log.d(Constants.TAG, "Retrofit success data loaded");
                new AsyncDBInsert(getApplicationContext()).execute(t);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(Constants.TAG, "Retrofit failure");
                sendCallback();
            }
        });
        return START_NOT_STICKY;
    }

    private void sendCallback() {
        // send callback to Activity
        Log.d(Constants.TAG, "DataLoadService sendCallback");
        Intent intent = new Intent(Constants.ACTION_LOADING_CALLBACK);
        sendBroadcast(intent);

        Intent serviceIntent = new Intent(this, DataLoadService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);// cancel previous alarm
        am.set(AlarmManager.RTC, System.currentTimeMillis() + SERVICE_TIMEOUT_MILISECOND, pendingIntent);

        stopSelf();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class AsyncDBInsert extends AsyncTask<ObjectModel, Void, Void> {

        Context mContext;

        public AsyncDBInsert(Context _context) {
            mContext = _context;
        }

        @Override
        protected Void doInBackground(ObjectModel... params) {
            Log.d(Constants.TAG, "Start write to base");
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

            Log.d(Constants.TAG, "End write to base ");
            sendCallback();
        }

    }
}
