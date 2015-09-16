package com.example.oliver.someexample.CustomView;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;

/**
 * Created by oliver on 16.09.15.
 */
public class OrgInfoCardView extends RelativeLayout {

    private TextView mOrgTitle, mOrgAddress, mOrgPhone, mOrgLink;

    public OrgInfoCardView(Context context) {
        super(context);

        inflate(context, R.layout.org_info_card, this);

        findViews();

    }

    private void findViews() {
        mOrgTitle   = (TextView) findViewById(R.id.tvTitleInfo_OIC);
        mOrgAddress = (TextView) findViewById(R.id.tvAddressInfo_OIC);
        mOrgPhone   = (TextView) findViewById(R.id.tvPhoneInfo_OIC);
        mOrgLink    = (TextView) findViewById(R.id.tvLink_Info_OIC);
    }

    public void setOrgInfo(OrgInfoModel _model) {
        mOrgTitle.setText(_model.getTitle());
        String fullAddress = _model.getRegionTitle();
        if (!fullAddress.equals(_model.getCityTitle())) {
            fullAddress = fullAddress + "\n" + _model.getCityTitle();
        }
        fullAddress = fullAddress + "\n" + _model.getAddress();
        mOrgAddress.setText(fullAddress);
        mOrgPhone.setText(_model.getPhone());

        mOrgLink.setClickable(true);
        mOrgLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + _model.getLink() + "'> " + _model.getTitle() + "</a>";
        mOrgLink.setText(Html.fromHtml(text));
    }
}
