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

public class signIn extends AppCompatActivity {
    ImageButton SignBack;
    EditText SignUsername,SignPassword;
    TextView SignRegister;
    Button SignIns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignBack = findViewById(R.id.backBtn);
        SignUsername = findViewById(R.id.editTextUsername);
        SignPassword =findViewById(R.id.editTextPassword);
        SignRegister = findViewById(R.id.RegisterTXT);
        SignIns = findViewById(R.id.signinBtn);

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

                if (false){
                    // The Database Connection hasn't been connected this is just a basic layout
                    Toast.makeText(getApplicationContext(),"Username atau Password Salah!",Toast.LENGTH_LONG).show();
                }
                else
                startActivity(new Intent(signIn.this, mainMenu.class));
            }
        });
    }
}