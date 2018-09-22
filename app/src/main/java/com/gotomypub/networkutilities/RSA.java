package com.gotomypub.networkutilities;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.gotomypub.utilitycomponents.utils.ConstantCode.PRIV_KEY;
import static com.gotomypub.utilitycomponents.utils.ConstantCode.PUB_KEY;


/**
 * Created by shantanukuvst on 26/03/2018.
 */

public class RSA {

    KeyPairGenerator kpg;
    KeyPair kp;
    PublicKey publicKey;
    PrivateKey privateKey;
    byte[] encryptedBytes, decryptedBytes;
    Cipher cipher, cipher1;
    String encrypted, decrypted;

    public String Encrypt(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
//        kpg = KeyPairGenerator.getInstance("RSA");
//        kpg.initialize(1024);
//        kp = kpg.genKeyPair();
//        publicKey = kp.getPublic();
//        privateKey = kp.getPrivate();

       // cipher = Cipher.getInstance("RSA");
//        cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
        cipher= Cipher.getInstance("RSA", "AndroidKeyStoreBCWorkaround");


       cipher.init(Cipher.ENCRYPT_MODE, PUB_KEY);

        encryptedBytes = cipher.doFinal(plain.getBytes());

        encrypted = bytesToString(encryptedBytes);
//        encrypted = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
        return encrypted;

    }

    public String Decrypt (String result) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {

       // cipher1=Cipher.getInstance("RSA");
//        Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
        Cipher cipher1 = Cipher.getInstance("RSA", "AndroidKeyStoreBCWorkaround");
        cipher1.init(Cipher.DECRYPT_MODE, PRIV_KEY);
        decryptedBytes = cipher1.doFinal(stringToBytes(result));
//        decryptedBytes = cipher1.doFinal(result.getBytes());
        decrypted = new String(decryptedBytes);
        return decrypted;

    }

    public  String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public  byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }
}
