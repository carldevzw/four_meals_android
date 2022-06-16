package com.four_meals_dining;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Available_Meals_Adapter extends RecyclerView.Adapter<OrdersViewholder> {

    private static final String TAG = "Available_Meals_Adapter";
    private FirebaseFirestore db;
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
        holder.orderMealPriceTV.setText("$" + model.getPrice());
        Glide.with(context)
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .centerCrop()
        .into(holder.orderMealIV);

        if(model.isReady()){
            holder.switchMaterial.setChecked(true);
        }

        holder.switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


                // Choose time zone in which you want to interpret your Date
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
                int day = cal.get(Calendar.DAY_OF_MONTH);

                db= FirebaseFirestore.getInstance();
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                String userId= firebaseUser.getUid();

                DocumentReference documentMealsReference= db.collection("meals").document(model.getMeal_name() + " " + day);
                DocumentReference documentReference= db.collection("users").document(userId).collection("orders").document(model.getMeal_name() + " " + day);

                documentMealsReference.update("Ready", true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Meal ready");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Meal failed to set ready...");
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

