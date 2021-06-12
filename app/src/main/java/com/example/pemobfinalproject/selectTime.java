package com.example.pemobfinalproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class selectTime extends AppCompatActivity implements View.OnClickListener {
 private EditText location;
 private Button back, confirm, selectDate;
 private Spinner selectTime;
 private String selectedSession;
 FirebaseFirestore firestore;
 FirebaseAuth firebaseAuth;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_select_time);

  location = findViewById(R.id.edit_loc);
  back = findViewById(R.id.btn_back);
  confirm = findViewById(R.id.btn_confirm);
  selectDate = findViewById(R.id.button_date);
  selectTime = findViewById(R.id.spinner);

  back.setOnClickListener(this);
  confirm.setOnClickListener(this);
  selectDate.setOnClickListener(this);

  firebaseAuth = FirebaseAuth.getInstance();
  firestore = FirebaseFirestore.getInstance();

  String locString = getIntent().getStringExtra("loc");
  location.setText(locString);
  location.setEnabled(false);

  setTimeDialog();
 }

 @Override
 public void onClick(View view) {
  if (view.getId() == back.getId()) {
   startActivity(new Intent(selectTime.this, selectLoc.class));
   finishAffinity();
  } else if (view.getId() == selectDate.getId()) {
   showDateDialog();
  } else if (view.getId() == confirm.getId()) {
   passData();
  }
 }

 private void passData() {
  String uid = firebaseAuth.getCurrentUser().getUid();
  String random = Integer.toString((int)(Math.random() * 1000));
  DocumentReference documentReference = firestore.collection("vaccineOrders").document(random + uid);

  Map<String,Object> data = new HashMap<>();
  data.put("uid", uid);
  data.put("loc", location.getText().toString());
  data.put("date", selectDate.getText().toString());
  data.put("session", selectedSession);

  documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
   @Override
   public void onSuccess(Void unused) {
    Toast.makeText(selectTime.this, "Order Success",
            Toast.LENGTH_SHORT).show();
    startActivity(new Intent(selectTime.this, mainMenu.class));
    finishAffinity();
   }
  });
 }

 private void showDateDialog() {
  Calendar calendar = Calendar.getInstance();

  DatePickerDialog datePickerDialog = new DatePickerDialog(selectTime.this, new DatePickerDialog.OnDateSetListener() {
   @Override
   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    selectDate.setText(dayOfMonth + "/" + month + "/" + year);
   }
  }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
  datePickerDialog.show();
 }

 public void setTimeDialog() {
  String session[] = {"Session 1 (09.30 - 11.30 WIB)", "Session 2 (13.00 - 14.30 WIB)", "Session 3 (14.30 - 16.00 WIB)"};
  ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, session);
  selectTime.setAdapter(adapter);
  selectTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
   @Override
   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    selectedSession = adapter.getItem(i);
   }

   @Override
   public void onNothingSelected(AdapterView<?> adapterView) {

   }
  });
 }
}