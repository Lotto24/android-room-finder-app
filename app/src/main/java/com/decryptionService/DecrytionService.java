package com.decryptionService;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by fbaue on 12/24/14.
 */
public class DecrytionService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private String TAG = this.getClass().getName();

    public String decrypt(byte[] encryptedData, String password) throws InvalidKeyException {
        String result = "";
        try {
            Key secretKey = new SecretKeySpec(password.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] outputBytes = cipher.doFinal(encryptedData);

            result = new String(outputBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            Log.d(TAG, "technical decryption error", e);
        }
        return result;
    }

}
