package com.example.oliver.someexample.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 13.09.15.
 */
public class ObjectModel {
    public String date;
    public List<OrganizationModel> organizations   = new ArrayList<>();
        // money USD = доллары США
    public Map<String, String> currencies          = new HashMap<>();
        // region ua,7oiylpmiow8iy1smaci = Днепропетровская область
    public Map<String, String> regions             = new HashMap<>();
        // city 7oiylpmiow8iy1smadm = Днепропетровск
    public Map<String, String> cities              = new HashMap<>();

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
}
