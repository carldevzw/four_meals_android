package com.four_meals_dining;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    TextView signup, policy;
    TextInputLayout til_fName, til_surname, til_su_username, til_su_password, til_con_password;
    Button submit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth= FirebaseAuth.getInstance();
        signup= findViewById(R.id.TVSignup);
        policy= findViewById(R.id.tv_signup);

        til_fName= findViewById(R.id.til_fname);
        til_surname= findViewById(R.id.til_surname);
        til_su_username= findViewById(R.id.til_su_username);
        til_su_password= findViewById(R.id.til_su_password);
        til_con_password= findViewById(R.id.til_conpassword);

        submit= findViewById(R.id.btn_Signup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FirstName= til_fName.getEditText().getText().toString().trim();
                String Surname= til_surname.getEditText().getText().toString().trim();
                String Username= til_su_username.getEditText().getText().toString().trim();
                String Password= til_su_password.getEditText().getText().toString().trim();

                if(valFullName() && valSurname() && valUsername() && valPassword() && valPasswordMatch()){

                    firebaseAuth.createUserWithEmailAndPassword(Username, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                               newUserSign(FirstName, Surname, Username, Password);

                                Toast.makeText(getApplicationContext(), "Signup Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Signup Failed. Try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SignupActivity.this, "Signup Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean valFullName(){

        String firstName= til_fName.getEditText().getText().toString().trim();
        String checkSpace= "\\A\\w{1,20}\\z";

        if(firstName.isEmpty()){
            til_fName.setError("Required");
            return false;
        }else if(!firstName.matches(checkSpace)){
            til_fName.setError("Remove whitespaces");
            return false;
        }
        else{
            til_fName.setError(null);
            til_fName.setErrorEnabled(false);
            return true;
        }
    }
    public boolean valSurname(){

        String surname= til_surname.getEditText().getText().toString().trim();
        String checkSpace= "\\A\\w{1,20}\\z";

        if(surname.isEmpty()){
            til_surname.setError("Required");
            return false;
        }else if(!surname.matches(checkSpace)){
            til_surname.setError("Remove whitespaces");
            return false;
        }
        else{
            til_surname.setError(null);
            til_surname.setErrorEnabled(false);
            return true;
        }
    }
    public boolean valUsername(){

        String username= til_su_username.getEditText().getText().toString().trim();
        String checkSpace= "^(.+)@(.+)$";

        if(username.isEmpty()){
            til_su_username.setError("Required");
            return false;
        }else if(!username.matches(checkSpace)){
            til_su_username.setError("Remove whitespaces");
            return false;
        }
        else{
            til_su_username.setError(null);
            til_su_username.setErrorEnabled(false);
            return true;
        }
    }
    public boolean valPassword() {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        String password = til_su_password.getEditText().getText().toString().trim();

        if (!password.matches(PASSWORD_PATTERN)) {
            til_su_password.setError("Invalid Password");
            return false;
        } else {
            return true;

        }
    }
    public boolean valPasswordMatch(){

        String password= til_su_password.getEditText().getText().toString().trim();
        String password2= til_con_password.getEditText().getText().toString().trim();

        if(password.isEmpty()){
            til_con_password.setError("Required");
            return false;
        }else if(!password2.equals(password)){
            til_con_password.setError("Passwords do not match");
            return false;
        }
        else{
            til_con_password.setError(null);
            til_con_password.setErrorEnabled(false);
            return true;
        }
    }

    public void newUserSign(String fName, String surname, String username, String password ){

        db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("First Name", fName);
        user.put("Surname", surname);
        user.put("Username", username);
        user.put("Password", password);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),
                                "DocumentSnapshot added with ID: " + documentReference.getId(),
                                Toast.LENGTH_LONG).
                                show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error adding document" + e,
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }
}