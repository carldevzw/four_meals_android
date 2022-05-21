package com.four_meals_dining;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class MealDetailsFragment extends Fragment {

    private View view;
    private TextInputLayout tilMealName, tilMealPrice;
    private Button btnUpload, btnAdd;
    private ImageView mealImage;
    private FirebaseFirestore db;
    private Uri ImageUri;
    private ActivityResultLauncher<String> selectImage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private String postImageURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_meal_details, container, false);

        mealImage= view.findViewById(R.id.IVUploadPicture);
        selectImage= registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        mealImage.setImageURI(result);
                        ImageUri= result;
                    }
                }
        );

        btnAdd= (Button) view.findViewById(R.id.btnAdd);
        btnUpload= (Button) view.findViewById(R.id.btnUploadImage);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage.launch("image/*");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMeal(ImageUri);
            }
        });

        return view;
    }

    public void postMeal(Uri ImageUri){

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Central Africa"));
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(date);
        String todaysDate = dateFormat.format(date);

        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading meal...");
        progressDialog.show();

        tilMealName= (TextInputLayout) getView().findViewById(R.id.til_mealName);
        tilMealPrice= (TextInputLayout) getView().findViewById(R.id.til_price);

        db= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("images/"+tilMealName.getEditText().getText().toString().trim());

        storageReference.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mealImage.setImageURI(null);
                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){
                                    String imageURL= task.getResult().toString();
                                    String mealName= tilMealName.getEditText().getText().toString().trim();
                                    String mealPrice= tilMealPrice.getEditText().getText().toString().trim();

                                    Map<String, Object> meal = new HashMap<>();
                                    meal.put("meal_name", mealName);
                                    meal.put("price", mealPrice);
                                    meal.put("meal_imageSrc", imageURL);
                                    meal.put("Count", 0);
                                    meal.put("Date", todaysDate);

                                    // Add a new document with a generated ID
                                    db.collection("meals").document(mealName + " " + day)
                                            .set(meal)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    if (progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                        tilMealName.getEditText().setText(null);
                                                        tilMealPrice.getEditText().setText(null);
                                                        Toast.makeText(getContext(), "Meal added", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    if(progressDialog.isShowing()){
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getContext(), "Error adding meal, SEE LOGS" + e, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }else{
                                    Toast.makeText(getContext(), "Failed to collect URL", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Picture upload failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}