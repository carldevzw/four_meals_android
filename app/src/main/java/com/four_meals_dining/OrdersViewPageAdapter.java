package com.four_meals_dining;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrdersViewPageAdapter extends FragmentStateAdapter {

    public OrdersViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position==1){
           return new ConfirmedFragment();
       }else {
           return new CartFragment();
       }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
