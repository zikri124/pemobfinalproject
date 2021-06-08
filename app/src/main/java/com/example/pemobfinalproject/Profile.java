package com.example.pemobfinalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    TextView hellouser,user,email,TL;
    ImageView back;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        hellouser = findViewById(R.id.TVHelloUser);
        user = findViewById(R.id.TVUsername);
        email = findViewById(R.id.TVEmail);
        TL = findViewById(R.id.TVTL);

        back = findViewById(R.id.Backbutton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot , @Nullable  FirebaseFirestoreException e) {
                hellouser.setText(documentSnapshot.getString("Username"));
                user.setText(documentSnapshot.getString("Username"));
                email.setText(documentSnapshot.getString("email"));
                TL.setText(documentSnapshot.getString("TanggalLahir"));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Profile.this, mainMenu.class));
            }
        });


    }
}