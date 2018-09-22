package com.gotomypub.networkutilities;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by shantanukuvst on 26/03/2018.
 */

public class DecryptInterseptor implements Interceptor {
    RSA rsa = new RSA();

    @Override
    public Response intercept(Chain chain) throws IOException {

//        Log.e("Shantanu", "DycriptInterseptor: Called");


        Response response = chain.proceed(chain.request());
        if (response.isSuccessful()) {
            Log.e("Shantanu", "DycriptInterseptor: Called");

            Response.Builder newResponse = response.newBuilder();
            String contentType = response.header("Content-Type");
            if (TextUtils.isEmpty(contentType)) contentType = "text/plain";
//            InputStream cryptedStream = response.body().byteStream();

            String headerKey = response.header("rsaEnKey");
            String dKey;
            if(!TextUtils.isEmpty(headerKey)){
                RSA rsa = new RSA();
                try {
                    dKey = rsa.Decrypt(headerKey);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }
            }



            String cryptedString = response.body().toString();
            String decrypted = null;


            try {
                decrypted = AESEncryption.decrypt(cryptedString);
            } catch (Exception e) {
                e.printStackTrace();
            }




            if(!TextUtils.isEmpty(decrypted)) {
                newResponse.body(ResponseBody.create(MediaType.parse(contentType), decrypted));
                return newResponse.build();
            }
        }
        return response;
    }
}
