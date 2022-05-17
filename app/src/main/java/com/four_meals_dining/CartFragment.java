package com.four_meals_dining;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private RecyclerView mealRV;
    FirebaseFirestore db;
    private Button btnConfirm;

    private ArrayList<Meal_Model> mealModelArrayList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_cart, container, false);

         btnConfirm= view.findViewById(R.id.btnConfirmOrders);

         btnConfirm.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                //Modify confirmed meal items "Confirmed = true"
                 confirmMeals();
             }
         });

         //listData();
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

        RecyclerView ordersRecyclerView= view.findViewById(R.id.RVCartMeals);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());

        ordersRecyclerView.setLayoutManager(linearLayoutManager);

        mealModelArrayList = new ArrayList<>();

        db= FirebaseFirestore.getInstance();

        db.collection("orders")
                .whereEqualTo("Date", todaysDate)
                .orderBy("meal_name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Log.e(TAG, "Fetch may have returned null", error);
                        }else {
                            assert value != null;
                            for(DocumentChange dc: value.getDocumentChanges()){
                                if(dc.getType()== DocumentChange.Type.ADDED){
                                    mealModelArrayList.add(dc.getDocument().toObject(Meal_Model.class));
                                }
                                Meal_Orders_Adapter meal_orders_adapter= new Meal_Orders_Adapter(getActivity(), mealModelArrayList);
                                ordersRecyclerView.setAdapter(meal_orders_adapter);
                                meal_orders_adapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }

    public void confirmMeals(){

        String TAG = "Cart Fragment Error";

        db= FirebaseFirestore.getInstance();

        db.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update("Confirmed", true);

                                db.collection("meals").document(document.getId()).update("Count", +1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.i(TAG, "Meals count +1");
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }

}