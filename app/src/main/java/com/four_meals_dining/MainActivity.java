package com.four_meals_dining;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mealRV;
    private FirebaseFirestore db;
    private ArrayList<Meal_Model> mealModelArrayList;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView= findViewById(R.id.nav_bottom);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:{
                    Intent openCart= new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.account:{
                    Intent openCart= new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
                case R.id.cart:{
                    Intent openCart= new Intent(MainActivity.this, OrdersActivity.class);
                    startActivity(openCart);
                    overridePendingTransition(0,0);
                    break;
                }
            }
            return true;
        });

        listMeals();

  }

    public void listMeals(){

        mealModelArrayList = new ArrayList<>();

        mealRV= findViewById(R.id.RVMealItems);
        mealRV.setHasFixedSize(true);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        mealRV.setLayoutManager(layoutManager);


        db= FirebaseFirestore.getInstance();

        db.collection("meals").orderBy("meal_name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(getApplicationContext(),"Firestore error", Toast.LENGTH_LONG).show();
                        }else {
                            assert value != null;
                            for(DocumentChange dc: value.getDocumentChanges()){
                                if(dc.getType()== DocumentChange.Type.ADDED){
                                    mealModelArrayList.add(dc.getDocument().toObject(Meal_Model.class));
                                }
                                Meal_Adapter mealAdapter = new Meal_Adapter(MainActivity.this,mealModelArrayList);
                                mealRV.setAdapter(mealAdapter);
                                Toast.makeText(MainActivity.this,"Updated", Toast.LENGTH_LONG).show();
                                mealAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }
}