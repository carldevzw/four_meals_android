package com.four_meals_dining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    ImageView logo;
    TextView TV_Login,TV_DHAC;
    TextInputLayout til_username, til_password;
    TextInputEditText tiet_username,tiet_password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo= findViewById(R.id.IVAppLogo);

        TV_Login= findViewById(R.id.TVLogin);
        TV_DHAC= findViewById(R.id.tv_signup);

        til_username=  findViewById(R.id.til_username);
        til_password=  findViewById(R.id.til_password);

        tiet_username= findViewById(R.id.tiet_username);
        tiet_password = findViewById(R.id.tiet_password);

        login= findViewById(R.id.btn_Login);

        TV_DHAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSignUp= new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(openSignUp);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openHomeActivity= new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(openHomeActivity);
            }
        });
    }
}