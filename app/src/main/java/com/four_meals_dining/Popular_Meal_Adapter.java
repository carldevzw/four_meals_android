package com.four_meals_dining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Popular_Meal_Adapter extends RecyclerView.Adapter<PopularViewholder> {

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;

    public Popular_Meal_Adapter(Context context, ArrayList<Meal_Model> meal_ModelArrayList) {
        this.context = context;
        Meal_ModelArrayList = meal_ModelArrayList;
    }

    @NonNull
    @Override
    public PopularViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_suggestion, parent, false);
        return new PopularViewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PopularViewholder holder, int position) {

        Meal_Model model = Meal_ModelArrayList.get(position);
        holder.popualarMealNameTV.setText(model.getMeal_name());
        holder.popualarMealPriceTV.setText("" + model.getPrice());
        holder.popularMealIV.setImageResource(model.getMeal_imageSrc());
    }
    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

