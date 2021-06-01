package com.example.pemobfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.perfmark.Tag;

public class register extends AppCompatActivity {
    ImageButton RegBack;
    EditText RegUsername,RegEmail,RegPassword,RegTL;
    TextView RegSignIn;
    FirebaseAuth fAuth;
    Button RegRegister;
    FirebaseFirestore fStore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegBack = findViewById(R.id.backBtn);

        RegUsername = findViewById(R.id.editTextEmail);
        RegEmail = findViewById(R.id.editTextEmailAddress);
        RegPassword = findViewById(R.id.editTextPassword);
        RegTL = findViewById(R.id.editTextDate);

        RegSignIn = findViewById(R.id.signInTxt);

        RegRegister = findViewById(R.id.registerBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),mainMenu.class));
            finish();
        }


        RegBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(register.this , landingPage.class));
            }
        });

        RegSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, signIn.class));
            }
        });

        RegRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = RegEmail.getText().toString().trim();
                String Password = RegPassword.getText().toString().trim();
                String Username = RegUsername.getText().toString().trim();
                String TL = RegTL.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    RegUsername.setError("Username cannot be empty!");
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    RegPassword.setError("Password cannot be empty!");
                }
                if (Password.length()<8){
                    RegPassword.setError("Password too weak!");
                }
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(register.this,"User created",Toast.LENGTH_LONG).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("email",Email);
                            user.put("password",Password);
                            user.put("Username",Username);
                            user.put("TanggalLahir",TL);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),signIn.class));
                        }
                        else
                            Toast.makeText(register.this, "Error " + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
    }
}