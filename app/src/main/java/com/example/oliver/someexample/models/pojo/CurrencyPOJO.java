package com.example.oliver.someexample.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zolotar on 27/11/16.
 */

public class CurrencyPOJO {

    @SerializedName("ask")
    @Expose
    public String ask;
    @SerializedName("bid")
    @Expose
    public String bid;

    @Override
    public String toString() {
        return "CurrencyPOJO{" +
                "ask='" + ask + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}
