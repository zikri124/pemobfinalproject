package com.example.pemobfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {
    ImageButton RegBack;
    EditText RegUsername,RegEmail,RegPassword,RegTL;
    TextView RegSignIn;
    Button RegRegister;
    Boolean TestNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegBack = findViewById(R.id.backBtn);

        RegUsername = findViewById(R.id.editTextUsername);
        RegEmail = findViewById(R.id.editTextEmailAddress);
        RegPassword = findViewById(R.id.editTextPassword);
        RegTL = findViewById(R.id.editTextDate);

        RegSignIn = findViewById(R.id.signInTxt);

        RegRegister = findViewById(R.id.registerBtn);


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

                if (false){
                // The Database Connection hasn't been connected this is just a basic layout
                    Toast.makeText(getApplicationContext(),"Mohon Isi Semua Data!",Toast.LENGTH_LONG).show();
                }
                else
                    startActivity(new Intent(register.this, mainMenu.class));
            }
        });
    }
}