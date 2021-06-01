package com.example.pemobfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signIn extends AppCompatActivity {
    ImageButton SignBack;
    EditText SignEmail,SignPassword;
    TextView SignRegister;
    Button SignIns;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignBack = findViewById(R.id.backBtn);
        SignEmail = findViewById(R.id.editTextEmail);
        SignPassword =findViewById(R.id.editTextPassword);
        SignRegister = findViewById(R.id.RegisterTXT);
        SignIns = findViewById(R.id.signinBtn);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),mainMenu.class));
            finish();
        }

        SignBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signIn.this, landingPage.class));
            }
        });

        SignRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signIn.this, register.class));
            }
        });

        SignIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = SignEmail.getText().toString().trim();
                String Password = SignPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    SignEmail.setError("Username cannot be empty!");
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    SignPassword.setError("Password cannot be empty!");
                }

                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(signIn.this,"User Logged In",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),mainMenu.class));
                        }
                        else
                            Toast.makeText(signIn.this, "Error " + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
}