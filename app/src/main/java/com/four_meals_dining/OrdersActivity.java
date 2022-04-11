package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrdersActivity extends AppCompatActivity {

    Button btnCart, btnConfirm;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        replaceFragment(new CartFragment());

        bottomNavigationView= findViewById(R.id.nav_bottom);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:{
                    Intent openCart= new Intent(OrdersActivity.this, HomeActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.account:{
                    Intent openCart= new Intent(OrdersActivity.this, AccountActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.cart:{
                    Intent openCart= new Intent(OrdersActivity.this, OrdersActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
            }
            return true;
        });

        btnCart= findViewById(R.id.btnCart);
        btnConfirm= findViewById(R.id.btnConfirm);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                replaceFragment(new CartFragment());

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new ConfirmedFragment());

            }
        });


    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}