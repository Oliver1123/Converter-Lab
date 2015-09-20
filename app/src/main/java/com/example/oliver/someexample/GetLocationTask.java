package com.example.oliver.someexample;

import android.os.AsyncTask;
import android.util.Log;

import com.example.oliver.someexample.Listener.GetLocationListener;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by oliver on 19.09.15.
 */
public class GetLocationTask  extends AsyncTask<String, Void, LatLng> {

    private final GetLocationListener mListener;

    public GetLocationTask(GetLocationListener _listener) {
        mListener = _listener;
    }

    @Override
    protected LatLng doInBackground(String... params) {
        LatLng result = null;
        try {
            result = getLatLong(getLocationInfo(params[0]));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(LatLng result) {
        super.onPostExecute(result);
        if (result != null) mListener.success(result);
        else mListener.failure();
    }


    public JSONObject getLocationInfo(String address) throws IOException, JSONException {
        String _url = "http://maps.google.com/maps/api/geocode/json?address=" +
                URLEncoder.encode(address, "UTF-8") + "&sensor=false";

        URL url = new URL(_url);
        Log.d(Constants.TAG, "url: " + url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");

        int respCode = conn.getResponseCode();
        if (respCode == 200) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return new JSONObject(sb.toString());
        }
        return null;

    }
    public  LatLng getLatLong(JSONObject jsonObject) throws JSONException {

        double longitude    = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");

        double latitude     = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");
        return new LatLng(latitude, longitude);

    }
}

