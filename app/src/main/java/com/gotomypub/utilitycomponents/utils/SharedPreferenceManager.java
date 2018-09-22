package com.gotomypub.utilitycomponents.utils;

/**
 * Created by rohitd061 on 15/06/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class contain all the sharedpreference keys and return values
 * Handle all the shared preference operations
 */
public class SharedPreferenceManager {

    public static final String KEY_NOTIFICATION_TOKEN_ID="notification_token_id";
    public static final String KEY_DEVICE_ID="device_id";
    public static final String KEY_IP_ADDRESS="ip_address";
    public static final String KEY_SESSION_TOKEN="session_token";
    public static final String KEY_RM_NAME ="rm_name";
    public static final String KEY_RM_ID ="rm_id";
    public static final String KEY_RM_PASSWORD ="rm_password";
    //public static final String KEY_NEW_VERSION_DESCRIPTION="new_version_description";
    //public static final String KEY_APP_DOWNLOAD_LINK="device_app_download_link";
    public static final String KEY_REFRESH_TOKEN="refreshtoken";
    public static final String IS_DB_VERSION_UPGRADED="isDbVersionUpgraded";
    public static final String KEY_PASSWORD="password";
    //use enum or intdef for type
    private static final String KEY_USERNAME="user_name";
    public static String KEY_SESSION_EXPIRE="session_expire";
    public static String KEY_APP_REF_ID="app_ref_id";
    public static String KEY_ACC_ID="acc_id";
    public static String KEY_UPLOAD_KYC_INDEX="kyc_index";
    //public static String KEY_RECORD_ID="recordID";
    public static String KEY_LAST_LOGIN_ON="last_login_on";
    public static String KEY_COMMENT_ID="comment_id";
    public static String KEY_AUTH_TOKEN="auth_token";
    public static String KEY_PUSH_ID="push_id";
    public static String KEY_STATUS="status";
    public static String KEY_USER_ID="user_id";
    public static String KEY_MOBILE="mobile";
    public static String KEY_FULL_NAME="fullname";

    public static String KEY_TEMP_USER_AUTH="temp_auth_token";
    public static String KEY_TEMP_USER_ID="temp_user_id";
    public static String KEY_TEMP_USER_NAME="temp_user_name";
    public static String KEY_TEMP_OFFLINE="temp_offline";
    public static String KEY_TEMP_DIFF_USER="temp_diff_user";
    public static String KEY_TEMP_PROFILE_PHOTO="temp_profile_photo";
    public static String KEY_TEMP_STATUS="temp_status";
    public static String KEY_TEMP_PUSH_ID="temp_push_id";
    public static String KEY_TEMP_REFRESH_TOKEN="temp_refresh_token";

    public static String KEY_DIFF_FIRST_RUN="first_run";

    public static String DASHBOARD_JSON="dashboard_json";
    public static String DASHBOARD_JSON_DATE="dashboard_json_date";
    public static String GOALSHEET_INCENTIVE_JSON="goalsheet_incentive_json";
    public static String GOALSHEET_INCENTIVE_JSON_DATE="goalsheet_incentive_json_date";

    public static String KEY_CQ_SET="cq_set";

    public static String OPTED_QUOTE="";

    public static String KEY_CH_QA="";


    public static String KEY_IS_SHOWCASE_LOGIN_DONE = "isLoginShowcaseShown";
    public static String KEY_IS_SHOWCASE_DASHBOARD_DONE = "isDashboardShowcaseShown";
    public static String KEY_IS_SHOWCASE_MENU_DONE = "isMenuShowcaseShown";

   // public static String KEY_SYNCINDEX="sync_index";


    public static void clear(Context mContext){
        SharedPreferences.Editor ed= getEditor(mContext);
        ed.clear();
        ed.apply();
    }


    private static SharedPreferences getSharedPreferences(Context mContext)  {
        return   PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private static SharedPreferences.Editor getEditor(Context mContext)  {
        return getSharedPreferences(mContext).edit();
    }

    public static String getStringValue(Context mContext, String key){
     return getSharedPreferences(mContext).getString(key,"");
    }

    public static void setStringValue(Context mContext, String key, String stringVal){
        SharedPreferences.Editor ed= getEditor(mContext);
        ed.putString(key,stringVal);
        ed.apply();
    }

    public static boolean getBoolean(Context mContext, String key){
        return getSharedPreferences(mContext).getBoolean(key,false);
    }

    public static void setBoolean(Context mContext, String key, boolean booleanVal){
        SharedPreferences.Editor ed= getEditor(mContext);
        ed.putBoolean(key,booleanVal);
        ed.apply();

    }

    public static void setInteger(Context mContext, String key, int intVal){
        SharedPreferences.Editor ed= getEditor(mContext);
        ed.putInt(key,intVal);
        ed.apply();
    }

    public static int getInteger(Context mContext, String key){
        return getSharedPreferences(mContext).getInt(key,0);
    }

    public static String getDeviceID(Context mContext){

       String uid=getSharedPreferences(mContext).getString(KEY_DEVICE_ID,null);
       if(uid==null){
           uid=UUID.randomUUID().toString();
           setStringValue(mContext,KEY_DEVICE_ID,uid);

       }
       return uid;

    }



}
