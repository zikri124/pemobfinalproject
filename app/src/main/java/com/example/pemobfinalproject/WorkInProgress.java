package com.example.pemobfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class WorkInProgress extends AppCompatActivity {
    ImageButton WIPBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_in_progress);
        WIPBack = findViewById(R.id.IBBack);

        WIPBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkInProgress.this, mainMenu.class));
                finishAffinity();
            }
        });
    }
}