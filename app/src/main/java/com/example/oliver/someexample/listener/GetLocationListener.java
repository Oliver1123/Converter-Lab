package com.example.oliver.someexample.listener;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by oliver on 19.09.15.
 */
public interface GetLocationListener {
        void success(LatLng result);
        void failure();

}
