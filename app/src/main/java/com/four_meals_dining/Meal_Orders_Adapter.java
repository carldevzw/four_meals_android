package com.four_meals_dining;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Meal_Orders_Adapter extends RecyclerView.Adapter<OrdersViewholder> {

    private static final String TAG = "Meal_Orders_Adapter";
    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;
    FirebaseFirestore db;

    public Meal_Orders_Adapter(Context context, ArrayList<Meal_Model> meal_ModelArrayList) {
        this.context = context;
        Meal_ModelArrayList = meal_ModelArrayList;
    }

    public void deleteFromCart(int position){
        Meal_Model model = Meal_ModelArrayList.get(position);
        db= FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userId= firebaseUser.getUid();
        
        CollectionReference collectionReference= db.collection("users").document(userId).collection("orders");
        DocumentReference documentReference= db.collection("users").document(userId).collection("orders").document(model.getDocumentID());

        documentReference.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: Order removed from database.");
                        }
                    }
                });
    }

    @NonNull
    @Override
    public OrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrdersViewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrdersViewholder holder, int position) {

        Meal_Model model = Meal_ModelArrayList.get(position);
        holder.orderMealNameTV.setText(model.getMeal_name());
        holder.orderMealPriceTV.setText("$" + model.getPrice());
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

