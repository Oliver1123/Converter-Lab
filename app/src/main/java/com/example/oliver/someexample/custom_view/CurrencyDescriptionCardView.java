package com.example.oliver.someexample.custom_view;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.oliver.someexample.R;

/**
 * Created by oliver on 16.09.15.
 */
public class CurrencyDescriptionCardView extends RelativeLayout {

    public CurrencyDescriptionCardView(Context context) {
        super(context);
        inflate(context, R.layout.currency_description_card, this);
    }
}
