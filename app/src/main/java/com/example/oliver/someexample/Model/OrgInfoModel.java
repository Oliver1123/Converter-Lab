package com.example.oliver.someexample.Model;

import android.os.Parcel;
import android.os.Parcelable;

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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public String getCityTitle() {
        return cityTitle;
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

            return model;
        }

        @Override
        public OrgInfoModel[] newArray(int size) {
            return new OrgInfoModel[size];
        }
    };
}
