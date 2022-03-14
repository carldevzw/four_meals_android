package com.four_meals_dining;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholder extends RecyclerView.ViewHolder {

    public ImageView mealIV;
    public TextView mealNameTV, mealPriceTV;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        mealIV = itemView.findViewById(R.id.IVFoodPicture);
        mealNameTV = itemView.findViewById(R.id.TVMealName);
        mealPriceTV = itemView.findViewById(R.id.TVMealPrice);
    }
}
