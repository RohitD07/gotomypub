package com.gotomypub.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.gotomypub.Controler;
import com.gotomypub.MyPubApplication;
import com.gotomypub.R;
import com.gotomypub.activities.MainActivity;
import com.gotomypub.directions.DirectionsJSONParser;
import com.gotomypub.interfaces.ControllerEmitter;
import com.gotomypub.mapcluster.CustomMapClusterRenderer;
import com.gotomypub.mapcluster.MapClusterItem;
import com.gotomypub.networkutilities.ApiClient;
import com.gotomypub.networkutilities.ApiResponse;
import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.Datum;
import com.gotomypub.networkutilities.PubApiRequest;
import com.gotomypub.networkutilities.PubApiResponse;
import com.gotomypub.networkutilities.PubFeatureItem;
import com.gotomypub.places.PlaceJSONParser;
import com.gotomypub.utilitycomponents.utils.UtilityMethods;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,ControllerEmitter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    Marker[] nearMarkersMarkerArrayList;
    MarkerOptions  currentLocationmarkerOptions;

    // Declare a variable for the cluster manager.
    private ClusterManager<MapClusterItem> mClusterManager;
    Controler controler;
   ProgressDialog progressDialog;


    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        setHasOptionsMenu(true);
        controler=new Controler(getActivity(),MapFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_list){
            ((MainActivity)getActivity()).replaceFragment(RestListFragment.newInstance(1),MainActivity.TAG_LIST,false);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        Location  mLocation=((MainActivity)getActivity()).getCurrentLocation();

        LatLng sydney = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        currentLocationmarkerOptions=new MarkerOptions().position(sydney).title("Your current location");
        mMap.addMarker(currentLocationmarkerOptions);
        moveCameraToCurrentLocation();

        //download
        /*PlaceTask placeTask = new PlaceTask();
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLocation.getLatitude() + "," + mLocation.getLongitude());
        sb.append("&radius=1000");
        sb.append("&types=" + "restaurant");
        sb.append("&sensor=true");
        sb.append("&key=" + getResources().getString(R.string.browserkey));
        placeTask.execute(sb.toString());
*/

        mMap.setIndoorEnabled(false);
        showProgressDialog();
        PubApiRequest pubApiRequest=new PubApiRequest();
        //pubApiRequest.setSearchString("SO40 2NP");
        //ApiClient.callApi(getActivity(),,controler);
        pubApiRequest.setLat(String.valueOf(mLocation.getLatitude()));
        pubApiRequest.setLng(String.valueOf(mLocation.getLongitude()));
        if(((MainActivity)getActivity()).isAdvanceSearch()){
            ArrayList<BeerItem> beerItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getBeerItemArrayList();
            ArrayList<PubFeatureItem> pubFeatureItemArrayList=((MyPubApplication)getActivity().getApplicationContext()).getPubFeatureItemArrayList();

            ArrayList<String> beerList=new ArrayList<String>();
            for(int pos=0;pos<beerItemArrayList.size();pos++){
                if(beerItemArrayList.get(pos).isSelected()){
                    beerList.add(beerItemArrayList.get(pos).getName());
                }
            }
            if(beerList.size()>0){
                pubApiRequest.setLstBeer(beerList);
            }

            for(int index=0;index<pubFeatureItemArrayList.size();index++){
                PubFeatureItem pubFeatureItem=pubFeatureItemArrayList.get(index);
                if(pubFeatureItem.isSelected()){
                setPubApiRequest(pubApiRequest,index,pubFeatureItem);
                }
            }

        }
        ApiClient.getApiInterFace(getActivity()).getPubListLatLng(pubApiRequest).enqueue(new Callback<PubApiResponse>() {
            @Override
            public void onResponse(Call<PubApiResponse> call, Response<PubApiResponse> response) {
                if(response.isSuccessful()){
                    netWorkResponser(response.body());
                }
                else {
                    hideProgressDialog();
                    moveCameraToCurrentLocation();
                    Toast.makeText(getContext(),getString(R.string.err_msg_no_data_location),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<PubApiResponse> call, Throwable t) {

                hideProgressDialog();
                moveCameraToCurrentLocation();
                Toast.makeText(getContext(),getString(R.string.err_msg_no_data_location),Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setPubApiRequest(PubApiRequest pubApiRequest,int position,PubFeatureItem pubFeatureItem){
      /*  switch (position){
            case 0:
                pubApiRequest.setSkytv("1");
                break;
            case 1:
                pubApiRequest.setChildren("1");
                break;
            case 2:
                pubApiRequest.setGarden("1");
                break;
            case 3:
                pubApiRequest.setFireplace("1");
                break;
            case 4:
                pubApiRequest.setMusic("1");
                break;
            case 5:
                pubApiRequest.setQuiet("1");
                break;
                case 6:
                pubApiRequest.setDarts("1");
                break;
                case 7:
                pubApiRequest.setFood("1");
                break;
            case 8:
                pubApiRequest.setFruitmachine("1");
                break;
            case 9:
                pubApiRequest.setAccommodation("1");
                break;
            case 10:
                pubApiRequest.setBrewery("1");
                break;
            case 11:
                pubApiRequest.setDogs("1");
                break;
            case 12:
                pubApiRequest.setParking("1");
                break;
            case 13:
                pubApiRequest.setFunction("1");
                break;
            case 14:
                pubApiRequest.setQuiz("1");
                break;
            case 15:
                pubApiRequest.setBtsport("1");
                break;
            case 16:
                pubApiRequest.setDj("1");
                break;
            case 17:
                pubApiRequest.setWifi("1");
                break;
            case 18:
                pubApiRequest.setCashPoint("1");
                break;




        }
*/
      String featurename=pubFeatureItem.getName();
      if(featurename.equals("skytv")){
          pubApiRequest.setSkytv("1");
      }
      else if(featurename.equals("children")){
          pubApiRequest.setChildren("1");
      }
      else if(featurename.equals("garden")){
          pubApiRequest.setGarden("1");
      }
      else if(featurename.equals("fireplace")){
          pubApiRequest.setFireplace("1");
      }
      else if(featurename.equals("music")){
          pubApiRequest.setMusic("1");
      }
      else if(featurename.equals("quiet")){
          pubApiRequest.setQuiet("1");
      }
      else if(featurename.equals("darts")){
          pubApiRequest.setDarts("1");
      }
      else if(featurename.equals("food")){
          pubApiRequest.setFood("1");
      }
      else if(featurename.equals("fruitmachine")){
          pubApiRequest.setFruitmachine("1");
      }
      else if(featurename.equals("accommodation")){
          pubApiRequest.setAccommodation("1");
      }
      else if(featurename.equals("brewery")){
          pubApiRequest.setBrewery("1");
      }
      else if(featurename.equals("dogs")){
          pubApiRequest.setDogs("1");
      }
      else if(featurename.equals("parking")){
          pubApiRequest.setParking("1");
      }
      else if(featurename.equals("function")){
          pubApiRequest.setFunction("1");
      }
      else if(featurename.equals("quiz")){
          pubApiRequest.setQuiz("1");
      }
      else if(featurename.equals("btsport")){
          pubApiRequest.setBtsport("1");
      }
      else if(featurename.equals("dj")){
          pubApiRequest.setDj("1");
      }
      else if(featurename.equals("wifi")){
          pubApiRequest.setWifi("1");
      }
      else if(featurename.equals("cash")){
          pubApiRequest.setCashPoint("1");
      }
    }

    private void moveCameraToCurrentLocation(){
        LatLngBounds.Builder latlngBuilder=new LatLngBounds.Builder();
        latlngBuilder.include(currentLocationmarkerOptions.getPosition());
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBuilder.build(), 5));

    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
             Log.i("map","data->"+data);

            br.close();

        }catch(Exception e){
            //Log.d("Exce while download url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void netWorkResponser(ApiResponse apiResponse) {
        PubApiResponse pubApiResponse= (PubApiResponse) apiResponse;
        //pubApiResponse.getData();
        addMarkerofPub(pubApiResponse.getData());
        hideProgressDialog();
    }

    @Override
    public void uiUpdater() {

    }



    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.length()>0){
                ParserTask parserTask = new ParserTask();

                // Invokes the thread for parsing the JSON data
                parserTask.execute(result);
            }

        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if(result==null){
                //Snackbar.make(spinnerNearBy,"There is some problem,please try again later",Snackbar.LENGTH_LONG).show();ß
                return;
            }
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }
    /** A class to parse the Google Places in JSON format */
    /***
     * Near to places
     */

    private class PlaceTask extends AsyncTask<String, Integer, String> {

        String data = "";

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                // Log.i(TAG,"url->"+url[0]);
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            if(result.length()>0){
                PlaceParserTask parserTask = new PlaceParserTask();

                // Start parsing the Google places in JSON format
                // Invokes the "doInBackground()" method of the class ParseTask
                parserTask.execute(result);
            }
            else {
                //Snackbar.make(spinnerNearBy, R.string.err_chk_connection,Snackbar.LENGTH_LONG).show();
                Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
            }

        }

    }

    class PlaceParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                //Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){

            // Clears all the existing markers
            //mGoogleMap.clear();
            if(list==null){
                //Snackbar.make(spinnerNearBy, R.string.err_chk_connection,Snackbar.LENGTH_LONG).show();
                Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                return;
            }
              addMarkers(list);
        }
    }


    private void addMarkers(List<HashMap<String,String>> list){
        nearMarkersMarkerArrayList=new Marker[list.size()];
      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationmarkerOptions.getPosition(), 15));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MapClusterItem>(getActivity(), mMap);
        mClusterManager.setRenderer(new CustomMapClusterRenderer<MapClusterItem>(getActivity(),mMap,mClusterManager));

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        ((MainActivity)getActivity()).setRestaurantList(list);

        for(int i=0;i<list.size();i++){

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();


            //markerOptions.icon(icon);
            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");

            LatLng latLng = new LatLng(lat, lng);

            // Setting the position for the marker
            markerOptions.position(latLng);

            // Setting the title for the marker.
            //This will be displayed on taping the marker
            markerOptions.title(name + " : " + vicinity);

            // Placing a marker on the touched position
                /*Marker marker= mMap.addMarker(markerOptions);
                nearMarkersMarkerArrayList[i]=marker;
                if(i==list.size()-1){
                    LatLngBounds.Builder latlngBuilder=new LatLngBounds.Builder();
                    latlngBuilder.include(currentLocationmarkerOptions.getPosition());
                    latlngBuilder.include(latLng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBuilder.build(), 40));

                }*/

            MapClusterItem mapClusterItem=new MapClusterItem(lat,lng,name,vicinity,i,true);
            mClusterManager.addItem(mapClusterItem);
        }
        mClusterManager.cluster();
    }

    private void addMarkerofPub(List<Datum> list){
        if(list!=null && list.size()>0 ){

            Collections.sort(list,new SortByDistanceComparator());

            nearMarkersMarkerArrayList=new Marker[list.size()];
           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationmarkerOptions.getPosition(), 15));

            // Initialize the manager with the context and the map.
            // (Activity extends context, so we can pass 'this' in the constructor.)
            mClusterManager = new ClusterManager<MapClusterItem>(getActivity(), mMap);
            mClusterManager.setRenderer(new CustomMapClusterRenderer<MapClusterItem>(getActivity(),mMap,mClusterManager));

            // Point the map's listeners at the listeners implemented by the cluster
            // manager.
            mMap.setOnCameraIdleListener(mClusterManager);
            mMap.setOnMarkerClickListener(mClusterManager);
            mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<MapClusterItem>() {
                @Override
                public void onClusterItemInfoWindowClick(MapClusterItem mapClusterItem) {
                    int index=mapClusterItem.getListIndex();
                    ((MainActivity)getActivity()).onItemClick(null,index);
                }
            });

            mMap.setOnInfoWindowClickListener(mClusterManager);



            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            int listSize=list.size();
            for(int i=0;i<listSize;i++){

                // Creating a marker



                //markerOptions.icon(icon);
                // Getting a place from the places list
                Datum hmPlace = list.get(i);

                // Getting latitude of the place
                double lat = hmPlace.getLat();

                // Getting longitude of the place
                double lng = hmPlace.getLng();

                // Getting name
                String name = hmPlace.getName();

                // Getting vicinity
                String address1 = UtilityMethods.textNullReturn(hmPlace.getAddress1());

                LatLng latLng = new LatLng(lat, lng);

                hmPlace.setDistance(UtilityMethods.getLocationDistanceInMeter(latLng.latitude,latLng.longitude,((MainActivity)getActivity()).getCurrentLocation()));

                // Setting the position for the marker

                // Setting the title for the marker.
                //This will be displayed on taping the marker

               if(i< 5)
                builder.include(latLng);





                MapClusterItem mapClusterItem=new MapClusterItem(lat,lng,name,address1,i,hmPlace.getActive());
                mClusterManager.addItem(mapClusterItem);
            }
            mClusterManager.cluster();
            builder.include(currentLocationmarkerOptions.getPosition());
            LatLngBounds bounds = builder.build();

           // mMap.setLatLngBoundsForCameraTarget(bounds);
          // CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(currentLocationmarkerOptions.getPosition(), 11);

            // Calculate ActionBar height
            TypedValue tv = new TypedValue();
            int actionBarHeight=0;
            if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            }
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            height-=actionBarHeight;
            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

           // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            CameraUpdate  cameraUpdate=CameraUpdateFactory.newLatLngZoom(currentLocationmarkerOptions.getPosition(),12);
            mMap.animateCamera(cameraUpdate);




        }
        else {
           moveCameraToCurrentLocation();
            Toast.makeText(getContext(),getString(R.string.err_msg_no_data_location),Toast.LENGTH_LONG).show();

        }




        ((MainActivity)getActivity()).setPubList(list);

    }


    /** Demonstizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".


        private final View mContents;

        CustomInfoWindowAdapter() {

            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }


        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {


            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }

    void showProgressDialog(){
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("loading nearest pub");
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

    class SortByDistanceComparator implements Comparator<Datum> {
        public int compare(Datum a, Datum b) {
            if ( a.getDistance() < b.getDistance() ) return -1;
            else if ( a.getDistance() == b.getDistance() ) return 0;
            else return 1;
        }
    }


}
