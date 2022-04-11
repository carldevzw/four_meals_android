package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AccountActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView accInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        bottomNavigationView= findViewById(R.id.nav_bottom);
        accInfor= findViewById(R.id.TVAccInfo);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.cart:{
                    Intent openCart= new Intent(AccountActivity.this, OrdersActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.account:{
                    Intent openCart= new Intent(AccountActivity.this, AccountActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.home:{
                    Intent openCart= new Intent(AccountActivity.this, HomeActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
            }
            return true;
        });


    }
}