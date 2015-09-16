package com.example.oliver.someexample.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oliver.someexample.Activities.DetailActivity;
import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;

import java.util.List;

/**
 * Created by oliver on 14.09.15.
 */
public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder>{

    private final Context mContext;
    private  List<OrgInfoModel> mData;

    public OrgAdapter(Context _context, List<OrgInfoModel> _data) {
        mContext = _context;
        mData = _data;
    }
    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setData(List<OrgInfoModel> _data) {
        mData = _data;
        notifyDataSetChanged();
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mRegionTitle, mCityTitle, mPhone, mAddress;
        private OrgInfoModel mCurrentModel;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitle          = (TextView) itemView.findViewById(R.id.tvTitle_OC);
            mRegionTitle    = (TextView) itemView.findViewById(R.id.tvRegionTitle_OC);
            mCityTitle      = (TextView) itemView.findViewById(R.id.tvCityTitle_OC);
            mPhone          = (TextView) itemView.findViewById(R.id.tvPhone_OC);
            mAddress        = (TextView) itemView.findViewById(R.id.tvAddress_OC);

            itemView.findViewById(R.id.ivCall_OC).setOnClickListener(this);
            itemView.findViewById(R.id.ivLink_OC).setOnClickListener(this);
            itemView.findViewById(R.id.ivMap_OC).setOnClickListener(this);
            itemView.findViewById(R.id.ivMore_OC).setOnClickListener(this);
        }

        public void onBind(OrgInfoModel _orgInfoModel) {
            mCurrentModel = _orgInfoModel;

            mTitle.         setText(_orgInfoModel.getTitle());
            mRegionTitle.   setText(_orgInfoModel.getRegionTitle());
            mCityTitle.     setText(_orgInfoModel.getCityTitle());
            mPhone.         setText(mContext.getResources().getString(R.string.phone_info) + _orgInfoModel.getPhone());
            mAddress.       setText(mContext.getResources().getString(R.string.address_info) + _orgInfoModel.getAddress());
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.ivLink_OC:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mCurrentModel.getLink()));
                    mContext.startActivity(intent);
                    break;
                case R.id.ivCall_OC:
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mCurrentModel.getPhone()));
                    mContext.startActivity(intent);
                    break;
                case R.id.ivMap_OC:
                    //TODO show map
                    showOnMap(mCurrentModel);
                    break;
                case R.id.ivMore_OC:
                    intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(Constants.ORG_INFO_MODEL_ARG, mCurrentModel);
                    mContext.startActivity(intent);
                    break;

            }
        }

        private void showOnMap(OrgInfoModel _currentModel) {
            String address = _currentModel.getRegionTitle();
            if (!address.equals(_currentModel.getCityTitle())) {
                address = address + " " + _currentModel.getCityTitle();
            }
            address = address + " " + _currentModel.getAddress();
            Uri uri = Uri.parse("geo:0,0?q=" + address);
            Log.d(Constants.TAG, "Show on map uri: " + uri.toString());

            mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
