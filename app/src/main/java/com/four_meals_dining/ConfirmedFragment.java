package com.four_meals_dining;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
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

public class ConfirmedFragment extends Fragment {
    private RecyclerView mealRV;
    private FirebaseFirestore db;
    private static final String TAG = "ConfirmedFragment";
    private ArrayList<Meal_Model> mealModelArrayList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_confirmed, container, false);
       // listData();
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

        RecyclerView ordersRecyclerView= view.findViewById(R.id.RVConMeals);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());

        ordersRecyclerView.setLayoutManager(linearLayoutManager);

        mealModelArrayList = new ArrayList<>();


        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        db= FirebaseFirestore.getInstance();
        String userId= firebaseUser.getUid();

        CollectionReference collectionReference= db.collection("users").document(userId).collection("orders");
                 collectionReference
                .whereEqualTo("Date", todaysDate)
                .whereEqualTo("Confirmed", true)
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
                                Meal_Con_Orders_Adapter meal_orders_adapter= new Meal_Con_Orders_Adapter(getActivity(), mealModelArrayList);
                                ordersRecyclerView.setAdapter(meal_orders_adapter);
                                meal_orders_adapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }

}