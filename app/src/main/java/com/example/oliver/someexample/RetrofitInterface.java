package com.example.oliver.someexample;

import com.example.oliver.someexample.models.ObjectModel;

import retrofit.Callback;
import retrofit.http.GET;

public interface RetrofitInterface {

    @GET("/ru/public/currency-cash.json")
    void getObjectModel(Callback<ObjectModel> callback);
}
