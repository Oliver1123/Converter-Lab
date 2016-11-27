package com.example.oliver.someexample.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oliver on 13.09.15.
 */
public class OrganizationPOJOModel{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("regionId")
    @Expose
    public String regionId;
    @SerializedName("cityId")
    @Expose
    public String cityId;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("link")
    @Expose
    public String link;
        // currencies USD = [value.ask, value.bid]
//    private Map<String, MoneyModel> currencies = new LinkedHashMap<>();


}
