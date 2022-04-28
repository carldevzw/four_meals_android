package com.four_meals_dining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AvailableMealsAdapter extends FirestoreRecyclerAdapter<Meal_Model, AvailableMealsAdapter.MealsHolder> {

    public AvailableMealsAdapter(@NonNull FirestoreRecyclerOptions<Meal_Model> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull MealsHolder holder, int position, @NonNull Meal_Model model) {
            holder.availableMealNameTV.setText(model.getMeal_name());
            holder.availableMealPriceTV.setText(model.getPrice());


        Glide.with(holder.availableMealIV.getContext())
                .load(model.getMeal_imageSrc())
                .placeholder(R.drawable.ic_baseline_no_food_24)
                .centerCrop()
                .into(holder.availableMealIV);

           // holder.availableMealIV.setImageResource(model.getMeal_imageSrc());

    }

    @NonNull
    @Override
    public MealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.available_item,
                parent, false);
        return new MealsHolder(view);
    }

    class MealsHolder extends RecyclerView.ViewHolder{
        public ImageView availableMealIV;
        public TextView availableMealNameTV, availableMealPriceTV;

        public MealsHolder(@NonNull View itemView) {
            super(itemView);
            availableMealIV = itemView.findViewById(R.id.IVOrderPicture);
            availableMealNameTV = itemView.findViewById(R.id.TVFoodItemOrder);
            availableMealPriceTV = itemView.findViewById(R.id.TVPriceOrder);
        }
    }
}
