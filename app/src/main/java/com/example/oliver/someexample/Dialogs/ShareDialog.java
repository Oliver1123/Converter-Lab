package com.example.oliver.someexample.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.oliver.someexample.Constants;
import com.example.oliver.someexample.Model.MoneyModel;
import com.example.oliver.someexample.Model.OrgInfoModel;
import com.example.oliver.someexample.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by oliver on 16.09.15.
 */
public class ShareDialog extends DialogFragment implements View.OnClickListener {
    private  Bitmap mBitmap;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        OrgInfoModel model = args.getParcelable(Constants.ORG_INFO_MODEL_ARG);
        mBitmap = createBitmap(model,
                getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                getResources().getDimensionPixelSize(R.dimen.abc_text_size_title_material),
                getResources().getDimensionPixelSize(R.dimen.abc_text_size_body_1_material),
                getResources().getDimensionPixelSize(R.dimen.abc_text_size_subhead_material));

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.share_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivShare);
        imageView.setImageBitmap(mBitmap);
        view.findViewById(R.id.btn_Share).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        File file = saveBitmapToFile(mBitmap, "file.png");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        startActivity(intent);
        dismiss();
    }

    private File saveBitmapToFile(Bitmap _bitmap, String fileName) {
        String filePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) ||
                (!Environment.isExternalStorageRemovable())) {
            filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            Log.d(Constants.TAG, "External storage: " + filePath);
        } else {
            filePath = getActivity().getFilesDir().getAbsolutePath();
            Log.d(Constants.TAG, "Internal storage: " + filePath);
        }
        File result = new File(filePath);
        if(result.exists())
            result.delete();
        try {
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(result);

                _bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            }finally {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Bitmap createBitmap(OrgInfoModel model, int padding,
                                int titleTextSize, int infoTextSize, int currenciesTextSize) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);


        int bitmap_width = getBitmapWidth(model, padding, titleTextSize, infoTextSize, currenciesTextSize);
        int bitmap_height = getBitmapHeight(model, padding, titleTextSize, infoTextSize, currenciesTextSize);

        Bitmap bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        int startX = padding;
        int startY = padding + titleTextSize;
                    // write Title
        p.setTextSize(titleTextSize);
        p.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
        canvas.drawText(model.getTitle(), startX, startY, p);

                    // write info regionTitle
        startY += titleTextSize;
        p.setTextSize(infoTextSize);
        p.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        canvas.drawText(model.getRegionTitle(), startX, startY, p);
        if (!model.getRegionTitle().equals(model.getCityTitle())) {
            startY +=infoTextSize;
            canvas.drawText(model.getCityTitle(), startX, startY, p);
        }

        startY += infoTextSize;
        canvas.drawText(model.getAddress(), startX, startY, p);
//////////////  Drawing Currencies

        startY += padding;
        p.setTextSize(currenciesTextSize);
        for (Map.Entry<String,MoneyModel> entry : model.getCurrencies().entrySet()) {

            String abb = entry.getKey();
            MoneyModel money = entry.getValue();

            String value = String.format("%07.04f/%07.04f", Double.valueOf(money.ask), Double.valueOf(money.bid));
            startY += currenciesTextSize + (padding / 2);
            p.setColor(getActivity().getResources().getColor(R.color.accent));
            canvas.drawText(abb, startX + padding, startY, p);
            p.setColor(Color.BLACK);
            canvas.drawText(value, bitmap_width - padding - p.measureText(value), startY, p);
        }

        Log.d(Constants.TAG, "Bitmap.KB: " + bitmap.getByteCount() / 1024 +
                " w: " + bitmap.getWidth() + " h:" + bitmap.getHeight());
        return  bitmap;
    }

    private int getBitmapHeight(OrgInfoModel _model, int _padding,
                                int titleTextSize, int infoTextSize, int currenciesTextSize) {
        float resultHeight = _padding;
        resultHeight += titleTextSize; // to write Title
        resultHeight += infoTextSize; // to write regionTitle
        if (!_model.getRegionTitle().equals(_model.getCityTitle())) {
            resultHeight += infoTextSize; // to  write cityTitle
        }
        resultHeight +=infoTextSize; // to write Address
        resultHeight += _padding; // padding
        if (_model.getCurrencies() != null) {
            resultHeight += (currenciesTextSize + (_padding / 2))* _model.getCurrencies().size(); // to write currencies
        }
        resultHeight += _padding;
        return Math.round(resultHeight);
    }

    private int getBitmapWidth(OrgInfoModel _model, int _padding,
                               int titleTextSize, int infoTextSize, int currenciesTextSize) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float maxTextWidth = 0;

        paint.setTextSize(titleTextSize);
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
        float textMeasure = paint.measureText(_model.getTitle()); // Title row
        if (textMeasure > maxTextWidth) {maxTextWidth = textMeasure;}

        paint.setTextSize(infoTextSize);
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        textMeasure = paint.measureText(_model.getRegionTitle()); // regionTitle row
        if (textMeasure > maxTextWidth) {maxTextWidth = textMeasure;}

        if (!_model.getRegionTitle().equals(_model.getCityTitle())) {
            textMeasure = paint.measureText(_model.getCityTitle());  //  cityTitle row
            if (textMeasure > maxTextWidth) {
                maxTextWidth = textMeasure;
            }
        }

        textMeasure = paint.measureText(_model.getAddress()); // Address row
        if (textMeasure > maxTextWidth) {maxTextWidth = textMeasure;}

        paint.setTextSize(currenciesTextSize);
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        if (_model.getCurrencies() != null) {
            for (Map.Entry<String, MoneyModel> entry : _model.getCurrencies().entrySet()) {
                float abbTextMeasure = paint.measureText(entry.getKey());
                MoneyModel money = entry.getValue();
                String value = String.format("%07.04f/%07.04f", Double.valueOf(money.ask), Double.valueOf(money.bid));

                textMeasure = paint.measureText(value);
                if (_padding + abbTextMeasure + _padding + textMeasure + _padding > maxTextWidth) {
                    maxTextWidth = _padding + abbTextMeasure + _padding + textMeasure + _padding;
                }
            }
        }
        return Math.round(_padding + maxTextWidth + _padding);
    }
}
