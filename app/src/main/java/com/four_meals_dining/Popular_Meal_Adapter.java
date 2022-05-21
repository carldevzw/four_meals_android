package com.four_meals_dining;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Popular_Meal_Adapter extends RecyclerView.Adapter<PopularViewholder> {

    private static final String TAG = "Popular_Meal_Adapter";

    private Context context;
    private ArrayList<Meal_Model> Meal_ModelArrayList;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

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
        holder.popualarMealPriceTV.setText("$" + model.getPrice());
        Glide.with(context)
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .fitCenter()
                .into(holder.popularMealIV);

        holder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();

                // Choose time zone in which you want to interpret your Date
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
                cal.setTime(date);
                String todaysDate = dateFormat.format(date);
                int day = cal.get(Calendar.DAY_OF_MONTH);

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
                order.put("Date", todaysDate);
                order.put("Count", model.getCount()+1);

                if(firebaseUser!=null){
                    String userID= firebaseUser.getUid();
                    DocumentReference userDoc= db.collection("users").document(userID);
                    userDoc.collection("orders").document(model.getMeal_name() + " " + day)
                            .set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Order entry into database");
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return Meal_ModelArrayList.size();
    }
}

