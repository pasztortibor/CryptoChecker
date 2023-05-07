package com.example.cryptochecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String PREF_KEY = RegistrationActivity.class.getPackage().toString();
    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    Spinner sexSpinner;
    EditText phoneEditText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);
        sexSpinner = findViewById(R.id.sexSpinner);
        phoneEditText = findViewById(R.id.phoneEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userNameEditText.setText(userName);
        passwordEditText.setText(password);
        passwordAgainEditText.setText(password);

        sexSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        ImageView imageView = findViewById(R.id.registerImg);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        imageView.startAnimation(animation);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.button_slide_up);
        Button loginButton = findViewById(R.id.registerButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        loginButton.startAnimation(anim);
        cancelButton.startAnimation(anim);
    }
    public void cancel(View view) {
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void register(View view) {

        String username = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();
        String sexType = sexSpinner.getSelectedItem().toString();
        String phoneNumber = phoneEditText.getText().toString();

        if(!password.equals(passwordAgain)){
            //System.out.println("Nem egyenlő a jelszó és a megerősítése.");
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //System.out.println("Sikeres regisztráció!");
                    finish();
                } else {
                    //System.out.println("Sikertelen regisztráció!");
                    finish();
                }
            }
        });
    }
}