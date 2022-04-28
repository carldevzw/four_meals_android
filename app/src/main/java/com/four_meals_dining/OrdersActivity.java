package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class OrdersActivity extends AppCompatActivity {

    private Button btnCart, btnConfirm;
    private BottomNavigationView bottomNavigationView;
    OrdersViewPageAdapter ordersViewPageAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.ordersTabs);

        FragmentManager fragmentManager = getSupportFragmentManager();

        ordersViewPageAdapter = new OrdersViewPageAdapter(fragmentManager, getLifecycle());

        viewPager2.setAdapter(ordersViewPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home: {
                    Intent openCart = new Intent(OrdersActivity.this, HomeActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0, 0);
                    break;
                }
                case R.id.account: {
                    Intent openCart = new Intent(OrdersActivity.this, AccountActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0, 0);
                    break;
                }
                case R.id.cart: {
                    Intent openCart = new Intent(OrdersActivity.this, OrdersActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0, 0);
                    break;
                }
            }
            return true;
        });

    }
}