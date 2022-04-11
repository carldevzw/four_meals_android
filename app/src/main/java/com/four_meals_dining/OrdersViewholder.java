package com.four_meals_dining;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersViewholder extends RecyclerView.ViewHolder {


    public ImageView  orderMealIV;
    public TextView orderMealNameTV, orderMealPriceTV;

    public OrdersViewholder(@NonNull View itemView) {
        super(itemView);
        orderMealIV = itemView.findViewById(R.id.IVOrderPicture);
        orderMealNameTV = itemView.findViewById(R.id.TVFoodItemOrder);
        orderMealPriceTV = itemView.findViewById(R.id.TVPriceOrder);
    }
}
