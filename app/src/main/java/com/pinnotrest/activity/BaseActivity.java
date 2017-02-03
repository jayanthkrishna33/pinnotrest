package com.pinnotrest.activity;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pinnotrest.R;

/**
 * Created by Jayanth on 03-02-2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected Snackbar getSuccessSnackBar(View iView, String iMessage, boolean isLong) {
        Snackbar snackbar;
        int length = isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT;
        snackbar = Snackbar.make(iView, iMessage, length);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#7ab849"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        return snackbar;
    }

    protected Snackbar getFailureSnackBar(View iView, String iMessage, boolean isLong) {
        Snackbar snackbar;
        int length = isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT;
        snackbar = Snackbar.make(iView, iMessage, length);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#F44336"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        return snackbar;
    }

    protected Snackbar getInfoSnackBar(View iView, String iMessage, boolean isLong) {
        Snackbar snackbar;
        int length = isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT;
        snackbar = Snackbar.make(iView, iMessage, length);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.yellow));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        return snackbar;
    }

}
