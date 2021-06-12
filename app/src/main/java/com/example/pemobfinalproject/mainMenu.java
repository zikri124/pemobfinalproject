package com.example.pemobfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mainMenu extends AppCompatActivity {

    //Initialize Variable
    RecyclerView recyclerView;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    ImageView accountlog;

    ImageView kotakCovid,kotakCheckup,kotakMedicine,kotakOther;

    MainAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        accountlog = findViewById(R.id.account);

        accountlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainMenu.this, Profile.class));
            }
        });

        //Assign Var
        recyclerView = findViewById(R.id.allService);

        //Create Integer Array
        Integer[] langLogo = {
                R.drawable.virus, R.drawable.medical_checkup, R.drawable.medicine, R.drawable.pharmacy, R.drawable.stethoscope, R.drawable.hospital
        };

        //Create String Array
        String[] langName = {
                "Vaccine", "CheckUp", "Medicine", "Pharmacy", "Doctor", "Hospital"
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
        setOnClickListener();
        mainAdapter = new MainAdapter(mainMenu.this, mainModels, listener);

        //Set MainAdapter to RecyclerView
        recyclerView.setAdapter(mainAdapter);
    }

    private void setOnClickListener() {
        listener = new MainAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(position == 0) {
                    startActivity(new Intent(mainMenu.this, selectLoc.class));
                } else {
                    startActivity(new Intent(mainMenu.this, WorkInProgress.class));
                }
            }
        };
    }
}