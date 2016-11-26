package com.example.oliver.someexample.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.oliver.someexample.custom_view.CurrencyRateView;
import com.example.oliver.someexample.models.MoneyModel;

import java.util.List;

/**
 * Created by oliver on 16.09.15.
 */
public class CurrencyAdapter  extends BaseAdapter{

    private final Context mContext;
    private   List<Pair<String, MoneyModel>>  mData;

    public CurrencyAdapter(Context _context, List<Pair<String, MoneyModel>> _data) {
        mContext = _context;
        mData = _data;
    }

    public CurrencyAdapter(Context _context) {
        mContext = _context;
    }
    public void setData ( List<Pair<String, MoneyModel>> _data) {
        mData = _data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return (mData == null) ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return (mData == null) ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Pair<String, MoneyModel> item = mData.get(position);

        View rootView = convertView;
        if (rootView == null) {
            rootView = new CurrencyRateView(mContext);
        }

        ((CurrencyRateView) rootView).setCurrency(item.first, item.second);

        return rootView;
    }
}
