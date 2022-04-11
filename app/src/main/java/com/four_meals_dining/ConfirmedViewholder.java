package com.four_meals_dining;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConfirmedViewholder extends RecyclerView.ViewHolder {


    public ImageView  con_orderMealIV;
    public TextView con_orderMealNameTV, con_orderMealPriceTV;

    public ConfirmedViewholder(@NonNull View itemView) {
        super(itemView);
        con_orderMealIV = itemView.findViewById(R.id.IVOrderPicture);
        con_orderMealNameTV = itemView.findViewById(R.id.TVFoodItemOrder);
        con_orderMealPriceTV = itemView.findViewById(R.id.TVPriceOrder);
    }
}
