package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AdminHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        bottomNavigationView= findViewById(R.id.nav_bottom);
        bottomNavigationView.setSelectedItemId(R.id.add);

        replaceFragment(new MealDetailsFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.add:{
                    replaceFragment(new MealDetailsFragment());
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.available:{
                    replaceFragment(new AvailableMealsFragment());
                    overridePendingTransition(0,0);
                    break;
                }
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAdmin, fragment);
        fragmentTransaction.commit();
    }
}