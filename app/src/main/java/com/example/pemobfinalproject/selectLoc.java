package com.example.pemobfinalproject;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.lang.ref.WeakReference;
import java.util.List;

public class selectLoc extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, View.OnClickListener {

 private MapView mapView;
 private MapboxMap mapboxMap;
 private EditText textHospital, hospitalLoc;
 private ImageButton btnBack;
 private Button btnNext;
 private PermissionsManager permissionsManager;
 private String geojsonSourceLayerId = "geojsonSourceLayerId";
 private LocationEngine locationEngine;
 private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
 private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

 private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

  Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

  setContentView(R.layout.activity_select_loc);

  mapView = findViewById(R.id.mapView);
  btnBack = findViewById(R.id.backBtn2);
  btnNext = findViewById(R.id.button_next_loc);
  hospitalLoc = findViewById(R.id.hospitalLoc);
  textHospital = findViewById(R.id.hospitalName);

  mapView.onCreate(savedInstanceState);
  mapView.getMapAsync(this);

  btnBack.setOnClickListener(this);
  btnNext.setOnClickListener(this);
 }

 @Override
 public void onMapReady(@NonNull final MapboxMap mapboxMap) {
  this.mapboxMap = mapboxMap;
  mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
   @Override
   public void onStyleLoaded(@NonNull Style style) {
    enableLocationComponent(style);
    setUpSource(style);
   }
  });
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

   LocationComponentActivationOptions locationComponentActivationOptions =
           LocationComponentActivationOptions.builder(this, style)
           .useDefaultLocationEngine(false)
           .build();

   locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, style).build());

   locationComponent.setLocationComponentEnabled(true);

   locationComponent.setCameraMode(CameraMode.TRACKING);

   locationComponent.setRenderMode(RenderMode.COMPASS);

   initLocationEngine();
  } else {
   permissionsManager = new PermissionsManager(this);
   permissionsManager.requestLocationPermissions(this);
  }
 }

 @SuppressWarnings("MissingPermission")
 private void initLocationEngine() {
  locationEngine = LocationEngineProvider.getBestLocationEngine(this);

  LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
          .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
          .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

  locationEngine.requestLocationUpdates(request, callback, getMainLooper());
  locationEngine.getLastLocation(callback);
 }

 private static class MainActivityLocationCallback implements LocationEngineCallback<LocationEngineResult> {

  private final WeakReference<selectLoc> activityWeakReference;

  MainActivityLocationCallback(selectLoc activity) {
   this.activityWeakReference = new WeakReference<>(activity);
  }

  /**
   * The LocationEngineCallback interface's method which fires when the device's location has changed.
   *
   * @param result the LocationEngineResult object which has the last known location within it.
   */
  @Override
  public void onSuccess(LocationEngineResult result) {
   selectLoc activity = activityWeakReference.get();

   if (activity != null) {
    Location location = result.getLastLocation();

    if (location == null) {
     return;
    }

// Create a Toast which displays the new location's coordinates
    /*Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
            String.valueOf(result.getLastLocation().getLatitude()), String.valueOf(result.getLastLocation().getLongitude())),
            Toast.LENGTH_SHORT).show();*/

// Pass the new location to the Maps SDK's LocationComponent
    if (activity.mapboxMap != null && result.getLastLocation() != null) {
     activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
    }
   }
  }

  /**
   * The LocationEngineCallback interface's method which fires when the device's location can not be captured
   *
   * @param exception the exception message
   */
  @Override
  public void onFailure(@NonNull Exception exception) {
   Log.d("LocationChangeActivity", exception.getLocalizedMessage());
   selectLoc activity = activityWeakReference.get();
   if (activity != null) {
    Toast.makeText(activity, exception.getLocalizedMessage(),
            Toast.LENGTH_SHORT).show();
   }
  }
 }

 private void setUpSource(@NonNull Style loadedMapStyle) {
  loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
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
  }
 }
}