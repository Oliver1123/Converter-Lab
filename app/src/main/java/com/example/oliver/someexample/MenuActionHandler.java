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
                if (!model.getPhone().isEmpty()) {
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
