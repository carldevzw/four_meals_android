package com.four_meals_dining;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdmin_Viewholder extends RecyclerView.ViewHolder {


    public ImageView  con_orderMealIV;
    public TextView con_orderMealNameTV, con_orderMealPriceTV, orderAmnt;

    public OrdersAdmin_Viewholder(@NonNull View itemView) {
        super(itemView);
        orderAmnt= itemView.findViewById(R.id.TVMealOrders);
        con_orderMealIV = itemView.findViewById(R.id.IVOrderPicture);
        con_orderMealNameTV = itemView.findViewById(R.id.TVFoodItemOrder);
        con_orderMealPriceTV = itemView.findViewById(R.id.TVPriceOrder);
    }
}
