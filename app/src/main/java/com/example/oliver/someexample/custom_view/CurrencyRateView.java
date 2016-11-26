package com.example.oliver.someexample.custom_view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oliver.someexample.models.MoneyModel;
import com.example.oliver.someexample.R;

/**
 * Created by oliver on 16.09.15.
 */
public class CurrencyRateView extends RelativeLayout {

    private TextView mCurrencyTitle, mAskValue, mBidValue;
    private ImageView mAskRate, mBidRate;

    public CurrencyRateView(Context context) {
        super(context);

        inflate(context, R.layout.currency_item, this);

        findViews();

    }

    private void findViews() {
        mCurrencyTitle = (TextView) findViewById(R.id.tvCurrencyTitle_CI);
        mAskValue = (TextView) findViewById(R.id.tvAskValue_CI);
        mBidValue = (TextView) findViewById(R.id.tvBidValue_CI);

        mAskRate = (ImageView) findViewById(R.id.ivAskRate_CI);
        mBidRate= (ImageView) findViewById(R.id.ivBidRate_CI);
    }

    public void setCurrency(String _title, MoneyModel _value) {
        mCurrencyTitle.setText(_title);

        mAskValue.setText(String.format("%7.04f", Double.valueOf(_value.ask)));
        mBidValue.setText(String.format("%7.04f", Double.valueOf(_value.bid)));
        switch (_value.ask_rate) {
            case -1:
                mAskValue.setTextColor(getResources().getColor(R.color.arrow_down));
                mAskRate.setImageResource(R.mipmap.ic_arrow_down);
                break;
            case 1:
                mAskValue.setTextColor(getResources().getColor(R.color.arrow_up));
                mAskRate.setImageResource(R.mipmap.ic_arrow_up);
                break;
            case 0:
                mAskValue.setTextColor(mCurrencyTitle.getCurrentTextColor());
                mAskRate.setImageBitmap(null);
                break;
        }
        switch (_value.bid_rate) {
            case -1:
                mBidValue.setTextColor(getResources().getColor(R.color.arrow_down));
                mBidRate.setImageResource(R.mipmap.ic_arrow_down);
                break;
            case 1:
                mBidValue.setTextColor(getResources().getColor(R.color.arrow_up));
                mBidRate.setImageResource(R.mipmap.ic_arrow_up);
                break;
            case 0:
                mBidValue.setTextColor(mCurrencyTitle.getCurrentTextColor());
                mBidRate.setImageBitmap(null);
                break;
        }
    }

}