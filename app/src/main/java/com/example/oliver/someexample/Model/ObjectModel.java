package com.example.oliver.someexample.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 13.09.15.
 */
public class ObjectModel {
    private String date;
    private List<OrganizationModel> organizations   = new ArrayList<>();
        // money USD = доллары США
    private Map<String, String> currencies          = new HashMap<>();
        // region ua,7oiylpmiow8iy1smaci = Днепропетровская область
    private Map<String, String> regions             = new HashMap<>();
        // city 7oiylpmiow8iy1smadm = Днепропетровск
    private Map<String, String> cities              = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder organizationsString = new StringBuilder();
        for (OrganizationModel model: organizations) {
            organizationsString.append(model + "\n");
        }
        return "ObjectModel{" +
                "date: '" + date + '\'' +
                "\n, organization: " + organizationsString.toString() +
                "\n, currencies: " + currencies +
                "\n, regions: " + regions +
                "\n, cities: " + cities+
                '}';
    }

    public OrganizationModel getOrganization(int position) {
        return organizations.get(position);
    }

    public String getCurrencyName(String key) {
        return currencies.get(key);
    }

    public String getRegionName(String key) {
        return regions.get(key);
    }

    public String getCityName(String key) {
        return cities.get(key);
    }
}
