package com.example.oliver.someexample.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oliver on 13.09.15.
 */
public class MoneyModel implements Parcelable{
    public String ask;
    public String bid;
    public int ask_rate;
    public int bid_rate;

    public MoneyModel(String _ask, String _bid, int _ask_rate, int _bid_rate) {
        ask = _ask;
        bid = _bid;
        ask_rate = _ask_rate;
        bid_rate = _bid_rate;
    }

    @Override
    public String toString() {
        String askResult;
        switch (ask_rate) {
            case -1:
                askResult = "[<<";
                break;
            case 1:
                askResult = "[>>";
                break;
            default:
                askResult = "[  ";
        }
        askResult = askResult + ask + "]";
        String bidResult;
        switch (bid_rate) {
            case -1:
                bidResult = "[<<";
                break;
            case 1:
                bidResult = "[>>";
                break;
            default:
                bidResult = "[  ";
        }
        bidResult = bidResult + bid+ "]";
        return    "ask=" + askResult + ", bid=" + bidResult;
    }

    public void setRate(MoneyModel prevValue){
        if (prevValue == null) {
            ask_rate = 0;
            bid_rate = 0;
            return;
        }
        double currentAsk = Double.valueOf(ask);
        double prevAsk = Double.valueOf(prevValue.ask);
        if (currentAsk <  prevAsk) {ask_rate = -1;}
        if (currentAsk == prevAsk) {ask_rate = 0;}
        if (currentAsk >  prevAsk) {ask_rate = 1;}

        double currentBid = Double.valueOf(bid);
        double prevBid = Double.valueOf(prevValue.bid);
        if (currentBid <  prevBid) {bid_rate = -1;}
        if (currentBid == prevBid) {bid_rate = 0;}
        if (currentBid >  prevBid) {bid_rate = 1;}
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(ask);
       dest.writeString(bid);
       dest.writeInt(ask_rate);
       dest.writeInt(bid_rate);
    }

    public static final Parcelable.Creator<MoneyModel> CREATOR = new Parcelable.Creator<MoneyModel>(){

        @Override
        public MoneyModel createFromParcel(Parcel source) {
         String ask = source.readString();
         String bid = source.readString();
            int ask_rate = source.readInt();
            int bid_rate = source.readInt();
            return new MoneyModel(ask, bid, ask_rate, bid_rate);
        }

        @Override
        public MoneyModel[] newArray(int size) {
            return new MoneyModel[size];
        }
    };
}
