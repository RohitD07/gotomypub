package com.gotomypub.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.gotomypub.MyPubApplication;
import com.gotomypub.R;

import com.gotomypub.activities.PubDetailsActivity;
import com.gotomypub.adapters.BannerPagerAdapter;
import com.gotomypub.adapters.PubFeatureRecyclerViewAdapter;
import com.gotomypub.adapters.RecycleerImageAdapter;
import com.gotomypub.adapters.TimingRecyclerViewAdapter;
import com.gotomypub.networkutilities.ApiClient;
import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.Datum;
import com.gotomypub.networkutilities.PubApiDetailsResponse;
import com.gotomypub.networkutilities.PubApiRequest;
import com.gotomypub.networkutilities.PubApiResponse;
import com.gotomypub.networkutilities.PubFeatureItem;
import com.gotomypub.utilitycomponents.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PubDetailsFragment} interface
 * to handle interaction events.
 * Use the {@link PubDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PubDetailsFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAMID = "pubId";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String pubId;
    private String mParam2;

    ViewPager imgViewpager;
    RecyclerView beerRecyclerView;
    RecycleerImageAdapter recycleerImageAdapter;
    RecyclerView featuresRecyclerView;
    PubFeatureRecyclerViewAdapter pubFeatureRecyclerViewAdapter;
    TextView titleTextView;
    TextView addressTextView;
    TextView distanceTextView;
    TextView timeTextView;
    TextView contentTextView;
    TextView txt_contact;
   // RatingBar pubRatingBar;
    ImageView leftArrowImageView;
    ImageView rightArrowImageView;

    RecyclerView time_recycler;

    ProgressDialog progressDialog;





    public PubDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PubDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PubDetailsFragment newInstance(String param1, String param2) {
        PubDetailsFragment fragment = new PubDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAMID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pubId = getArguments().getString(ARG_PARAMID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pub_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgViewpager=view.findViewById(R.id.img_pager);
         titleTextView=view.findViewById(R.id.title);
         distanceTextView=view.findViewById(R.id.txt_distance);
         addressTextView=view.findViewById(R.id.txt_address);
         beerRecyclerView=view.findViewById(R.id.beer_recycler);
        time_recycler=view.findViewById(R.id.time_recycler);
         timeTextView=view.findViewById(R.id.txt_time);
        // pubRatingBar=view.findViewById(R.id.rating_bar);
         contentTextView=view.findViewById(R.id.txt_content);
         leftArrowImageView=view.findViewById(R.id.img_left_arrow);
         rightArrowImageView=view.findViewById(R.id.img_righ_arrow_arrow);
         featuresRecyclerView=view.findViewById(R.id.feature_recycler);
        txt_contact=view.findViewById(R.id.txt_contact);



        beerRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        featuresRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        time_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        leftArrowImageView.setOnClickListener(this);
        rightArrowImageView.setOnClickListener(this);

        showProgressDialog();
        PubApiRequest pubApiRequest=new PubApiRequest();
        pubApiRequest.setId(pubId);
        ApiClient.getApiInterFace(getActivity()).getPubByID(pubApiRequest).enqueue(new Callback<PubApiDetailsResponse>() {
            @Override
            public void onResponse(Call<PubApiDetailsResponse> call, Response<PubApiDetailsResponse> response) {
                if(response.isSuccessful()){
                    PubApiDetailsResponse pubApiResponse=response.body();
                    Datum data=pubApiResponse.getData();
                    if(data!=null){
                        ((PubDetailsActivity)getActivity()).setToolbarTitle(data.getName());
                        titleTextView.setText(data.getName());
                        addressTextView.setText(getAddress(data));

                        float distance= UtilityMethods.getLocationDistanceInMeter(data.getLat(),data.getLng(),((PubDetailsActivity)getActivity()).getCurrentLocation());
                        distanceTextView.setText(String.format("%.02f", distance)+" Km away");
                        String telNo=UtilityMethods.textNullReturn(data.getTelephone1());
                        telNo=telNo.isEmpty()?"NA":telNo;

                        txt_contact.setText("Contact: "+telNo);

                        contentTextView.setText(UtilityMethods.textNullReturn(data.getSummary()));

                        imgViewpager.setAdapter(new BannerPagerAdapter(getActivity(),getChildFragmentManager(),pubId,imgViewpager));
                        //timeValTextView.setText(getOpenCloseTime(data));
                        time_recycler.setAdapter(new TimingRecyclerViewAdapter(getActivity(),setOpenCloseTime(data)));

                        String beerList=UtilityMethods.textNullReturn(data.getAles());

                        String[] beerItemArrayList;//=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
                         beerItemArrayList=beerList.split(",");
                        recycleerImageAdapter=new RecycleerImageAdapter(beerItemArrayList,getActivity())   ;
                        beerRecyclerView.setAdapter(recycleerImageAdapter);
                           rightArrowImageView.setVisibility(View.VISIBLE);


                       imgViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                           @Override
                           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                           }

                           @Override
                           public void onPageSelected(int position) {
                               if(position==0){
                                   leftArrowImageView.setVisibility(View.INVISIBLE);
                               }
                               else if(position==7){
                                   rightArrowImageView.setVisibility(View.INVISIBLE);
                               }
                               else {
                                   leftArrowImageView.setVisibility(View.VISIBLE);
                                   rightArrowImageView.setVisibility(View.VISIBLE);
                               }

                           }

                           @Override
                           public void onPageScrollStateChanged(int state) {

                           }
                       });

                       ArrayList<PubFeatureItem> pubFeatureItemArrayList=new ArrayList<PubFeatureItem>();

                       if(data.getSkytv()!=null && data.getSkytv().equalsIgnoreCase("true")){
                           PubFeatureItem pubFeatureItem=new PubFeatureItem();
                           pubFeatureItem.setId(getString(R.string.ic_skysport));
                           pubFeatureItem.setName(getString(R.string.sky_sport));
                           pubFeatureItemArrayList.add(pubFeatureItem);
                       }

                        if(data.getChildren()!=null && data.getChildren().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_child));
                            pubFeatureItem.setName("Children");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getAccommodation()!=null && data.getAccommodation().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_accomodation));
                            pubFeatureItem.setName("Accomodation");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getGarden()!=null && data.getGarden().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_garden));
                            pubFeatureItem.setName("Garden");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getFireplace()!=null && data.getFireplace().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_fireplace));
                            pubFeatureItem.setName("Fire Place");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getMusic()!=null && data.getMusic().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_livemusic));
                            pubFeatureItem.setName("Live Music");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getFruitmachine()!=null && data.getFruitmachine().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_fruitmachine));
                            pubFeatureItem.setName("Fruit Machine");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getFood()!=null && data.getFood().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_food));
                            pubFeatureItem.setName("Food");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }

                        if(data.getQuiet()!=null && data.getQuiet().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_quiet));
                            pubFeatureItem.setName("Quiet");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getDarts()!=null && data.getDarts().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_dart));
                            pubFeatureItem.setName("Dart");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getDogs()!=null && data.getDogs().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_dog));
                            pubFeatureItem.setName("Dogs");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getParking()!=null && data.getParking().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_parking));
                            pubFeatureItem.setName("Parking");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getFunction()!=null && data.getFunction().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_function));
                            pubFeatureItem.setName("Function");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getQuiz()!=null && data.getQuiz().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_quiz));
                            pubFeatureItem.setName("Quiz");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getBtsport()!=null && data.getBtsport().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_btsport));
                            pubFeatureItem.setName("Bt Sport");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getDj()!=null && data.getDj().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_dj));
                            pubFeatureItem.setName("DJ");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }
                        if(data.getWifi()!=null && data.getWifi().equalsIgnoreCase("true")){
                            PubFeatureItem pubFeatureItem=new PubFeatureItem();
                            pubFeatureItem.setId(getString(R.string.ic_wifi));
                            pubFeatureItem.setName("Wifi");
                            pubFeatureItemArrayList.add(pubFeatureItem);
                        }


                       pubFeatureRecyclerViewAdapter=new PubFeatureRecyclerViewAdapter(pubFeatureItemArrayList);
                       featuresRecyclerView.setAdapter(pubFeatureRecyclerViewAdapter);





                    }


                }
                else {

                    Toast.makeText(getContext(),getString(R.string.err_msg_no_data_pub),Toast.LENGTH_LONG).show();

                }
                hideProgressDialog();

            }

            @Override
            public void onFailure(Call<PubApiDetailsResponse> call, Throwable t) {

                hideProgressDialog();

                Toast.makeText(getContext(),getString(R.string.err_msg_no_data_pub),Toast.LENGTH_LONG).show();
            }
        });

    }



    String getAddress(Datum data){
        String fullAddress="";
        String address1=UtilityMethods.textNullReturn(data.getAddress1());
        String address2=UtilityMethods.textNullReturn(data.getAddress2());
        String address3=UtilityMethods.textNullReturn(data.getAddress3());
        String city=UtilityMethods.textNullReturn(data.getCity());
        String pinCode=UtilityMethods.textNullReturn(data.getPostcode());
        String country=UtilityMethods.textNullReturn(data.getCounty());
        if(!address1.isEmpty()){
            fullAddress=address1+",";
        }
        if(!address1.isEmpty()){
            fullAddress=address1+",";
        }
        if(!address2.isEmpty()){
            fullAddress+=address2+",";
        }
        if(!address3.isEmpty()){
            fullAddress+=address3+",";
        }
        if(!city.isEmpty()){
            fullAddress+=city+",";
        }
        if(!pinCode.isEmpty()){
            fullAddress+=pinCode+",";
        }
        if(!country.isEmpty()){
            fullAddress+=country+".";
        }


        if(fullAddress.isEmpty()){
            fullAddress="NA";
        }
        return fullAddress;
    }

    ArrayList<String> setOpenCloseTime(Datum datum){
        String openCloseTime="";
        Calendar todayCalendar=Calendar.getInstance();
        int dayOfWeek=todayCalendar.get(Calendar.DAY_OF_WEEK);
ArrayList<String> openClosetimeStringArrayList=new ArrayList<String>(7);


        openClosetimeStringArrayList.add(getDayOpenCloseTime(2,dayOfWeek,datum));
        openClosetimeStringArrayList.add(getDayOpenCloseTime(3,dayOfWeek,datum));
        openClosetimeStringArrayList.add(getDayOpenCloseTime(4,dayOfWeek,datum));
        openClosetimeStringArrayList.add(getDayOpenCloseTime(5,dayOfWeek,datum));
        openClosetimeStringArrayList.add(getDayOpenCloseTime(6,dayOfWeek,datum));
        openClosetimeStringArrayList.add(getDayOpenCloseTime(7,dayOfWeek,datum));
        openClosetimeStringArrayList.add(getDayOpenCloseTime(1,dayOfWeek,datum));



        return openClosetimeStringArrayList;




    }
    String getDayOpenCloseTime(int dayCompare,int currentdayofWeek,Datum datum){
        String currentDay=getDayWeekTime(dayCompare,datum);
        if(currentdayofWeek==dayCompare){
            return setSpannable(currentDay);
        }
        else {
            return currentDay;
        }

    }

    String setSpannable(String inputString){
        SpannableString wordtoSpan = new SpannableString(inputString);

        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),R.color.colorPrimary)), 0, inputString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return wordtoSpan.toString();

    }

    String getDayWeekTime(int day,Datum datum){
        switch (day){
            case 2:
                return "Mon \t:" +convertTotime(datum.getOpenTime_0(),datum.getClosedTime_0());
            case 3:
                return "Tue \t:" +convertTotime(datum.getOpenTime_1(),datum.getClosedTime_1());
            case 4:
                return "Wed \t:" +convertTotime(datum.getOpenTime_2(),datum.getClosedTime_2());
            case 5:
                return "Thu \t:" +convertTotime(datum.getOpenTime_3(),datum.getClosedTime_3());
            case 6:
                return "Fri \t:" +convertTotime(datum.getOpenTime_4(),datum.getClosedTime_4());
            case 7:
                return "Sat \t:" +convertTotime(datum.getOpenTime_5(),datum.getClosedTime_5());
            case 1:
                return "Sun \t:" +convertTotime(datum.getOpenTime_6(),datum.getClosedTime_6());


        }
        return "";
    }

    String convertTotime(String openTime,String closeTime){
        if(UtilityMethods.textNullReturn(openTime).isEmpty()) return "";
        return UtilityMethods.textNullReturn(openTime) + "-" +UtilityMethods.textNullReturn(closeTime);
    }

    void showProgressDialog(){
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("loading pub details");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void hideProgressDialog(){
        if(progressDialog!=null){
            try {
                progressDialog.dismiss();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.img_left_arrow){
            imgViewpager.setCurrentItem(imgViewpager.getCurrentItem()-1);
        }
        else if(id==R.id.img_righ_arrow_arrow){
            imgViewpager.setCurrentItem(imgViewpager.getCurrentItem()+1);
        }
    }
}
