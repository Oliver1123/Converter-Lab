package com.example.oliver.someexample.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oliver on 13.09.15.
 */
public class OrganizationPOJO {

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
//         currencies USD = [value.ask, value.bid]
    @SerializedName("currencies")
    @Expose
    public Map<String, CurrencyPOJO> currencies = new HashMap<>();


    @Override
    public String toString() {
        return "OrganizationPOJO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", regionId='" + regionId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
