package com.four_meals_dining;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private TextView seeALl;
    private ImageButton btnCart;
    private RecyclerView popularRV, suggestionRV;
    private FirebaseFirestore db;
    private ArrayList<Meal_Model> mealModelArrayList;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnCart= findViewById(R.id.btnPopCart);
        bottomNavigationView= findViewById(R.id.nav_bottom);
        seeALl= findViewById(R.id.TVSeeAll);

        listPopularMeals();
        listSuggestedMeals();
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
                    Log.i(TAG, "User is home already");
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

    public void listPopularMeals() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
        cal.setTime(date);
        String todaysDate = dateFormat.format(date);

        mealModelArrayList = new ArrayList<>();

        popularRV = findViewById(R.id.RVHomeItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        popularRV.setLayoutManager(linearLayoutManager);


        db = FirebaseFirestore.getInstance();

        db.collection("meals")
                .whereEqualTo("Date", todaysDate)
                .orderBy("Count", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.e(TAG, "Fetch may have returned null", error);
                        } else {
                            assert value != null;
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    mealModelArrayList.add(dc.getDocument().toObject(Meal_Model.class));
                                }
                                Popular_Meal_Adapter mealAdapter = new Popular_Meal_Adapter(HomeActivity.this, mealModelArrayList);
                                popularRV.setAdapter(mealAdapter);
                                Log.i(TAG, "Fetched");
                                mealAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public void listSuggestedMeals(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
        cal.setTime(date);
        String todaysDate = dateFormat.format(date);

        mealModelArrayList = new ArrayList<>();

        suggestionRV= findViewById(R.id.RVSuggestion);
        suggestionRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        suggestionRV.setLayoutManager(linearLayoutManager);


        db= FirebaseFirestore.getInstance();

        db.collection("meals")
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
                                Popular_Meal_Adapter mealAdapter = new Popular_Meal_Adapter(HomeActivity.this,mealModelArrayList);
                                suggestionRV.setAdapter(mealAdapter);
                                Log.i(TAG, "Fetched");
                                mealAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }
}