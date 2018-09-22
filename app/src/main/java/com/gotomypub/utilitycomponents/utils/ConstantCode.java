package com.gotomypub.utilitycomponents.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by rohitd061 on 22/01/2018.
 */

public class ConstantCode {
    public static final int MINORAGE = 18;
    public static final String KEY_CHECK_OFFLINE_LEAD ="key_offline_check" ;
    public static final String KEY_OFFLINE_MODE ="is_offline";
    public static final int MINIMUM_CLUSTER_SIZE = 4;
    public static String API_VERSION="1";
    public static String BASE_URL="http://mob-gtmp.azurewebsites.net/api/";
    public static String BASE_IMGURL="http://mob-gtmp.azurewebsites.net/content/images/items/%s/%s";
    public static String BASE_BEERIMGURL="http://mob-gtmp.azurewebsites.net/";
    public static final String DATABASE_NAME = "myoffice2-db";
    public static final int DATABASE_VERSION = 1;
    public static final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[@#$%]).{8,20})";
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
            //"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    public static final String MYOFFICE_IMAGE_DIRECTORY="myoffice_image";
    public static final String JPGEXTENSION=".jpg";
    public static final String PROFILEIMAGE="profile_";
    public static final String TEMPIMAGE="temp_";
    public static final String TYPE_IMAGE_AADHAAR_FRONT="AADHAAR_FRONT";
    public static final String TYPE_IMAGE_AADHAAR_BACK="AADHAAR_BACK";
    public static final String TYPE_IMAGE_PAN_FRONT="PAN_FRONT";
    public static final String TYPE_IMAGE_PAN_BACK="PAN_BACK";
    public static final String TYPE_IMAGE_PROFILE_PHOTO="PROFILE_PHOTO";
    public static final String TYPE_IMAGE_SIGNED_CONSENT="SIGNED_CONSENT";
    public static final String TYPE_IMAGE_REPORT="TEST_REPORT";
    public static final String TYPE_IMAGE_PERSON_PHOTO="CUSTOMER_PHOTO";
    public static final String DISPLAY_DATE_FORMAT="dd/MM/yyyy";
    public static final String LEAD_STATUS_BASIC="BASIC_DETAILS";
    public static final String LEAD_STATUS_ALTER_QUOTE="ALTER_QUOTE";
    public static final String LEAD_STATUS_PROPOSER_KYC="PROPOSER_KYC";
    public static final String LEAD_STATUS_DEPENDENT_KYC="DEPENDENT_KYC";
    public static final String LEAD_STATUS_HEALTH_UNDERWRITING="HEALTH_UNDERWRITING";
    public static final String LEAD_STATUS_NOMINEE_DETAILS="NOMINEE_DETAILS";
    public static final String LEAD_STATUS_TAX_80D="TAX_80D";
    public static final String LEAD_STATUS_EIA="EIA";
    public static final String LEAD_STATUS_FINAL_CONSENT="FINAL_CONSENT";
    public static final String USER_TYPE_PROPOSER = "proposer_";
    public static final String USER_TYPE_ADULT = "adult_";
    public static final String USER_TYPE_CHILD = "child_";
    public static final String USER_TYPE_NOMINEE = "nominee";
    public static final String USER_TYPE_GUARDIAN = "guardian";
    public static final String USER_TYPE_REGISTRATION = "registration";
    public static final String USER_TYPE_PAYMENT = "payment";
    public static final String KEY_LEAD_USERID = "lead_";

    public static final String PAYMENT_URL = "https://220.226.201.244/payment/PGRedirect";
    public static final String DIRECTORY_NAME="Myoffice";
    public static final String LIBRARY_NAME="LIBRARY";
    public static final String DASHBOARD_NAME="DASHBOARD";
    public static final String BUSINESS_NAME="BUSINESS_DASHBOARD";
    public static final String TRACKER_NAME="TRACKER";
    public static final String PROFILE_NAME="PROFILE";
    public static final String DEVELOPER_KEY = "AIzaSyCRMX5gq2j4WsfD_ioM-cnpJdF49Jx1yZE";
    // YouTube video id
    public static final String YOUTUBE_VIDEO_CODE = "_oEA18Y8gM0";

    public static final String PREMIUM_OFFLINE_KEY="final_premium";
    public static final String KEY_ISMOCDEFAULT="isMOCDefault";
    public static final String KEY_ISMOTDEFAULT="isMOTDefault";
    public static final String KEY_ISMOCLDEFAULT="isMOCLDefault";

    public static final String FILE_COMPONENT_SEPRATOR="~";
    public static final String TRACKER_PENDING = "Payment_Pending";
    public static final String TRACKER_LEADS = "Lead_Stage";
    public static final String TRACKER_ISSUED = "Policy_Issued";
    public static final String TRACKER_SUBMITTED = "Proposal_Submitted";
    public static final String TRACKER_INITIATED = "Payment_Initiated";
    public static final String TRACKER_PROPOSAL_FAILURE = "Payment_Success";

    public static final String TRACKER_PPHC = "PPHC";
    public static final String TRACKER_REJECTED = "REJECTED";
    public static final String TRACKER_ADDITIONAL_INFO = "ADDITIONAL_INFO";
    public static final String TRACKER_PAYMENT_FAILURE = "PAYMENT_FAILURE";
    public static final String PRODUCT_CODE="H01";
    public static final String GENDER_MALE="Male";
    public static final String GENDER_FEMALE="Female";
    public static final String GENDER_NEUTRAL="Neutral";

    public static  PrivateKey PRIV_KEY;
    public static  PublicKey PUB_KEY;

    public static String CUSTOMER_PORTAL="http://www.google.com";//"http://api.rhi/";

    public static final String PROPOSAL_PPHC = "PPHC";
    public static final String PROPOSAL_REJECTED = "REJECTED";
    public static final String PROPOSAL_ADDITIONAL_INFO = "ADDITIONAL_INFO";
    public static final String POLICY_CREATED = "POLICY_CREATED";
    public static final String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
}

