package com.example.pemobfinalproject;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class selectLoc extends AppCompatActivity implements PermissionsListener, View.OnClickListener, OnMapReadyCallback{

 private MapView mapView;
 private MapboxMap mapboxMap;
 private AutoCompleteTextView hospitalSelect;
 private ImageButton btnBack;
 private Button btnNext, btnReset;
 private PermissionsManager permissionsManager;

 private JSONObject dataJson;
 private List<String> arr;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

  Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

  setContentView(R.layout.activity_select_loc);

  mapView = findViewById(R.id.mapView);
  btnBack = findViewById(R.id.backBtn2);
  btnNext = findViewById(R.id.button_next_loc);
  btnReset = findViewById(R.id.button_reset);
  hospitalSelect = (AutoCompleteTextView) findViewById(R.id.hospital_select);

  mapView.onCreate(savedInstanceState);
  mapView.getMapAsync(this);

  btnBack.setOnClickListener(this);
  btnNext.setOnClickListener(this);
  btnReset.setOnClickListener(this);
  getJsonData();
 }



 @Override
 public void onMapReady(@NonNull final MapboxMap mapboxMap) {
  this.mapboxMap = mapboxMap;
  mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/astrocat/ckpo2s0sa01rx17mmnmulrgc9"), new Style.OnStyleLoaded() {
   @Override
   public void onStyleLoaded(@NonNull Style style) {
    enableLocationComponent(style);
    setMapClickListener();
   }
  });
 }

 private void searchHospital() {
  arr = new ArrayList<>();
  try {
   JSONArray dataArray = dataJson.getJSONArray("features");
   for (int i=0; i<dataArray.length(); i++) {
    JSONObject res = dataArray.getJSONObject(i).getJSONObject("properties");
    arr.add(res.getString("HOSPITAL_NAME")+", "+res.getString("ADDRESS_LINE1")+", "+res.getString("ADDRESS_LINE2")+", "+res.getString("CITY"));
   }
  } catch (JSONException e) {
   e.printStackTrace();
  }

  ArrayAdapter<String> adapter = new ArrayAdapter<String>(selectLoc.this, android.R.layout.simple_dropdown_item_1line, arr);
  hospitalSelect.setAdapter(adapter);
  hospitalSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    try {
     JSONArray dataArray = dataJson.getJSONArray("features");
     for (int i2=0; i2<dataArray.length(); i2++) {
      JSONObject res = dataArray.getJSONObject(i2).getJSONObject("properties");
      String select = hospitalSelect.getText().toString();
      if ((res.getString("HOSPITAL_NAME")+", "+res.getString("ADDRESS_LINE1")+", "+res.getString("ADDRESS_LINE2")+", "+res.getString("CITY")).equals(select)) {
       JSONArray coor = dataArray.getJSONObject(i2).getJSONObject("geometry").getJSONArray("coordinates");
       LatLng point2 = new LatLng();
       point2.setLongitude(coor.getDouble(0));
       point2.setLatitude(coor.getDouble(1));
       animateCamera(point2);
       break;
      }
     }
    } catch (JSONException e) {
     e.printStackTrace();
    }
   }
  });
 }

 private void setMapClickListener() {
  mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
   @Override
   public boolean onMapClick(@NonNull @NotNull LatLng point) {
    return handleClickIcon(point);
   }
  });
 }

 private boolean handleClickIcon(@NonNull LatLng point) {
  //double currentZoomLevel = mapboxMap.getCameraPosition().zoom;
  PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);

  try {
   JSONArray dataArray = dataJson.getJSONArray("features");
   for (int i=0; i<dataArray.length(); i++) {
    JSONArray res = dataArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
    LatLng point2 = new LatLng();
    point2.setLongitude(res.getDouble(0));
    point2.setLatitude(res.getDouble(1));
    PointF screenPoint2 = mapboxMap.getProjection().toScreenLocation(point2);
    if (screenPoint.x <= screenPoint2.x+40 && screenPoint.x >= screenPoint2.x-40) {
     if (screenPoint.y <= screenPoint2.y+40 && screenPoint.y >= screenPoint2.y-40) {
      Log.d("ClickInspect", "handleClickIcon: Clicked on "+dataArray.getJSONObject(i).getJSONObject("properties").getString("HOSPITAL_NAME"));
      hospitalSelect.setText(arr.get(i));
      animateCamera(point);
      break;
     }
    }
   }
  } catch (JSONException e) {
   e.printStackTrace();
  }

  return true;
 }

 private void animateCamera(LatLng point) {
  CameraPosition position = new CameraPosition.Builder()
          .target(point)
          .zoom(14)
          .build();
  mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position),5000);
 }

 private void getJsonData() {
  String url = "https://api.mapbox.com/datasets/v1/astrocat/ckpo0fnfn06mw28ltok0c27ne/features?access_token=pk.eyJ1IjoiYXN0cm9jYXQiLCJhIjoiY2twbGJ3c3ozMG83bTJwbmdqZmEzNXZrOSJ9.Jbr0rQWwtAiAJ7nrOlUJmQ";
  RequestQueue queue = Volley.newRequestQueue(this);

  JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
   @Override
   public void onResponse(JSONObject response) {
    dataJson = response;
    searchHospital();
   }
  }, new Response.ErrorListener() {
   @Override
   public void onErrorResponse(VolleyError error) {
    Log.d("ErrorU", "onResponse: " + error.toString());
   }
  });

  queue.add(jsonObjectRequest);
 }

 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
 }

 @Override
 public void onExplanationNeeded(List<String> permissionsToExplain) {
  Toast.makeText(this, "user_location_permission_explanation", Toast.LENGTH_LONG).show();
 }

 @Override
 public void onPermissionResult(boolean granted) {
  if (granted) {
   mapboxMap.getStyle(new Style.OnStyleLoaded() {
    @Override
    public void onStyleLoaded(@NonNull Style style) {
     enableLocationComponent(style);
    }
   });
  } else {
   Toast.makeText(this, "Location permission not granted", Toast.LENGTH_LONG).show();
   finish();
  }
 }

 @SuppressWarnings({"MissingPermission"})
 private void enableLocationComponent(@NonNull Style style) {
  if (PermissionsManager.areLocationPermissionsGranted(this)) {
   LocationComponent locationComponent = mapboxMap.getLocationComponent();

   locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, style).build());

   locationComponent.setLocationComponentEnabled(true);

   locationComponent.setCameraMode(CameraMode.TRACKING);

   locationComponent.setRenderMode(RenderMode.COMPASS);
  } else {
   permissionsManager = new PermissionsManager(this);
   permissionsManager.requestLocationPermissions(this);
  }
 }

 @Override
 public void onResume() {
  super.onResume();
  mapView.onResume();
 }

 @Override
 protected void onStart() {
  super.onStart();
  mapView.onStart();
 }

 @Override
 protected void onStop() {
  super.onStop();
  mapView.onStop();
 }

 @Override
 public void onPause() {
  super.onPause();
  mapView.onPause();
 }

 @Override
 public void onLowMemory() {
  super.onLowMemory();
  mapView.onLowMemory();
 }

 @Override
 protected void onDestroy() {
  super.onDestroy();
  mapView.onDestroy();
 }

 @Override
 protected void onSaveInstanceState(Bundle outState) {
  super.onSaveInstanceState(outState);
  mapView.onSaveInstanceState(outState);
 }

 @Override
 public void onClick(View view) {
  if (view.getId() == btnBack.getId()) {
   startActivity(new Intent(selectLoc.this, mainMenu.class));
   finishAffinity();
  } else if (view.getId() == btnNext.getId()) {
   Intent intent = new Intent(this, selectTime.class);
   intent.putExtra("loc", hospitalSelect.getText().toString());
   startActivity(intent);
  } else if (view.getId() == btnReset.getId()) {
   hospitalSelect.setText("");
  }
 }
}
