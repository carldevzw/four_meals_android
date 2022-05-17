package com.four_meals_dining;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private ImageView logo;
    private TextView TV_Login,TV_DHAC;
    private TextInputLayout til_username, til_password;
    private Button login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoadingDialogue loadingDialogue= new LoadingDialogue(LoginActivity.this);


        logo= findViewById(R.id.IVAppLogo);

        TV_Login= findViewById(R.id.TVLogin);
        TV_DHAC= findViewById(R.id.tv_signup);

        til_username=  findViewById(R.id.til_username);
        til_password=  findViewById(R.id.til_password);

        login= findViewById(R.id.btn_Login);

        firebaseAuth= FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null) {
//            finish();
//            return;
//        }

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
                loadingDialogue.startLoadingDialogue();
                String Username= til_username.getEditText().getText().toString().trim();
                String Password= til_password.getEditText().getText().toString().trim();
                loginUser(Username, Password);
            }
        });
    }

    private void loginUser(String username, String password) {

        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            userOrAdminLogin(username);
                            Toast.makeText(getApplicationContext(), "Welcome Admin", Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Password or Username Wrong!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void userOrAdminLogin(String username) {

        if(username.contains("fourmealsapp.com")){
            Toast.makeText(getApplicationContext(), "Welcome Admin", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
        }else{
            Toast.makeText(getApplicationContext(), "User Login Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }
}