package com.four_meals_dining;

import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Meal_Adapter extends RecyclerView.Adapter<Viewholder> {

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

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
        holder.mealPriceTV.setText(model.getPrice());
        Glide.with(context)
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .centerCrop()
                .into(holder.mealIV);
        holder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog= new ProgressDialog(context);
                progressDialog.setTitle("Adding to cart...");
                progressDialog.show();

                db= FirebaseFirestore.getInstance();
                Boolean confirmed= false;

                Map<String, Object> order = new HashMap<>();
                order .put("meal_name", model.getMeal_name());
                order .put("price", model.getPrice());
                order .put("meal_imageSrc", model.getMeal_imageSrc());
                order.put("Confirmed", confirmed);

                // Add a new document with a generated ID
                db.collection("orders")
                        .add(order )
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Error adding meal" + e, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

