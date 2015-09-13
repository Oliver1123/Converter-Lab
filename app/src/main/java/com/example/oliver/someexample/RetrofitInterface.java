package com.example.oliver.someexample;

import com.example.oliver.someexample.Model.ObjectModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by oliver on 13.09.15.
 */
public interface RetrofitInterface {
    @GET("/ru/public/currency-cash.json")
    void getObjectModel(Callback<ObjectModel> callback);
}
