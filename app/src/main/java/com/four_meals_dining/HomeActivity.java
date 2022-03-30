package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView seeALl;

    private RecyclerView popularRV, suggestionRV;

    private ArrayList<Meal_Model> mealModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        popularRV= findViewById(R.id.RVHomeItems);
        suggestionRV= findViewById(R.id.RVSuggestion);

        mealModelArrayList = new ArrayList<>();

        mealModelArrayList.add(new Meal_Model("Egg-mayo Sandwich", 4.5, R.drawable.egg_sandwich));
        mealModelArrayList.add(new Meal_Model("Egg-mayo Sandwich", 4.5, R.drawable.egg_sandwich));
        mealModelArrayList.add(new Meal_Model("Egg-mayo Sandwich", 4.5, R.drawable.egg_sandwich));
        mealModelArrayList.add(new Meal_Model("Egg-mayo Sandwich", 4.5, R.drawable.egg_sandwich));
        // we are initializing our adapter class and passing our arraylist to it.
        Popular_Meal_Adapter mealAdapter = new Popular_Meal_Adapter(this,mealModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager popularLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager suggestionLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        popularRV.setLayoutManager(popularLinearLayoutManager);
        popularRV.setAdapter(mealAdapter);
        suggestionRV.setLayoutManager(suggestionLinearLayoutManager);
        suggestionRV.setAdapter(mealAdapter);

        seeALl= findViewById(R.id.TVSeeAll);

        seeALl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openList = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(openList);
            }
        });

    }
}