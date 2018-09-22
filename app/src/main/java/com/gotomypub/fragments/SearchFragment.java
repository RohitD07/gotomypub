package com.gotomypub.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gotomypub.MyPubApplication;
import com.gotomypub.R;
import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.PubFeatureItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String tabTitles[] = new String[] { "Normal Search", "Advance Search"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   // TabLayout tabLayout;
    ViewPager viewPager;
    RadioGroup radioGroup;
    RadioButton normalRadioButton;
    RadioButton  advanceRadioButton;
    AdvanceSearchFragment advanceSearchFragment;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.search_pager);
        radioGroup=view.findViewById(R.id.search_radiogroup);
        normalRadioButton=view.findViewById(R.id.radio_normal);
        advanceRadioButton=view.findViewById(R.id.radio_advance);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(new SearchFragmentPagerAdapter(getChildFragmentManager()) );
       // tabLayout.setupWithViewPager(viewPager);



       /* TextView leftTab= (TextView) getLayoutInflater().inflate(R.layout.tab_customview,null);
        leftTab.setText(tabTitles[0]);
        leftTab.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.tab_lef_background));
        tabLayout.getTabAt(0).setCustomView(leftTab);

        TextView rightTab= (TextView) getLayoutInflater().inflate(R.layout.tab_customview,null);
        rightTab.setText(tabTitles[1]);
        rightTab.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.tab_right_bg));
        tabLayout.getTabAt(1).setCustomView(rightTab);
*/

       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int id) {
               if(id==R.id.radio_advance && viewPager.getCurrentItem()!=1){
                   viewPager.setCurrentItem(1);
               }
               else if(id==R.id.radio_normal && viewPager.getCurrentItem()!=0){
                 /*  ArrayList<BeerItem> beerItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
                   ArrayList<PubFeatureItem> pubFeatureItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getPubFeatureItemArrayList();

                   int beerSize=beerItemArrayList.size();
                   for(int pos=0;pos<beerSize;pos++){
                       beerItemArrayList.get(pos).setSelected(false);

                   }
                   beerSize=pubFeatureItemArrayList.size();
                   for(int pos=0;pos<beerSize;pos++){
                       pubFeatureItemArrayList.get(pos).setSelected(false);

                   }*/
                   viewPager.setCurrentItem(0);
                  /* if(advanceSearchFragment!=null){
                       advanceSearchFragment.updateUI();
                   }*/
               }

           }
       });

       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               if(position==0){
                   normalRadioButton.setChecked(true);

               }
               else {
                   advanceRadioButton.setChecked(true);
               }

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });






        //tabLayout.getChildAt(0).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.tab_lef_background));
        //tabLayout.getChildAt(1).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.tab_right_bg));

    }

    public class SearchFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;



        public SearchFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return NormalSearchFragment.newInstance( "","");
            }
            else {
                advanceSearchFragment=AdvanceSearchFragment.newInstance("","");
                return advanceSearchFragment;
            }

        }


    }
}
