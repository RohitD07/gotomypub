package com.gotomypub.networkutilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gotomypub.LoginActivity;
import com.gotomypub.utilitycomponents.utils.ConstantCode;
import com.gotomypub.utilitycomponents.utils.UtilityMethods;


import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rohitd061 on 22/01/2018.
 */

public class ApiClient {
    private static Retrofit retrofit;
    private static ApiInterface apiService;
    static ProgressDialog progressDialog;

    public static Retrofit getClient(Context mContext) {

        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(3, TimeUnit.MINUTES);
            httpClient.writeTimeout(3, TimeUnit.MINUTES);
            httpClient.connectTimeout(3, TimeUnit.MINUTES);
            // add your other interceptors …

            /*
                * TODO: remove this to print http communication
                * add logging as last interceptor*/
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
           // httpClient.addInterceptor(logging);
            //httpClient.addInterceptor(new EncriptionIntersepter());
            //httpClient.addInterceptor(new DecryptInterseptor());

            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantCode.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())

                   // .client(httpClient.build())
                    .client(getUnsafeOkHttpClient())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClient2G(Context mContext) {

        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(5, TimeUnit.MINUTES);
            httpClient.writeTimeout(5, TimeUnit.MINUTES);
            httpClient.connectTimeout(5, TimeUnit.MINUTES);


            // add your other interceptors …

            /*
                * TODO: remove this to print http communication
                * add logging as last interceptor*/
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            //httpClient.addInterceptor(new EncriptionIntersepter());
            //httpClient.addInterceptor(new DecryptInterseptor());

            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantCode.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())

                    // .client(httpClient.build())
                    .client(getUnsafeOkHttpClient())
                    .build();
        }

        return retrofit;
    }



    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            //ConnectionPool connectionPool=new ConnectionPool(1,2,TimeUnit.MINUTES);


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

//           builder.addInterceptor(new EncriptionIntersepter());
//           builder.addInterceptor(new DecryptInterseptor());

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);


            builder.readTimeout(3, TimeUnit.MINUTES);
            builder.writeTimeout(3, TimeUnit.MINUTES);
            builder.connectTimeout(3, TimeUnit.MINUTES);


            // builder.connectionPool(connectionPool);
            OkHttpClient okHttpClient = builder.build();


            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static ApiInterface getApiInterFace(Context mContext) {
        if (apiService == null) {
            Log.w("network",UtilityMethods.getNetworkClass(mContext));
            if(UtilityMethods.getNetworkClass(mContext).equalsIgnoreCase("2g")) {
                apiService = getClient2G(mContext).create(ApiInterface.class);
            }
            else
            {
                apiService = getClient(mContext).create(ApiInterface.class);
            }
        }
        return apiService;
    }

    public static void callApi(final Context context, final Call<ApiResponse> apiResponseCall, final APIResponseHandlerInterface apiResponseHandlerInterface){
        apiResponseCall.clone().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response){
                ApiResponse arData = (ApiResponse) response.body();
                RequestMetaData rmdData;
                ResponseData rdData;
                ErrorRequestResponse errResponse;

                if(response.code() == 200) {
                    apiResponseHandlerInterface.sendApiResponse(response);
                }
                else if(response.code() == 502){
                    apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable("Something went wrong.. Please try again!",600));
                }
                else if(response.code()==401){
                   //showdialogLogin(context, "My Login", "Your session has expired.Please login to continue online.", "OK", "Cancel", false);
                    Log.w("api url",apiResponseCall.request().url().toString());
                    if(apiResponseCall.request().url().toString().contains("sign_out"))
                    {
                        Intent i = new Intent(context, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    }
                    else
                    {
                        Log.w("api url",apiResponseCall.request().url().toString());
                    }
                    //apiResponseHandlerInterface.hideProgressResponse();
                }
                else{ //if (response.code() == 401 || response.code() == 500 || response.code() == 400 || response.code() == 404) {

                    Gson gson = new GsonBuilder().create();
                    ApiResponse mError;

                    try {
                        String errorString=response.errorBody().string();
                        Log.w("error body",errorString);

                        mError= gson.fromJson(errorString,ApiResponse.class);

                        int code=600;//Integer.valueOf(mError.getRequestMetaData().getErrorRequestResponse().getCode());
                        String message="Something went wrong";//mError.getRequestMetaData().getErrorRequestResponse().getMessage();

                        apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable(message,code));
                    }
                    catch (IllegalStateException IOException){
                        IOException.printStackTrace();
                        apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable("Something went wrong.. Please try again!",600));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable("Something went wrong.. Please try again!",600));

                    }
                    catch (Exception e){
                        apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable("Something went wrong.. Please try again!",600));
                    }
                    /*catch (JSONException e) {
                        e.printStackTrace();
                    }*/


                }
                /*else if (response.code() == 404) {
                    apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable("Something went wrong.. Please try again!",response.code()));
                }*/
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                apiResponseHandlerInterface.sendApiFailureResponse(new APIThrowable("Something went wrong.. Please try again!",600));
                t.printStackTrace();
            }
        });
    }


}
