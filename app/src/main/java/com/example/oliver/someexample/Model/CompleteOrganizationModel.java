package com.example.oliver.someexample.Model;

import java.util.Map;

/**
 * Created by oliver on 14.09.15.
 */
public class CompleteOrganizationModel extends OrganizationModel{
    private String regionTitle;
    private String cityTitle;

    public String getRegionTitle() {
        return regionTitle;
    }

    public String getCityTitle() {
        return cityTitle;
    }


    public CompleteOrganizationModel setId(String _id) {
        super.setId(_id);
        return this;
    }

    public CompleteOrganizationModel setTitle(String _title) {
        super.setTitle(_title);
        return this;
    }

    public CompleteOrganizationModel setRegionTitle(String _regionTitle) {
        regionTitle = _regionTitle;
        return this;
    }

    public CompleteOrganizationModel setCityTitle(String _cityTitle) {
        cityTitle = _cityTitle;
        return this;
    }

    public CompleteOrganizationModel setPhone(String _phone) {
        super.setPhone(_phone);
        return this;
    }

    public CompleteOrganizationModel setAddress(String _address) {
        super.setAddress(_address);
        return this;
    }

    public CompleteOrganizationModel setLink(String _link) {
        super.setLink(_link);
        return this;
    }

    public CompleteOrganizationModel setCurrencies(Map<String, MoneyModel> _currencies) {
        super.setCurrencies(_currencies);
        return this;
    }

    @Override
    public String toString() {
        return  "id: "          + getId() + "\n" +
                "title: "       + getTitle()  + "\n" +
                "regionTitle: " + getRegionTitle()+ "\n" +
                "cityTitle: "   + getCityTitle()+ "\n" +
                "phone: "       + getPhone()+ "\n" +
                "address: "     + getAddress()+ "\n" +
                "link: "        + getLink()+ "\n" +
                "currencies: \n" + getCurrencies();
    }
}
