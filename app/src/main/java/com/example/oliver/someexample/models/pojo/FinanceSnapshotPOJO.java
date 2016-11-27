package com.example.oliver.someexample.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 13.09.15.
 */
public class FinanceSnapshotPOJO {

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("organizations")
    @Expose
    public List<OrganizationPOJO> organizations   = new ArrayList<>();

        // money USD = доллары США
    @SerializedName("currencies")
    @Expose
    public Map<String, String> currencies = new HashMap<>();

        // region ua,7oiylpmiow8iy1smaci = Днепропетровская область
    @SerializedName("regions")
    @Expose
    public Map<String, String> regions = new HashMap<>();

        // city 7oiylpmiow8iy1smadm = Днепропетровск
    @SerializedName("cities")
    @Expose
    public Map<String, String> cities = new HashMap<>();


}
