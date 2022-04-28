package com.four_meals_dining;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialogue {

    Activity activity;
    AlertDialog alertDialog;

    public LoadingDialogue(Activity activity) {
        this.activity = activity;
    }

    void startLoadingDialogue(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater=  activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.laoding_dialogue, null));
        builder.setCancelable(true);

        alertDialog= builder.create();
        alertDialog= builder.show();
    }

    void dismissDialogue(){

        alertDialog.dismiss();
    }
}
