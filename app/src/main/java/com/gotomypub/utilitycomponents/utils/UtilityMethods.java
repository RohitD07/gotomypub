package com.gotomypub.utilitycomponents.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.gotomypub.R;
import com.gotomypub.networkutilities.APIResponseHandlerInterface;
import com.gotomypub.networkutilities.ApiRequest;
import com.gotomypub.networkutilities.RequestData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by rohitd061 on 15/06/2017.
 *
 * This class used for all common methods used across application
 */

public class UtilityMethods {




    public static void hideSoftKeyboard (Activity activity, View view)
    {
        try {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public static boolean isActivityForIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void goToInternetSettings(Context mContext){
        mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }






    public static long getParseDateLongTime(String pattern,String dateString){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            Date date= simpleDateFormat.parse(dateString);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);

            return calendar.getTimeInMillis();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getParseDateString(String pattern,Calendar calendar){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            String date= simpleDateFormat.format(calendar.getTime());
            return date;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**s
     *
     * @param
     * @return is minor not if age is <19 minor
     */
    public static boolean isMinor(int year, int month, int dayOfMonth){
                return getAgeOFPerson(year,month,dayOfMonth) < ConstantCode.MINORAGE;



    }

    public static int getAgeOFPerson(int year, int month, int dayOfMonth){
        Calendar  todayCalendar=Calendar.getInstance();
        Calendar birthCalendar=Calendar.getInstance();
        birthCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        birthCalendar.set(Calendar.MONTH,month);
        birthCalendar.set(Calendar.YEAR,year);
        int ageYears=todayCalendar.get(Calendar.YEAR)-birthCalendar.get(Calendar.YEAR);
        Log.w("today year",todayCalendar.get(Calendar.DAY_OF_YEAR)+"");
        Log.w("birthday year",birthCalendar.get(Calendar.DAY_OF_YEAR)+"");
        if (todayCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)){
            ageYears--;
        }
        return ageYears;

    }

    public static int getAge(int year, int month, int day) {
        final Calendar birthDay = Calendar.getInstance();
        birthDay.set(year, month, day);
        final Calendar current = Calendar.getInstance();
        /*if (current.getTimeInMillis() < birthDay.getTimeInMillis())
            throw new IllegalArgumentException("age < 0");*/
        int age = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (birthDay.get(Calendar.MONTH) > current.get(Calendar.MONTH) ||
                (birthDay.get(Calendar.MONTH) == current.get(Calendar.MONTH) &&
                        birthDay.get(Calendar.DATE) > current.get(Calendar.DATE)))
            age--;
        return age;
    }

    public static String getCurrencyFormat(Context context,int str){
        Double amountdbl;
        try {
            amountdbl= amountdbl=Double.valueOf(str);
        }
        catch (Exception e){
            e.printStackTrace();
            amountdbl=0d;
        }

        return getCurrencyFormat(context,amountdbl);
    }

    public static String getCurrencyFormat(Context context,String str){
        Double amountdbl;
        try {
            amountdbl= amountdbl=Double.valueOf(str);
        }
        catch (Exception e){
            e.printStackTrace();
            amountdbl=0d;
        }

        return getCurrencyFormat(context,amountdbl);
    }

    public static String getCurrencyFormat(Context context,Double str){
        DecimalFormat formatter = new DecimalFormat("##,##,###");

       /* String digits=str.toString().replace(",","");
        digits= digits.trim();
      */  String currencyFormatString="0";

        try {
            Double valDouble=Double.valueOf(str);
            currencyFormatString = formatter.format(valDouble);

        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){

        }
        return currencyFormatString;
        //return currencyFormatString;
    }

    public static boolean validateAadharNumber(String aadharNumber){
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if(isValidAadhar){
            isValidAadhar = Verhoeffalgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;


    }

    public static String textNullReturn(String isNull){
        return isNull==null?"":isNull;
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static void hide(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
    }

    public static String getPathParamLeadId(String leadID){
        String leadIDStr=leadID;
        try {
            Integer.valueOf(leadID);
        }
        catch (NullPointerException  Exception){
            leadIDStr="0";
        }
        catch (Exception e){
        leadIDStr="0";
        }
        return leadIDStr;
    }

    public static Double getCurrencyConverted(Double amount){
        Double convertedAmount = 0.0;

        //Convert in thousand
        if(amount >= 1000 && amount <100000){
            convertedAmount = amount /1000;
        }
        else if (amount >= 100000 && amount < 10000000){ //Convert in lacs
            convertedAmount = amount / 100000;
        }else if (amount >= 10000000 && amount < 100000000){ // Convert in Crore
            convertedAmount = amount / 10000000;
        }
        return convertedAmount;
    }

    public static String getCurrencyUnit(double amount){
        String currencyUnit = "";
        if(amount >= 1000 && amount <100000){
            currencyUnit = "Thousands";
        }
        else if (amount >= 100000 && amount < 10000000){ //Convert in lacs
            currencyUnit = "Lacs";
        }else if (amount >= 10000000 && amount < 100000000){ // Convert in Crore
            currencyUnit = "Crores";
        }

        return currencyUnit;
    }

    public static int getToolBarHeight(Context context) {
        int[] attrs = new int[] {R.attr.actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }




    public static boolean isPanValid(String panNumber){
        /*Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(panNumber);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }*/

        Pattern mPattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

        Matcher mMatcher = mPattern.matcher(panNumber);
        return mMatcher.matches();

    }


    public static String printNumber(int i)
    {
        String strNumber = "";
        switch(i)
        {
            case 1:
                strNumber = "First ";
                break;
            case 2:
                strNumber = "Second ";
                break;
            case 3:
                strNumber = "Third ";
                break;
            case 4:
                strNumber = "Fourth ";
                break;
            case 5:
                strNumber = "Fifth ";
                break;
            case 6:
                strNumber = "Sixth ";
                break;
            case 7:
                strNumber = "Seventh ";
                break;
            case 8:
                strNumber = "Eight ";
                break;
            case 9:
                strNumber = "Ninth ";
                break;
            case 10:
                strNumber = "Tenth ";
                break;
            default:
                strNumber = "";
                break;
        }
        return strNumber;
    }


    //encrypt/decrypt with salt to save password/C-QA locally
    public static String encryptWithSalt(String str) {
        Random random=new Random((new Date().getTime()));
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.encodeToString(salt,Base64.DEFAULT) + Base64.encodeToString(str.getBytes(),Base64.DEFAULT);
    }

    public static String decryptWithSalt(String encstr) {
        if (encstr.length() > 12) {
            String cipher = encstr.substring(12);
            return new String(Base64.decode(cipher,Base64.DEFAULT));
        }
        return null;
    }

    public static boolean BoolNullReturn(Boolean isNull){
        return isNull==null?false:isNull;
    }



    public static String getRelationshipGender(String relationship){
     String gender="Neutral";
     switch (relationship){
         case "Brother":
         case "Father":
         case "Grand Father":
         case "Brother-in-law":
         case "Father-in-Law":
         case "Grand Son":
         case "Nephew":
         case "Son":
         case "SON":
         case "Son-in-Law":
             gender=ConstantCode.GENDER_MALE;
             break;
         case "Sister":
         case  "Mother":
         case  "Grand Mother":
         case "Daughter":
         case "Daughter-in-Law":
         case "Grand Daughter":
         case "Mother-in-Law":
         case "Niece":
         case "Sister-in-law":
             gender=ConstantCode.GENDER_FEMALE;
             break;
         case "Legal Guardian":
         case "Spouse":
             gender=ConstantCode.GENDER_NEUTRAL;

             break;
     }
     return gender;
    }

    public static int getRelationshipForMinor(String relationship){
        int minor=0;
        switch (relationship){
            case "Brother":
            case "Brother-in-law":
            case "Grand Son":
            case "Nephew":
            case "Son":
            case "Son-in-Law":
            case "Sister":
            case "Daughter":
            case "Niece":
            case "Sister-in-law":
            case "Grand Daughter":
                minor=1;
                break;


        }
        return minor;
    }

    public static void goToPortal(Activity activity,String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        try {
            activity.startActivity(browserIntent);
        }catch (ActivityNotFoundException ae){
            //showdialog(activity.getBaseContext(),get);
            ae.printStackTrace();
        }


    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static String getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static void showPermissionSettingDialog(final Context context,int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.title_permission));
        if(type==RequestCodeConstant.REQUEST_CONTACT_CODE){
            builder.setMessage(context.getString(R.string.msg_permission_call));
        }
        else {
            builder.setMessage(context.getString(R.string.msg_permission_location));

        }
        builder.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        });
        /*builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        builder.setCancelable(false);
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }






    public static float getLocationDistanceInMeter(Double latitude, Double longitude,Location currentLocation) {
        float distance = 0.0f;
        Location location = new Location("");
        location.setLatitude(Double.valueOf(latitude));
        location.setLongitude(Double.valueOf(longitude));
        //mListener.getCurrentLocation().
                /*Location.distanceBetween(currentLocation.getLatitude(),currentLocation.getLongitude(),Double.valueOf(latitude),
                        Double.valueOf(longitude));*/

                /*currentLocation.setLatitude(Double.valueOf("51.405914"));
                currentLocation.setLongitude(Double.valueOf("0.013677"));*/
        try {
            float distanceInmtr = currentLocation.distanceTo(location);
            distance =  distanceInmtr/1000.0f;

        } catch (Exception exception) {
            exception.printStackTrace();
            distance = 0.0f;
        }

        return distance;
    }




}
