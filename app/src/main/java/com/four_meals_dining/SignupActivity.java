package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {

    TextView signup, policy;
    TextInputLayout til_fName, til_surname, til_su_username, til_su_password, til_con_password;
    TextInputEditText tiet_fName, tiet_surname, tiet_su_username, tiet_su_password, tiet_con_password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup= findViewById(R.id.TVSignup);
        policy= findViewById(R.id.tv_signup);

        til_fName= findViewById(R.id.til_fname);
        til_surname= findViewById(R.id.til_surname);
        til_su_username= findViewById(R.id.til_su_username);
        til_su_password= findViewById(R.id.til_su_password);
        til_con_password= findViewById(R.id.til_conpassword);

        tiet_fName= findViewById(R.id.tiet_fname);
        tiet_surname= findViewById(R.id.tiet_surname);
        tiet_su_username= findViewById(R.id.tiet_su_username);
        tiet_su_password= findViewById(R.id.tiet_su_password);
        tiet_con_password= findViewById(R.id.tiet_conpassword);

        submit= findViewById(R.id.btn_Signup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMenu = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(openMenu);
            }
        });

    }
}