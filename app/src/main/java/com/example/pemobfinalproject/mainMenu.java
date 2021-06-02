package com.example.pemobfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class mainMenu extends AppCompatActivity {
    ImageView kotakCovid,kotakCheckup,kotakMedicine,kotakOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        kotakCovid = findViewById(R.id.rectangleCovid);
        kotakCheckup = findViewById(R.id.rectangleCheckUp);
        kotakMedicine = findViewById(R.id.rectangleMedicine);
        kotakOther = findViewById(R.id.rectangleOther);

        kotakCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, WorkInProgress.class));
            }
        });
        kotakCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, WorkInProgress.class));
            }
        });
        kotakMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, WorkInProgress.class));
            }
        });
        kotakOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, WorkInProgress.class));
            }
        });
    }
}