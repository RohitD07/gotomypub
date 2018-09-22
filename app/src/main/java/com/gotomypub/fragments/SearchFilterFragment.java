package com.gotomypub.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gotomypub.MyPubApplication;
import com.gotomypub.R;
import com.gotomypub.adapters.CustomSpinnerAdapter;
import com.gotomypub.networkutilities.BeerItem;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFilterFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText searchEditText;
    ListView beerListView;
    Button filterButton,cancelButton;
    CustomSpinnerAdapter customSpinnerAdapter;
    ArrayList<BeerItem> dummyBeerItemArrayList;




    public SearchFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFilterFragment newInstance(String param1, String param2) {
        SearchFilterFragment fragment = new SearchFilterFragment();
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
        return inflater.inflate(R.layout.fragment_search_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchEditText=view.findViewById(R.id.searh_edittext);
        beerListView=view.findViewById(R.id.beer_recycleviews);
        filterButton=view.findViewById(R.id.btn_filter);
        cancelButton=view.findViewById(R.id.btn_cancel);
        customSpinnerAdapter=new CustomSpinnerAdapter(getContext(),R.layout.spinner_item,getDummyBeerListItem());
        beerListView.setAdapter(customSpinnerAdapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchEditText.getText().toString().toLowerCase(Locale.getDefault());
                customSpinnerAdapter.getFilter().filter(text);
            }
        });

        filterButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


    }

    ArrayList<BeerItem> getDummyBeerListItem(){
        dummyBeerItemArrayList=new ArrayList<BeerItem>();
        ArrayList<BeerItem> beerItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
        for(BeerItem beerItem:beerItemArrayList){
            dummyBeerItemArrayList.add(beerItem.getNewCopy(beerItem));
        }
        return dummyBeerItemArrayList;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
      int id=view.getId();
      if(id==R.id.btn_filter){
          boolean isFilterd=false;
          ((MyPubApplication)getActivity().getApplicationContext()).setBeerItemArrayList(dummyBeerItemArrayList);

          for(BeerItem beerItem:dummyBeerItemArrayList){
              if(beerItem.isSelected()){
                  isFilterd=true;
                  break;
              }
          }

          ((AdvanceSearchFragment)getParentFragment()).setBeerFilterUI(isFilterd);
      }
      dismiss();
    }


















}
