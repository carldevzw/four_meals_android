package com.four_meals_dining;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    private RecyclerView mealRV;

    private ArrayList<Meal_Model> mealModelArrayList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_cart, container, false);

         listData();
         initRecyclerView(view);
         return view;
    }

    public void initRecyclerView(View view){

        RecyclerView ordersRecyclerView= view.findViewById(R.id.RVCartMeals);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());

        ordersRecyclerView.setLayoutManager(linearLayoutManager);

        Meal_Orders_Adapter meal_orders_adapter= new Meal_Orders_Adapter(getActivity(), mealModelArrayList);
        ordersRecyclerView.setAdapter(meal_orders_adapter);

    }

    public void listData(){
        mealModelArrayList = new ArrayList<>();
        mealModelArrayList.add(new Meal_Model("Bread and Eggs", 4.5, R.drawable.egg_sandwich));
        mealModelArrayList.add(new Meal_Model("Plain Chips", 3.2, R.drawable.plain_chips));

    }
}