package com.four_meals_dining;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Meal_Orders_Admin_Adapter extends RecyclerView.Adapter<OrdersAdmin_Viewholder> {

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;
    FirebaseFirestore db;

    public Meal_Orders_Admin_Adapter(Context context, ArrayList<Meal_Model> meal_ModelArrayList) {
        this.context = context;
        Meal_ModelArrayList = meal_ModelArrayList;
    }
    public void count(int position) {
        String TAG= "Meal orders adapter.";
        Meal_Model model = Meal_ModelArrayList.get(position);
        db = FirebaseFirestore.getInstance();
        db.collection("meals").document(model.getDocumentID()).update("Count", model.getCount()+1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Meals count +1");
                        }
                    }
                });
    }

    @NonNull
    @Override
    public OrdersAdmin_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_num_item, parent, false);
        return new OrdersAdmin_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdmin_Viewholder holder, int position) {

        Meal_Model model = Meal_ModelArrayList.get(position);
        holder.con_orderMealNameTV.setText(model.getMeal_name());
        holder.orderAmnt.setText(String.valueOf(model.getCount()));

        Glide.with(context)
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .centerCrop()
                .into(holder.con_orderMealIV);
    }

    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

