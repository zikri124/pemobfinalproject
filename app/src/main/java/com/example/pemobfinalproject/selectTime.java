package com.example.pemobfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class selectTime extends AppCompatActivity implements View.OnClickListener {
 private EditText location;
 private Button back, confirm;


 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_select_time);

  location = findViewById(R.id.edit_loc);
  back = findViewById(R.id.btn_back);
  confirm = findViewById(R.id.btn_confirm);

  back.setOnClickListener(this);
  confirm.setOnClickListener(this);
 }

 @Override
 public void onClick(View view) {
  if (view.getId() == back.getId()) {
   startActivity(new Intent(selectTime.this, selectLoc.class));
   finishAffinity();
  } else if (view.getId() == confirm.getId()) {

  }
 }
}