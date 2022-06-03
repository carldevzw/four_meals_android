package com.four_meals_dining;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private RecyclerView mealRV;
    FirebaseFirestore db;
    private Button btnConfirm;
    private FirebaseUser firebaseUser;
    private Meal_Orders_Adapter meal_orders_adapter;
    private ArrayList<Meal_Model> mealModelArrayList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_cart, container, false);

        initRecyclerView(view);

       /*  if(initRecyclerView(view)){
             Log.i(TAG, "Returned recycler view");
         }else {
             view= inflater.inflate(R.layout.fragment_empty_cart, container, false);
         }*/


         return view;
    }

    public boolean initRecyclerView(View view){
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

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        db= FirebaseFirestore.getInstance();
        String userId= firebaseUser.getUid();

        CollectionReference collectionReference= db.collection("users").document(userId).collection("orders");

        collectionReference.whereEqualTo("Date", todaysDate)
                .whereEqualTo("Confirmed", false)
                        .orderBy("meal_name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e(TAG, "Fetch may have returned null", error);
                        } else {
                            assert value != null;
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                    Meal_Model model = dc.getDocument().toObject(Meal_Model.class);
                                    model.setDocumentID(dc.getDocument().getId());
                                    mealModelArrayList.add(model);
                                    meal_orders_adapter = new Meal_Orders_Adapter(getActivity(), mealModelArrayList);
                                    ordersRecyclerView.setAdapter(meal_orders_adapter);
                                    meal_orders_adapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });

        btnConfirm= view.findViewById(R.id.btnConfirmOrders);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Modify confirmed meal items "Confirmed = true"
                confirmMeals();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position= viewHolder.getBindingAdapterPosition();
                meal_orders_adapter.deleteFromCart(position);
                mealModelArrayList.remove(position);
                meal_orders_adapter.notifyItemRemoved(position);
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_outline_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(ordersRecyclerView);

        if(mealModelArrayList.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public void confirmMeals(){

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        db= FirebaseFirestore.getInstance();
        String userId= firebaseUser.getUid();

        CollectionReference collectionReference= db.collection("users").document(userId).collection("orders");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    mealModelArrayList.clear();
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

}