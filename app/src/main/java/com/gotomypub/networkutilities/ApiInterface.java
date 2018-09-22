package com.gotomypub.networkutilities;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rohitd061 on 22/01/2018.
 */

public interface ApiInterface {

    String USER_PATH_PARAMETER="users";
    @POST("Search/GetPubDetailsByID")
    Call<PubApiDetailsResponse> getPubByID(@Body PubApiRequest obj);
    @POST("Search/SearchPub")
    Call<PubApiResponse> getPubSearchList(@Body PubApiRequest obj);

    @POST("Search/GetPubListByLatLng")
    Call<PubApiResponse> getPubListLatLng(@Body PubApiRequest obj);

    @GET("Search/GetBeerList")
    Call<BeerItemResponse> getBeerList();

    @GET("Search/GetPubFeatures")
    Call<PubFeatureResponse> getPubFetaures();


}
