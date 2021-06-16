package com.example.cleaningservices.loading;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.cleaningservices.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity myActivity){
    activity = myActivity;

    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custem_dilog , null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissLoadingDialog(){
        alertDialog.dismiss();
    }


}
