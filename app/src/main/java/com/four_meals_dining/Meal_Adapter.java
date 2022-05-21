package com.four_meals_dining;

import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

public class Meal_Adapter extends RecyclerView.Adapter<Viewholder> {

    private static final String TAG = "Meal_Adapter";
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
        holder.mealPriceTV.setText("$" + model.getPrice());
        Glide.with(context)
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .centerCrop()
                .into(holder.mealIV);

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

