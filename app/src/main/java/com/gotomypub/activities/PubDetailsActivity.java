package com.gotomypub.activities;

import android.location.Location;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gotomypub.R;
import com.gotomypub.fragments.PubDetailsFragment;

public class PubDetailsActivity extends AppCompatActivity {

    String pubID;
    Location currentLocation;

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_details);

        pubID=getIntent().getStringExtra("pub_id");
        currentLocation=getIntent().getParcelableExtra("current_location");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_container, PubDetailsFragment.newInstance(pubID,""))
                .commit();
    }

    public void setToolbarTitle(String tag) {
        try {

                getSupportActionBar().setTitle(tag);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
          /*  if(isTaskRoot()){
                Intent mainIntent=new Intent(Details_Activity.this, MainActivity.class);
                startActivity(mainIntent);
            }
            this.finish();*/
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
