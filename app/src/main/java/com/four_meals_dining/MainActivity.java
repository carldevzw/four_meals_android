package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mealRV;

    private ArrayList<Meal_Model> mealModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mealRV= findViewById(R.id.RVMealItems);

        mealModelArrayList = new ArrayList<>();

        mealModelArrayList.add(new Meal_Model("Bread and Eggs", 4.5, R.drawable.bread));
        mealModelArrayList.add(new Meal_Model("Plain Chips", 3.2, R.drawable.chips));
        mealModelArrayList.add(new Meal_Model("PLain Rice", 4.12, R.drawable.plain_rice));
        mealModelArrayList.add(new Meal_Model("Sadza Beans", 1.5, R.drawable.sadza_beans));
        mealModelArrayList.add(new Meal_Model("Sadza Beef", 2, R.drawable.sadza_beef));
        mealModelArrayList.add(new Meal_Model("Sadza Chicken", 6.5, R.drawable.sadza_chicken));

        // we are initializing our adapter class and passing our arraylist to it.
        Meal_Adapter mealAdapter = new Meal_Adapter(this,mealModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        mealRV.setLayoutManager(layoutManager);
        mealRV.setAdapter(mealAdapter);

  }
}