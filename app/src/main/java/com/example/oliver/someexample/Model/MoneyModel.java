package com.example.oliver.someexample.Model;

/**
 * Created by oliver on 13.09.15.
 */
public class MoneyModel {
    public String ask;
    public String bid;

    public MoneyModel(String _ask, String _bid) {
        ask = _ask;
        bid = _bid;
    }

    @Override
    public String toString() {
        return    "ask=" + ask + ", bid=" + bid;
    }
}
