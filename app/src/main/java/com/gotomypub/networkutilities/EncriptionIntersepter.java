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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by shantanukuvst on 26/03/2018.
 */

public class EncriptionIntersepter implements Interceptor {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private boolean encrypt;


    @Override
    public Response intercept(Chain chain) throws IOException {

        Log.i("Shantanu", "EncryptionInterceptor: CALLED");

        Request request = chain.request();
        String urtStr = request.url().toString();
        if (GET.equals(request.method())) {
            /** do encrypt action for url **/
            request = request.newBuilder().url(urtStr).build();
        } else if (POST.equals(request.method())) {
            MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
            RequestBody oldBody = request.body();
            Buffer buffer = new Buffer();
            oldBody.writeTo(buffer);
            String strNewBody = encrypt(buffer.readUtf8());  /// AES Body Encryption
            /** do encrypt action for strNewBody **/
            if (!TextUtils.isEmpty(strNewBody)) {

                String key = AESEncryption.plainText;
                String rsaEnKey = key;
                RSA rsa = new RSA();
                try {
                    rsaEnKey = rsa.Encrypt(key); /// RSA Key Encryption
                    Log.i("Shantanu", "plainText Encrypted rsaEnKey: " + rsaEnKey);
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

                RequestBody enBody = RequestBody.create(MediaType.parse("text/plain"), strNewBody);

                Request newRequest = request.newBuilder()
//                        .header("Content-Type", request.body().contentType().toString())
//                        .header("Content-Type", "application/json")
                        .header("rsaEnKey", rsaEnKey)
//                        .method(request.method(), request.body())
                        .method(request.method(), enBody)
                        .build();

                return chain.proceed(newRequest);
            }
        }
        return chain.proceed(request);
//        return null;
    }

    private static String encrypt(String body) {
        String encryptBody = null;
        try {
            encryptBody = AESEncryption.encrypt(body);
            Log.i("Shantanu", "encryptBody AES: "+encryptBody);
        } catch (Exception e) {
            Log.e("Shantanu", "Body encrypt: Error");
            e.printStackTrace();
        }
        return encryptBody;

    }
}
