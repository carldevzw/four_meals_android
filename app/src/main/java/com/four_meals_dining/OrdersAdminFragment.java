package com.four_meals_dining;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class OrdersAdminFragment extends Fragment {

    TextView TVOrders;
    private ArrayList<Meal_Model> mealModelArrayList;
    FirebaseFirestore db;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_orders_admin, container, false);
        initRecyclerView(view);
        return view;

    }

    public void initRecyclerView(View view){

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
        cal.setTime(date);
        String todaysDate = dateFormat.format(date);

        RecyclerView ordersRecyclerView= view.findViewById(R.id.RVorderItems);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());

        ordersRecyclerView.setLayoutManager(linearLayoutManager);

        mealModelArrayList = new ArrayList<>();

        db= FirebaseFirestore.getInstance();
        String TAG= "Orders Admin Error";

        db.collection("meals")
                .whereEqualTo("Date", todaysDate)
                .orderBy("Count", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Log.e(TAG, "Database fetch may have returned null", error);
                        }else {
                            assert value != null;
                            for(DocumentChange dc: value.getDocumentChanges()){
                                if(dc.getType()== DocumentChange.Type.ADDED){
                                    mealModelArrayList.add(dc.getDocument().toObject(Meal_Model.class));
                                }
                                Meal_Orders_Admin_Adapter meal_orders_admin_adapter= new Meal_Orders_Admin_Adapter(getActivity(), mealModelArrayList);
                                ordersRecyclerView.setAdapter(meal_orders_admin_adapter);
                                meal_orders_admin_adapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }
}