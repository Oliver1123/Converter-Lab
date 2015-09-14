package com.example.oliver.someexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oliver.someexample.Model.OrgInfoModel;

import java.util.List;

/**
 * Created by oliver on 14.09.15.
 */
public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder>{


    private final Context mContext;
    private final List<OrgInfoModel> mData;

    OrgAdapter(Context _context, List<OrgInfoModel> _data) {
        mContext = _context;
        mData = _data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup _viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(_viewGroup.getContext())
                .inflate(R.layout.org_card, _viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder _viewHolder, int position) {
        OrgInfoModel orgInfo = mData.get(position);
        _viewHolder.onBind(orgInfo);
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle, mRegionTitle, mCityTitle, mPhone, mAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitle          = (TextView) itemView.findViewById(R.id.tvTitle_OC);
            mRegionTitle    = (TextView) itemView.findViewById(R.id.tvRegionTitle_OC);
            mCityTitle      = (TextView) itemView.findViewById(R.id.tvCityTitle_OC);
            mPhone          = (TextView) itemView.findViewById(R.id.tvPhone_OC);
            mAddress        = (TextView) itemView.findViewById(R.id.tvAddress_OC);
        }

        public void onBind(OrgInfoModel _orgInfoModel) {
            mTitle.         setText(_orgInfoModel.getTitle());
            mRegionTitle.   setText(_orgInfoModel.getRegionTitle());
            mCityTitle.     setText(_orgInfoModel.getCityTitle());
            mPhone.         setText(mContext.getResources().getString(R.string.phone_info) + _orgInfoModel.getPhone());
            mAddress.       setText(mContext.getResources().getString(R.string.address_info) + _orgInfoModel.getAddress());
        }
    }
}
