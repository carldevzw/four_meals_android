package com.four_meals_dining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Available_Meals_Adapter extends RecyclerView.Adapter<OrdersViewholder> {

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;

    public Available_Meals_Adapter(Context context, ArrayList<Meal_Model> meal_ModelArrayList) {
        this.context = context;
        Meal_ModelArrayList = meal_ModelArrayList;
    }

    @NonNull
    @Override
    public OrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_item, parent, false);
        return new OrdersViewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrdersViewholder holder, int position) {

        Meal_Model model = Meal_ModelArrayList.get(position);
        holder.orderMealNameTV.setText(model.getMeal_name());
        holder.orderMealPriceTV.setText(model.getPrice());
        Glide.with(context)
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .centerCrop()
        .into(holder.orderMealIV);

    }
    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

