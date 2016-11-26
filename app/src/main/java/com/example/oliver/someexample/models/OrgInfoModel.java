package com.example.oliver.someexample.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.oliver.someexample.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oliver on 14.09.15.
 */
public class OrgInfoModel implements Parcelable{
    private String id;
    private String title;
    private String regionTitle;
    private String cityTitle;
    private String phone;
    private String address;
    private String link;
    private Map<String, MoneyModel> currencies = null;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return (TextUtils.isEmpty(title))? Constants.UNKNOWN_VALUE : title;
    }

    public String getRegionTitle() {
        return (TextUtils.isEmpty(regionTitle))? Constants.UNKNOWN_VALUE : regionTitle;
    }

    public String getCityTitle() {
        return (TextUtils.isEmpty(cityTitle))? Constants.UNKNOWN_VALUE: cityTitle;
    }

    public String getPhone() {
        return (TextUtils.isEmpty(phone))? Constants.UNKNOWN_VALUE : phone;
    }

    public String getAddress() {
        return (TextUtils.isEmpty(address))? Constants.UNKNOWN_VALUE : address;
    }

    public String getLink() {
        return link;
    }

    public Map<String, MoneyModel> getCurrencies() {
        return currencies;
    }

    public OrgInfoModel setId(String _id) {
        id = _id;
        return  this;
    }

    public OrgInfoModel setTitle(String _title) {
        title = _title;
        return  this;
    }

    public OrgInfoModel setRegionTitle(String _regionTitle) {
        regionTitle = _regionTitle;
        return  this;
    }

    public OrgInfoModel setCityTitle(String _cityTitle) {
        cityTitle = _cityTitle;
        return  this;
    }

    public OrgInfoModel setPhone(String _phone) {
        phone = _phone;
        return  this;
    }

    public OrgInfoModel setAddress(String _address) {
        address = _address;
        return  this;
    }

    public OrgInfoModel setLink(String _link) {
        link = _link;
        return  this;
    }

    public OrgInfoModel setCurrencies(Map<String, MoneyModel> _currencies) {
        currencies = _currencies;
        return  this;
    }

    @Override
    public String toString() {
        return  "id: "          + getId() + "\n" +
                "title: "       + getTitle()  + "\n" +
                "regionTitle: " + getRegionTitle()+ "\n" +
                "cityTitle: "   + getCityTitle()+ "\n" +
                "phone: "       + getPhone()+ "\n" +
                "address: "     + getAddress()+ "\n" +
                "link: "        + getLink();
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
        if (currencies != null) {
            dest.writeInt(currencies.size());
            for (Map.Entry<String, MoneyModel> entry: currencies.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeParcelable(entry.getValue(), flags);
            }
        } else {
            dest.writeInt(-1);
        }
    }

    public static final Parcelable.Creator<OrgInfoModel> CREATOR = new Parcelable.Creator<OrgInfoModel>(){

        @Override
        public OrgInfoModel createFromParcel(Parcel source) {
            OrgInfoModel model = new OrgInfoModel();
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
            }

            return model;
        }

        @Override
        public OrgInfoModel[] newArray(int size) {
            return new OrgInfoModel[size];
        }
    };

    public boolean containStr(String str) {
        String sourceStr = getTitle() + " " + getRegionTitle() + " " + getCityTitle();

        String[] sourceWords = sourceStr.toLowerCase().trim().split(" ");
        String searchStr = str.toLowerCase().trim();

        for (String word : sourceWords) {
            if (word.startsWith(searchStr))
                return true;
        }

        String[] searchLetters = searchStr.split("");
        for (int i = 0; i < searchLetters.length;) {
            boolean containLetter = false;
            for (String word : sourceWords) {
                String searchPart = searchLetters[i].trim();
                while (word.startsWith(searchPart)) {
                    containLetter = true;
                    i++;
                    if (i == searchLetters.length)
                        return true;
                    searchPart = searchPart + searchLetters[i].trim();
                }
            }
            if (!containLetter) return false;
        }
        return true;
    }
}
