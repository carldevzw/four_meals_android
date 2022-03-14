package com.four_meals_dining;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Meal_Adapter extends RecyclerView.Adapter<Viewholder> {

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;

    public Meal_Adapter(Context context, ArrayList<Meal_Model> meal_ModelArrayList) {
        this.context = context;
        Meal_ModelArrayList = meal_ModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items, parent, false);
        return new Viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Meal_Model model = Meal_ModelArrayList.get(position);
        holder.mealNameTV.setText(model.getMeal_name());
        holder.mealPriceTV.setText("" + model.getPrice());
        holder.mealIV.setImageResource(model.getMeal_imageSrc());
    }
    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

