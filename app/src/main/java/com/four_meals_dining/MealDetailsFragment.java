package com.four_meals_dining;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MealDetailsFragment extends Fragment {

    View view;
    TextInputLayout tilMealName, tilMealPrice;
    TextInputEditText tietMealName, tietMealPrice;
    Button btnUpload, btnAdd;
    ImageView mealImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_meal_details, container, false);

//        tilMealName= view.findViewById(R.id.til_mealNam);
//        tilMealPrice= getView().findViewById(R.id.til_price);
//        tietMealName= getView().findViewById(R.id.tiet_mealName);
//        tietMealPrice= getView().findViewById(R.id.tiet_price);
//        btnUpload= getView().findViewById(R.id.btnUploadImage);
//        btnAdd= getView().findViewById(R.id.btnAdd);
//        mealImage= getView().findViewById(R.id.IVUploadPicture);

        return view;
    }
}