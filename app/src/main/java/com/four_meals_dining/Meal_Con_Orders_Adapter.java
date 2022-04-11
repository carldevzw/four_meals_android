package com.four_meals_dining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Meal_Con_Orders_Adapter extends RecyclerView.Adapter<ConfirmedViewholder> {

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;

    public Meal_Con_Orders_Adapter(Context context, ArrayList<Meal_Model> meal_ModelArrayList) {
        this.context = context;
        Meal_ModelArrayList = meal_ModelArrayList;
    }

    @NonNull
    @Override
    public ConfirmedViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_item, parent, false);
        return new ConfirmedViewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ConfirmedViewholder holder, int position) {

        Meal_Model model = Meal_ModelArrayList.get(position);
        holder.con_orderMealNameTV.setText(model.getMeal_name());
        holder.con_orderMealPriceTV.setText("" + model.getPrice());
        holder.con_orderMealIV.setImageResource(model.getMeal_imageSrc());
    }
    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

