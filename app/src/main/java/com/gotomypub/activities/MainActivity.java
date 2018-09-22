package com.gotomypub.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.gotomypub.BuildConfig;
import com.gotomypub.R;
import com.gotomypub.fragments.MapFragment;
import com.gotomypub.fragments.PubDetailsFragment;
import com.gotomypub.fragments.SearchFragment;
import com.gotomypub.interfaces.ListItemClick;
import com.gotomypub.networkutilities.Datum;
import com.gotomypub.utilitycomponents.utils.RequestCodeConstant;
import com.gotomypub.utilitycomponents.utils.UtilityMethods;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ListItemClick{
    FrameLayout main_container_layout;

    public static final String TAG_SEARCH = "serach_tag";
    public static  final String TAG_MAP = "map_tag";
    public static final String TAG_LIST = "list_tag";
    public static final String TAG_HOME = "home_tag";
    public static final String TAG_DETAILS = "details_tag";

    public boolean showMapOnLocationFetch=false;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private FusedLocationProviderClient mFusedLocationClient;

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    Location currentLocation;
    LatLng placeLatLng;
    boolean isAdvanceSearch;

    public boolean isAdvanceSearch() {
        return isAdvanceSearch;
    }

    public void setAdvanceSearch(boolean noramlSearch) {
        isAdvanceSearch = noramlSearch;
    }

    public LatLng getPlaceLatLng() {
        return placeLatLng;
    }

    public void setPlaceLatLng(LatLng placeLatLng) {
        this.placeLatLng = placeLatLng;
    }

    public List<HashMap<String, String>> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<HashMap<String, String>> restaurantList) {
        this.restaurantList = restaurantList;
    }

    List<HashMap<String,String>> restaurantList;
    List<Datum> pubList;

    public List<Datum> getPubList() {
        return pubList;
    }

    public void setPubList(List<Datum> pubList) {
        this.pubList = pubList;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.hamburgermenu);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        main_container_layout = findViewById(R.id.main_container);
        setToolbarTitle(TAG_HOME);

        //requestPermission();
        replaceFragment(SearchFragment.newInstance("",""),MainActivity.TAG_SEARCH,true);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
          Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.main_container);
          if(fragment instanceof PubDetailsFragment){
              getSupportFragmentManager().popBackStack();
          }
          else {
             // super.onBackPressed();
              showBackDialog();
          }

        }
    }

    public void showBackDialog(){
        AlertDialog.Builder  builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Are you really want to exit?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog  alertDialog=builder.create();
        alertDialog.show();
    }

    public void replaceFragment(Fragment fragment, String tag, boolean force) {
        // getSupportActionBar().setTitle(tag);
        setToolbarTitle(tag);
        if (getSupportFragmentManager().findFragmentByTag(tag) == null || force) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.main_container, fragment, tag).commit();
        }
    }

    public void addFragment(Fragment fragment, String tag, boolean force) {
        // getSupportActionBar().setTitle(tag);
        setToolbarTitle(tag);
        if (getSupportFragmentManager().findFragmentByTag(tag) == null || force) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.add(R.id.main_container, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        }
    }

    public void setToolbarTitle(String tag) {
        try {
            if (tag.equalsIgnoreCase(TAG_SEARCH)) {
                getSupportActionBar().setTitle(R.string.search);
            } else if (tag.equalsIgnoreCase(TAG_LIST)) {
                getSupportActionBar().setTitle(R.string.app_name);
            } else if (tag.equalsIgnoreCase(TAG_MAP)) {
                getSupportActionBar().setTitle(R.string.app_name);
            } else {
                getSupportActionBar().setTitle(tag);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
           // return;
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)){
                //return;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.title_permission));

                builder.setMessage(getString(R.string.msg_permission_location));


                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this, INITIAL_PERMS, RequestCodeConstant.REQUEST_LOCATION_CODE);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                AlertDialog alertDialog =builder.create();
                alertDialog.show();
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this, INITIAL_PERMS, RequestCodeConstant.REQUEST_LOCATION_CODE);
            }
        }
        else {
            setmFusedLocationClient();
        }

    }

    private void setLocationSetting(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient locationSetttingclient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = locationSetttingclient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                setmFusedLocationClient();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                RequestCodeConstant.REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }
    @SuppressLint("MissingPermission")
    private void setmFusedLocationClient(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            currentLocation=location;
                           // currentLocation.setLatitude(Double.valueOf("51.405914"));
                           // currentLocation.setLongitude(Double.valueOf("0.013677"));
                            placeLatLng=new LatLng(location.getLatitude(),location.getLongitude());
                            Log.d("Current Location",currentLocation.toString());
                            //remove updates
                            if(showMapOnLocationFetch){
                                replaceFragment(MapFragment.newInstance("",""),TAG_MAP,false);
                            }

                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            showMapOnLocationFetch=true;
            if(currentLocation==null){
                requestPermission();
            }
            else {
                replaceFragment(MapFragment.newInstance("",""),MainActivity.TAG_MAP,false);
            }

        } else if (id == R.id.nav_logout) {
            finish();

        } else if (id == R.id.nav_search) {
            replaceFragment(SearchFragment.newInstance("",""),MainActivity.TAG_SEARCH,false);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          if(requestCode==RequestCodeConstant.REQUEST_LOCATION_CODE){
              if (grantResults.length > 0
                      && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  setmFusedLocationClient();
              }
              else{ //if(!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)){
                  UtilityMethods.showPermissionSettingDialog(MainActivity.this,RequestCodeConstant.REQUEST_LOCATION_CODE);
              }
          }
    }


    @Override
    public void onItemClick(Object object, int position) {
       Datum datum= pubList.get(position);
      // addFragment(PubDetailsFragment.newInstance(String.valueOf(datum.getId()),""),TAG_DETAILS,false);
        Intent  intent= new Intent(MainActivity.this,PubDetailsActivity.class);
        intent.putExtra("pub_id",String.valueOf(datum.getId()));
        intent.putExtra("current_location",getCurrentLocation());
        startActivity(intent);
    }


}
