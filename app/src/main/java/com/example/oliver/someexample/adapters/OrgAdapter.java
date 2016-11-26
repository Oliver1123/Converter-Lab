package com.example.oliver.someexample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.MenuActionHandler;
import com.example.oliver.someexample.models.OrgInfoModel;
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
    public OrgAdapter(Context _context) {
        mContext = _context;
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
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle_OC);
            mRegionTitle = (TextView) itemView.findViewById(R.id.tvRegionTitle_OC);
            mCityTitle = (TextView) itemView.findViewById(R.id.tvCityTitle_OC);
            mPhone = (TextView) itemView.findViewById(R.id.tvPhone_OC);
            mAddress = (TextView) itemView.findViewById(R.id.tvAddress_OC);

            itemView.findViewById(R.id.ivCall_OC).setOnClickListener(this);
            itemView.findViewById(R.id.ivLink_OC).setOnClickListener(this);
            itemView.findViewById(R.id.ivMap_OC).setOnClickListener(this);
            itemView.findViewById(R.id.ivMore_OC).setOnClickListener(this);
        }

        public void onBind(OrgInfoModel _orgInfoModel) {
            mCurrentModel = _orgInfoModel;

            mTitle.setText(_orgInfoModel.getTitle());
            mRegionTitle.setText(_orgInfoModel.getRegionTitle());
            if (!_orgInfoModel.getRegionTitle().equals(_orgInfoModel.getCityTitle())) {
                mCityTitle.setText(_orgInfoModel.getCityTitle());
            } else {
                mCityTitle.setText("");
            }

            mPhone.setText(mContext.getString(R.string.phone_info) + _orgInfoModel.getPhone());
            mAddress.setText(mContext.getString(R.string.address_info) + _orgInfoModel.getAddress());
        }

        @Override
        public void onClick(View v) {
            int itemID = Constants.MENU_ITEM_LINK;
            switch (v.getId()) {
                case R.id.ivLink_OC:
                    itemID = Constants.MENU_ITEM_LINK;
                    break;
                case R.id.ivCall_OC:
                    itemID = Constants.MENU_ITEM_PHONE;
                    break;
                case R.id.ivMap_OC:
                    itemID = Constants.MENU_ITEM_MAP;
                    break;
                case R.id.ivMore_OC:
                    itemID = Constants.MENU_ITEM_MORE;
                    break;

            }
            new MenuActionHandler(mContext).itemClickAction(itemID, mCurrentModel);
        }
    }
}
