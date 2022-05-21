package com.four_meals_dining;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AvailableMealsFragment extends Fragment {

    private static final String TAG = "AvailableMealsFragment";
    private Available_Meals_Adapter available_meals_adapter;
    private AvailableMealsAdapter mealsAdapter;
    RecyclerView mealRV;
    private ArrayList<Meal_Model> mealModelArrayList;
    View view;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    private CollectionReference mealsReference;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_available_meals, container, false);

        progressDialog=  new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

         // initRecyclerView(view);
        listData();
        return view;
    }

    public void listData(){

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
        cal.setTime(date);

        String todaysDate = dateFormat.format(date);

        mealModelArrayList = new ArrayList<>();

        mealRV= view.findViewById(R.id.RVMealItems);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());

        mealRV.setLayoutManager(linearLayoutManager);

        db= FirebaseFirestore.getInstance();

        db.collection("meals")
                .whereEqualTo("Date", todaysDate)
                .orderBy("meal_name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                                Log.e(TAG, "Fetch may have returned null", error);
                            }
                        }else {
                            assert value != null;
                            for(DocumentChange dc: value.getDocumentChanges()){
                                if(dc.getType()== DocumentChange.Type.ADDED){
                                    mealModelArrayList.add(dc.getDocument().toObject(Meal_Model.class));
                                }
                                available_meals_adapter= new Available_Meals_Adapter(getActivity(), mealModelArrayList);
                                mealRV.setAdapter(available_meals_adapter);
                                Log.i(TAG, "Fetched");
                               // available_meals_adapter.notifyDataSetChanged();
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        }
                    }
                });
   }
}