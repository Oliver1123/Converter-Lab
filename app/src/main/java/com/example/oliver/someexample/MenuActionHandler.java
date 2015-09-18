package com.example.oliver.someexample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.oliver.someexample.Activities.DetailActivity;
import com.example.oliver.someexample.Model.OrgInfoModel;

/**
 * Created by oliver on 18.09.15.
 */
public class MenuActionHandler {
    private final Context mContext;

    public MenuActionHandler(Context _context) {
        mContext = _context;
    }
    public void itemClickAction(int itemID, OrgInfoModel model) {
        Intent intent;
        switch (itemID) {
            case Constants.MENU_ITEM_LINK:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink()));
                mContext.startActivity(intent);
                break;
            case Constants.MENU_ITEM_PHONE:
                if (!Constants.UNKNOWN_VALUE.equals(model.getPhone())) {
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + model.getPhone()));
                    mContext.startActivity(intent);
                }
                break;
            case Constants.MENU_ITEM_MAP:
                //TODO show map
                showOnMap(model);
                break;
            case Constants.MENU_ITEM_MORE:
                intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Constants.ORG_INFO_MODEL_ARG, model);
                mContext.startActivity(intent);
                break;
        }
    }


    private void showOnMap(OrgInfoModel _currentModel) {
        StringBuilder address = new StringBuilder();
        if (!Constants.UNKNOWN_VALUE.equals(_currentModel.getRegionTitle())) {
            address.append(_currentModel.getRegionTitle());
        }
        if (!Constants.UNKNOWN_VALUE.equals(_currentModel.getCityTitle()) &&
            !_currentModel.getRegionTitle().equals(_currentModel.getCityTitle())) {
            address.append(_currentModel.getCityTitle());
        }
        if (!Constants.UNKNOWN_VALUE.equals(_currentModel.getAddress())) {
            address.append(_currentModel.getAddress());
        }

        Uri uri = Uri.parse("geo:0,0?q=" + address.toString());
        Log.d(Constants.TAG, "Show on map uri: " + uri.toString());

        mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
