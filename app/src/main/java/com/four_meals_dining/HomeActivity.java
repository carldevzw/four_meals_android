package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView seeALl;

    private RecyclerView popularRV, suggestionRV;

    private ArrayList<Meal_Model> mealModelArrayList;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView= findViewById(R.id.nav_bottom);
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

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:{
                    Toast.makeText(HomeActivity.this, "Already Home", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.account:{
                    Intent openCart= new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);

                    break;
                }
                case R.id.cart:{
                    Intent openCart= new Intent(HomeActivity.this, OrdersActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
            }
            return true;
        });

    }
}