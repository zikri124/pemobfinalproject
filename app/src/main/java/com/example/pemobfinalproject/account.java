package com.example.pemobfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class account extends AppCompatActivity {

    TextView user,email;
    ImageView back;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    private RecyclerView recyclerView;
    private ArrayList<ListAccountPage> listAccountPages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        user = findViewById(R.id.txtName);
        email = findViewById(R.id.txtMail);
        back = findViewById(R.id.backBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable FirebaseFirestoreException e) {
                user.setText(documentSnapshot.getString("Username"));
                email.setText(documentSnapshot.getString("email"));

            }
        });

        listAccountPages.addAll(IsiList.getList());
        showRecyclerList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(account.this, mainMenu.class));
            }
        });
    }

    public void LOGOUT (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),landingPage.class));
        Toast.makeText(account.this, "You Have Been Logged Out" ,Toast.LENGTH_LONG).show();
        finish();
    }

    private void showRecyclerList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterDataAccountPage adapterDataAccountPage = new adapterDataAccountPage(listAccountPages);
        recyclerView.setAdapter(adapterDataAccountPage);
    }
}