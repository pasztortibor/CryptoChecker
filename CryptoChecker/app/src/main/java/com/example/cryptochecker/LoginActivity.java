package com.example.cryptochecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_KEY =MainActivity.class.getPackage().toString();
    EditText userEmailET;
    EditText passwordET;
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailET = findViewById(R.id.userEmailEditText);
        passwordET = findViewById(R.id.passwordEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        ImageView imageView = findViewById(R.id.loginImg);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        imageView.startAnimation(animation);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.button_slide_up);
        Button loginButton = findViewById(R.id.loginButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        loginButton.startAnimation(anim);
        cancelButton.startAnimation(anim);

    }
    public void cancel(View view) {
        finish();
    }

    public void login(View view) {
        String useremail = userEmailET.getText().toString();
        String password = passwordET.getText().toString();

        mAuth.signInWithEmailAndPassword(useremail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //System.out.println("Bejelentkeztél: " + useremail + password);
                    startCrpytoList();

                } else {
                    //System.out.println("Nem sikerült a bejelentkezés");
                    finish();
                }
            }
        });
    }
    public void startCrpytoList(){
        Intent intent = new Intent(this, CryptoListActivity.class);
        startActivity(intent);
    }
}