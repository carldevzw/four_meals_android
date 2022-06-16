package com.four_meals_dining;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class OrdersViewholder extends RecyclerView.ViewHolder {

    public SwitchMaterial switchMaterial;
    public ImageButton btnSetReady;
    public ImageView  orderMealIV;
    public TextView orderMealNameTV, orderMealPriceTV;

    public OrdersViewholder(@NonNull View itemView) {
        super(itemView);
        switchMaterial= itemView.findViewById(R.id.switchReady);
        btnSetReady = itemView.findViewById(R.id.IBReady);
        orderMealIV = itemView.findViewById(R.id.IVOrderPicture);
        orderMealNameTV = itemView.findViewById(R.id.TVFoodItemOrder);
        orderMealPriceTV = itemView.findViewById(R.id.TVPriceOrder);
    }
}
