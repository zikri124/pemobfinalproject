package com.example.pemobfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class landingPage extends AppCompatActivity {
    Button LPRegister,LPLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        LPRegister = findViewById(R.id.registerBtn);
        LPLogin = findViewById(R.id.signInBtn);
        //halo aldi

        LPRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(landingPage.this, register.class));
            }
        });
        LPLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(landingPage.this, signIn.class));
            }
        });
    }
}