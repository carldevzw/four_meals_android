package com.four_meals_dining;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AvailableMealsFragment extends Fragment {

    RecyclerView mealRV;
    private ArrayList<Meal_Model> mealModelArrayList;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_available_meals, container, false);

        listData();
        initRecyclerView(view);

        return view;
    }

    public void initRecyclerView(View view) {

        RecyclerView availableRecyclerView = view.findViewById(R.id.RVMealItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        availableRecyclerView.setLayoutManager(linearLayoutManager);

        Available_Meals_Adapter available_meals_adapter = new Available_Meals_Adapter(getActivity(), mealModelArrayList);
        availableRecyclerView.setAdapter(available_meals_adapter);
    }

    public void listData(){
        mealModelArrayList = new ArrayList<>();
        mealModelArrayList.add(new Meal_Model("Bread and Eggs", 4.5, R.drawable.egg_sandwich));
        mealModelArrayList.add(new Meal_Model("Plain Chips", 3.2, R.drawable.plain_chips));
        mealModelArrayList.add(new Meal_Model("Bread and Eggs", 4.5, R.drawable.egg_sandwich));
        mealModelArrayList.add(new Meal_Model("Plain Chips", 3.2, R.drawable.plain_chips));
        mealModelArrayList.add(new Meal_Model("Plain Rice", 4.12, R.drawable.plain_rice));
        mealModelArrayList.add(new Meal_Model("Sadza Beans", 1.5, R.drawable.sadza_beans));
        mealModelArrayList.add(new Meal_Model("Sadza Beef", 2, R.drawable.sadza_beef));
        mealModelArrayList.add(new Meal_Model("Sadza Chicken", 6.5, R.drawable.sadza_chicken));

    }
}