package com.gotomypub.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.gotomypub.MyPubApplication;
import com.gotomypub.R;
import com.gotomypub.activities.MainActivity;
import com.gotomypub.customviews.PopupWindows;
import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.PubFeatureItem;
import com.gotomypub.networkutilities.PubFeatureResponse;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdvanceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvanceSearchFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView orTextView;
    TextView locationTextView;
    TextView txtBeer;
    TextView txtFeatures;
    Button searchButton;
    Button currentLocation;
    CardView autocompleteLocationCardView;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public AdvanceSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvanceSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvanceSearchFragment newInstance(String param1, String param2) {
        AdvanceSearchFragment fragment = new AdvanceSearchFragment();
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
        return inflater.inflate(R.layout.fragment_advance_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            updateUI();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orTextView=view.findViewById(R.id.txt_or);
        searchButton=view.findViewById(R.id.btn_search);
        currentLocation=view.findViewById(R.id.btn_currentlocation);
        locationTextView=view.findViewById(R.id.location_autocompletetextview);
        autocompleteLocationCardView=view.findViewById(R.id.autocompletetextview_cardview);
        txtBeer=view.findViewById(R.id.txt_beer);
        txtFeatures=view.findViewById(R.id.txt_features);

        searchButton.setOnClickListener(this);
        currentLocation.setOnClickListener(this);
        locationTextView.setOnClickListener(this);
        orTextView.setOnClickListener(this);
        autocompleteLocationCardView.setOnClickListener(this);

        txtBeer.setOnClickListener(this);
        txtFeatures.setOnClickListener(this);

        orTextView.bringToFront();
        orTextView.invalidate();


          updateUI();


    }

    public void updateUI(){
        Drawable leftDrawable=ContextCompat.getDrawable(getActivity(),R.drawable.ic_features);
        Drawable leftDrawablebeer=ContextCompat.getDrawable(getActivity(),R.drawable.ic_pintbeer);
        Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),android.R.drawable.arrow_down_float);

        txtBeer.setCompoundDrawablesWithIntrinsicBounds(leftDrawablebeer,null, rightdrawable,null);

        txtFeatures.setCompoundDrawablesWithIntrinsicBounds(leftDrawable,null, rightdrawable,null);

        ArrayList<BeerItem> beerItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
        ArrayList<PubFeatureItem> pubFeatureItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getPubFeatureItemArrayList();

        int beerSize=beerItemArrayList.size();
        for(int pos=0;pos<beerSize;pos++){
            beerItemArrayList.get(pos).setSelected(false);
           /* if(beerItemArrayList.get(pos).isSelected()){
                //Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),R.drawable.ic_filter);
                txtBeer.setCompoundDrawablesWithIntrinsicBounds(null,null, rightdrawable,null);
                break;
            }*/
        }
        beerSize=pubFeatureItemArrayList.size();
        for(int pos=0;pos<beerSize;pos++){
            pubFeatureItemArrayList.get(pos).setSelected(false);
            /*if(pubFeatureItemArrayList.get(pos).isSelected()){
                // Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),R.drawable.ic_filter);
                txtFeatures.setCompoundDrawablesWithIntrinsicBounds(null,null, rightdrawable,null);
                break;
            }*/
        }
    }



    @Override
    public void onClick(View view) {
        int id =view.getId();
        if(id==R.id.btn_search){
            /*Location selectedLocation=new Location("");
            selectedLocation.setLongitude(placeLatLng.longitude);
            selectedLocation.setLatitude(placeLatLng.latitude);

            ((MainActivity)getActivity()).setCurrentLocation(selectedLocation);
           */
            if(((MainActivity)getActivity()).getCurrentLocation()==null){
                Toast.makeText(getActivity(),"Please select location to search nearby pubs",Toast.LENGTH_LONG).show();
                return;
            }
            String distance=((SearchFragment)getParentFragment()).getDistance();
            ((MainActivity)getActivity()).setDistance(distance);
            ((MainActivity)getActivity()).setAdvanceSearch(true);
            ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance("",""),MainActivity.TAG_MAP,false);

        }
        else if(id==R.id.btn_currentlocation){
            ((MainActivity)getActivity()).showMapOnLocationFetch=false;
            ((MainActivity)getActivity()).requestPermission();        }
        else if(id==R.id.location_autocompletetextview || id==R.id.autocompletetextview_cardview){
         callAutoCompleteActivity();
        }
        else if(id==R.id.txt_features){
           /* PopupWindows popupWindow=new PopupWindows(getActivity(),1);
            popupWindow.setContentView(R.layout.layout_filter_popup);
            popupWindow.onShow(txtFeatures);*/
           showFilterListDialog(2);
        }
        else if(id==R.id.txt_beer){

           // showFilterListDialog(1);

            SearchFilterFragment searchFilterFragment=SearchFilterFragment.newInstance("","");
            searchFilterFragment.show(getChildFragmentManager(),"Search_BEER");
        }
    }


    public void showFilterListDialog(final int type){
        CharSequence[] displayCharSequences;
        boolean[] checkedArray;
        int size;
        if(type==1){
            //beer
            ArrayList<BeerItem> beerItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
            size=beerItemArrayList.size();
            displayCharSequences=new CharSequence[size];
            checkedArray=new boolean[size];
            for(int index=0;index<size;index++){
                displayCharSequences[index]=beerItemArrayList.get(index).getName();
                checkedArray[index]=beerItemArrayList.get(index).isSelected();
            }

        }
        else {
            //features
            ArrayList<PubFeatureItem> pubFeatureItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getPubFeatureItemArrayList();
            size=pubFeatureItemArrayList.size();
            displayCharSequences=new CharSequence[size];
            checkedArray=new boolean[size];
            for(int index=0;index<size;index++){
                displayCharSequences[index]=pubFeatureItemArrayList.get(index).getDisplayName();
                checkedArray[index]=pubFeatureItemArrayList.get(index).isSelected();
            }
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(displayCharSequences, checkedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            }
        });
        builder.setCancelable(true);
        builder.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListView list = ((AlertDialog) dialogInterface).getListView();
                boolean isFilterActive=false;
                if(type==1){
                    //beer
                    ArrayList<BeerItem> beerItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
                    int count=beerItemArrayList.size();

                    for(int index=0;index<count;index++){
                        beerItemArrayList.get(index).setSelected(list.isItemChecked(index));
                        if(list.isItemChecked(index)){
                            isFilterActive=true;
                        }
                    }

                }
                else {
                    //features
                    ArrayList<PubFeatureItem> pubFeatureItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getPubFeatureItemArrayList();
                    int count=pubFeatureItemArrayList.size();

                    for(int index=0;index<count;index++){
                        pubFeatureItemArrayList.get(index).setSelected(list.isItemChecked(index));
                        if(list.isItemChecked(index)){
                            isFilterActive=true;
                        }
                    }
                }

                if(type==1 && isFilterActive){
                    Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),R.drawable.ic_filter);
                txtBeer.setCompoundDrawablesWithIntrinsicBounds(null,null, rightdrawable,null);
                }
                else if(type==1 && !isFilterActive){
                    Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),android.R.drawable.arrow_down_float);
                    txtBeer.setCompoundDrawablesWithIntrinsicBounds(null,null, rightdrawable,null);

                }
                else if(type==2 && isFilterActive){
                    Drawable leftDrawable=ContextCompat.getDrawable(getActivity(),R.drawable.ic_features);
                    Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),R.drawable.ic_filter);
                    txtFeatures.setCompoundDrawablesWithIntrinsicBounds(leftDrawable,null, rightdrawable,null);

                }
                else if(type==2 && !isFilterActive){
                    Drawable leftDrawable=ContextCompat.getDrawable(getActivity(),R.drawable.ic_features);
                    Drawable rightdrawable= ContextCompat.getDrawable(getActivity(),android.R.drawable.arrow_down_float);
                    txtFeatures.setCompoundDrawablesWithIntrinsicBounds(leftDrawable,null, rightdrawable,null);

                }
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
       AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    public void setBeerFilterUI(boolean isFilterActive){
        Drawable leftDrawable=ContextCompat.getDrawable(getActivity(),R.drawable.ic_pintbeer);
        int drawableRight=isFilterActive?R.drawable.ic_filter:android.R.drawable.arrow_down_float;
        Drawable rightDrawable=ContextCompat.getDrawable(getActivity(),drawableRight);
        txtBeer.setCompoundDrawablesWithIntrinsicBounds(leftDrawable,null, rightDrawable,null);

    }


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i("AutoComplet Result", "Place: " + place.getName());
                ((MainActivity)getActivity()).setPlaceLatLng(place.getLatLng());
                Location selectedLocation=new Location("");
                selectedLocation.setLongitude(place.getLatLng().longitude);
                selectedLocation.setLatitude(place.getLatLng().latitude);

                ((MainActivity)getActivity()).setCurrentLocation(selectedLocation);

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
