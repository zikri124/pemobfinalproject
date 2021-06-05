package com.example.pemobfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class mainMenu extends AppCompatActivity {

    //Initialize Variable
    RecyclerView recyclerView;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    ImageView kotakCovid,kotakCheckup,kotakMedicine,kotakOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Assign Var
        recyclerView = findViewById(R.id.allService);

        //Create Integer Array
        Integer[] langLogo = {
                R.drawable.virus, R.drawable.medical_checkup, R.drawable.medicine, R.drawable.pharmacy, R.drawable.stethoscope, R.drawable.hospital
        };

        //Create String Array
        String[] langName = {
                "Covid", "CheckUp", "Medicine", "Pharmacy", "Doctor", "Hospital"
        };

        //Initialize ArrayList
        mainModels = new ArrayList<>();
        for (int i = 0; i < langLogo.length; i++) {
            MainModel model = new MainModel(langLogo[i], langName[i]);
            mainModels.add(model);
        }

        //Design Horizontal Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                mainMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Initialize MainAdapter
        mainAdapter = new MainAdapter(mainMenu.this, mainModels);

        //Set MainAdapter to RecyclerView
        recyclerView.setAdapter(mainAdapter);

        /*kotakCovid.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }
}