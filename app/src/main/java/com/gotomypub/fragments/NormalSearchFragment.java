package com.gotomypub.fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.gotomypub.MyPubApplication;
import com.gotomypub.R;
import com.gotomypub.activities.MainActivity;
import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.PubFeatureItem;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NormalSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NormalSearchFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button searchButton;
    LatLng placeLatLng;

    TextView locationTextView;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;




    public NormalSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NormalSearchFragment.
     */
    public static NormalSearchFragment newInstance(String param1, String param2) {
        NormalSearchFragment fragment = new NormalSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_normal_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchButton=view.findViewById(R.id.btn_search);
        locationTextView=view.findViewById(R.id.location_autocompletetextview);
        searchButton.setOnClickListener(this);
        locationTextView.setOnClickListener(this);



       /*setAutoCompleteFragment();*/



    }

/*
    private void setAutoCompleteFragment(){
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("UK")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS | AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("Search by town/picode");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                placeLatLng=place.getLatLng();

            }

            @Override
            public void onError(Status status) {

            }
        });
    }
*/

    private void callAutoCompleteActivity(){


        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("UK")
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS | AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        if(id==R.id.btn_search){

            if(placeLatLng==null){
                 Toast.makeText(getActivity(),"Please select location to search nearby pubs",Toast.LENGTH_LONG).show();
                return;
            }
            Location selectedLocation=new Location("");
            selectedLocation.setLongitude(placeLatLng.longitude);
            selectedLocation.setLatitude(placeLatLng.latitude);

            ((MainActivity)getActivity()).setCurrentLocation(selectedLocation);
            String distance=((SearchFragment)getParentFragment()).getDistance();
            ((MainActivity)getActivity()).setDistance(distance);
            ((MainActivity)getActivity()).setAdvanceSearch(false);
            ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance("",""),MainActivity.TAG_MAP,false);

        }
        else if(id==R.id.location_autocompletetextview){
           callAutoCompleteActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i("AutoComplet Result", "Place: " + place.getName());
                placeLatLng=place.getLatLng();
                locationTextView.setText(place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("AutoComplet Result", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
