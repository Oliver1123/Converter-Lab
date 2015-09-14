package com.example.oliver.someexample.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getTitle());
        dest.writeString(getRegionTitle());
        dest.writeString(getCityTitle());
        dest.writeString(getPhone());
        dest.writeString(getAddress());
        dest.writeString(getLink());

        Map<String, MoneyModel> currencies = getCurrencies();
        if (currencies != null) {
            dest.writeInt(currencies.size());
            for (Map.Entry<String, MoneyModel> entry : currencies.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeParcelable(entry.getValue(), 0);
            }
        } else {
            dest.writeInt(-1);
        }
    }

    public static final Parcelable.Creator<CompleteOrganizationModel> CREATOR = new Parcelable.Creator<CompleteOrganizationModel>(){

        @Override
        public CompleteOrganizationModel createFromParcel(Parcel source) {
            CompleteOrganizationModel model = new CompleteOrganizationModel();
            model.setId(source.readString())
                 .setTitle(source.readString())
                 .setRegionTitle(source.readString())
                 .setCityTitle(source.readString())
                 .setPhone(source.readString())
                 .setAddress(source.readString())
                 .setLink(source.readString());

            int mapSize = source.readInt();
            if (mapSize > -1) {
                Map<String, MoneyModel> map = new HashMap<>(mapSize);
                for (int i = 0; i < mapSize; i++) {
                    String key = source.readString();
                    MoneyModel value = source.readParcelable(MoneyModel.class.getClassLoader());
                    map.put(key, value);
                }
                model.setCurrencies(map);
            } else {
                model.setCurrencies(null);
            }


            return model;
        }

        @Override
        public CompleteOrganizationModel[] newArray(int size) {
            return new CompleteOrganizationModel[size];
        }
    };
}
