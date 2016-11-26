package com.example.oliver.someexample;


import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by oliver on 15.09.15.
 */
public final class FABController implements View.OnClickListener {

    public interface FABActionListener {
        void onItemSelected(int itemID);
    }

    private FloatingActionMenu mQuickMenu;
    private FABActionListener mActionListener;

    private FloatingActionButton mMap, mLink, mPhone;

    public void register(FABActionListener actionListener, View view) {
        mActionListener = actionListener;
        mQuickMenu = (FloatingActionMenu) view;
        findUI();
        initListeners();
    }

    private void findUI() {
        mMap        = (FloatingActionButton) mQuickMenu.findViewById(R.id.fbMap);
        mLink       = (FloatingActionButton) mQuickMenu.findViewById(R.id.fbLink);
        mPhone      = (FloatingActionButton) mQuickMenu.findViewById(R.id.fbPhone);
    }

    private void initListeners() {
        mMap.setOnClickListener(this);
        mLink.setOnClickListener(this);
        mPhone.setOnClickListener(this);
    }

    private void sendClickToActivity(int id) {
        ((FABActionListener) mActionListener).onItemSelected(id);
    }


    public void setVisibility(final int isVisible){
            mQuickMenu.setVisibility(isVisible);
    }


    @Override
    public void onClick(View v) {
        mQuickMenu.close(false);
        switch (v.getId()){
            case R.id.fbMap:
                sendClickToActivity(Constants.MENU_ITEM_MAP);
                break;
            case R.id.fbLink:
                sendClickToActivity(Constants.MENU_ITEM_LINK);
                break;
            case R.id.fbPhone:
                sendClickToActivity(Constants.MENU_ITEM_PHONE);
                break;
        }
    }
}
