package com.example.oliver.someexample.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oliver on 13.09.15.
 */
public class OrganizationModel {
    private String id;
//    private String orgType;
    private String title;
    private String regionId;
    private String cityId;
    private String phone;
    private String address;
    private String link;
        // currencies USD = MoneyModel{ack: x; bid y}
    private Map<String, MoneyModel> currencies = new HashMap<>();


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getLink() {
        return link;
    }

    public Map<String, MoneyModel> getCurrencies() {
        return currencies;
    }


    @Override
    public String toString() {
        return  "id: '" + id + '\'' + "\n" +
//                "orgType: '" + orgType + '\'' + "\n" +
                "title: '" + title + '\'' + "\n" +
                "regionId: '" + regionId + '\'' + "\n" +
                "cityId: '" + cityId + '\'' + "\n" +
                "phone: '" + phone + '\'' + "\n" +
                "address: '" + address + '\'' + "\n" +
                "link: '" + link + '\'' + "\n" +
                "currencies: \n" + currencies;
    }
}
