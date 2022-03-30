package com.four_meals_dining;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularViewholder extends RecyclerView.ViewHolder {


    public ImageView  popularMealIV;
    public TextView popualarMealNameTV, popualarMealPriceTV;

    public PopularViewholder(@NonNull View itemView) {
        super(itemView);
        popularMealIV = itemView.findViewById(R.id.IVPopularFood);
        popualarMealNameTV = itemView.findViewById(R.id.TVPopularFoodName);
        popualarMealPriceTV = itemView.findViewById(R.id.TVPopularPrice);
    }
}
