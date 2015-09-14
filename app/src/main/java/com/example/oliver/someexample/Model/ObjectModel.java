package com.example.oliver.someexample.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oliver on 13.09.15.
 */
public class ObjectModel implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        if (organizations != null) {
            dest.writeInt(organizations.size());
            for (OrganizationModel org : organizations) {
                dest.writeParcelable(org, 0);
            }
        } else {dest.writeInt(-1);}

        if (currencies != null) {
            dest.writeInt(currencies.size());
            for(Map.Entry<String, String> entry: currencies.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        } else {dest.writeInt(-1);}

        if (regions != null) {
            dest.writeInt(regions.size());
            for(Map.Entry<String, String> entry: regions.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        } else {dest.writeInt(-1);}

        if (cities != null) {
            dest.writeInt(cities.size());
            for(Map.Entry<String, String> entry: cities.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        } else {dest.writeInt(-1);}

    }

    public static final Parcelable.Creator<ObjectModel> CREATOR = new Parcelable.Creator<ObjectModel>(){

        @Override
        public ObjectModel createFromParcel(Parcel source) {
            ObjectModel model = new ObjectModel();
            model.date = source.readString();

            int orgSize = source.readInt();
            if (orgSize > -1) {
                List<OrganizationModel> listOrg = new ArrayList<>(orgSize);
                for (int i = 0; i < orgSize; i++) {
                    listOrg.add((OrganizationModel) source.readParcelable(OrganizationModel.class.getClassLoader()));
                }
                model.organizations = listOrg;
            }else {model.organizations = null;}

            int currSize = source.readInt();
            if (currSize > -1) {
                Map<String, String> mapCurrencies = new HashMap<>(currSize);
                for (int i = 0; i < currSize; i++) {
                    String key = source.readString();
                    String value = source.readString();
                    mapCurrencies.put(key, value);
                }
                model.currencies = mapCurrencies;
            } else {model.currencies = null;}

            int regSize = source.readInt();
            if (regSize > -1) {
                Map<String, String> mapRegions = new HashMap<>(regSize);
                for (int i = 0; i < regSize; i++) {
                    String key = source.readString();
                    String value = source.readString();
                    mapRegions.put(key, value);
                }
                model.regions = mapRegions;
            }else {model.regions = null;}

            int citySize = source.readInt();
            if (citySize > -1) {
                Map<String, String> mapCities = new HashMap<>(citySize);
                for (int i = 0; i < citySize; i++) {
                    String key = source.readString();
                    String value = source.readString();
                    mapCities.put(key, value);
                }
                model.cities = mapCities;
            }else {model.cities = null;}

            return model;
        }

        @Override
        public ObjectModel[] newArray(int size) {
            return new ObjectModel[size];
        }
    };
}
