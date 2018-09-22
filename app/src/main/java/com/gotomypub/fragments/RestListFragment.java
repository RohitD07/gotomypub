package com.gotomypub.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.activities.MainActivity;
import com.gotomypub.adapters.RestListRecyclerViewAdapter;
import com.gotomypub.networkutilities.Datum;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link}
 * interface.
 */
public class RestListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    List<HashMap<String,String>> restaurantList;
    List<Datum> pubDatumList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    RestListRecyclerViewAdapter restListRecyclerViewAdapter;
    TextView errorTextView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RestListFragment newInstance(int columnCount) {
        RestListFragment fragment = new RestListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_list, container, false);
        Context context = view.getContext();
        // Set the adapter
        recyclerView= (RecyclerView) view.findViewById(R.id.list);
        errorTextView=(TextView) view.findViewById(R.id.txt_news_error);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantList=((MainActivity)getActivity()).getRestaurantList();
        pubDatumList=((MainActivity)getActivity()).getPubList();
        if(pubDatumList!=null && pubDatumList.size()>0){
            errorTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            restListRecyclerViewAdapter=new RestListRecyclerViewAdapter(pubDatumList,((MainActivity)getActivity()).getCurrentLocation());
            recyclerView.setAdapter(restListRecyclerViewAdapter);
            restListRecyclerViewAdapter.setListItemClick(((MainActivity)getActivity()));
        }
        else {
            errorTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            errorTextView.setText(R.string.err_msg_no_data_location);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_map){
            ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance("",""),MainActivity.TAG_MAP,false);
            return true;
        }
        else if(id==R.id.action_search){
            ((MainActivity)getActivity()).replaceFragment(SearchFragment.newInstance("",""),MainActivity.TAG_SEARCH,false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }



}
