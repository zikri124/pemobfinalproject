package com.example.pemobfinalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class account extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ListAccountPage> listAccountPages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);

        listAccountPages.addAll(IsiList.getList());
        showRecyclerList();
    }

    private void showRecyclerList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterDataAccountPage adapterDataAccountPage = new adapterDataAccountPage(listAccountPages);
        recyclerView.setAdapter(adapterDataAccountPage);
    }
}