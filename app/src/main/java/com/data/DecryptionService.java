package com.data;

import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by fbaue on 12/30/14.
 */
public class DecryptionService {

    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final int MAX_SALT_LENGTH = MAX_PASSWORD_LENGTH - 1;
    public static final String ENCRYPTION_ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES";

    private DecryptionService() {

    }

    public static byte[] decryptData(final byte[] data, final String password) {
        try {
            return new DecryptionService().decrypt(data, password);
        } catch (Exception e) {
            throw new RuntimeException("Cannot decrypt data", e);
        }
    }

    public static byte[] decryptData(final List<Byte> data, final String password) {
        return decryptData(convertListToArray(data),password);
    }

    private static byte[] convertListToArray(List<Byte> data) {
        byte[] result = new byte[data.size()];
        int i = -1;
        for(Byte b : data){
            result[++i] = b;
        }
        return result;
    }

    public byte[] decrypt(final byte[] data, final String password) throws Exception {
        final byte[] passwordBytes = password.getBytes();

        final byte[] normalizedSalt = extractSaltFromData(data);
        final byte[] content = extractContentFromData(data);
        final byte[] salt = reduceSalt(normalizedSalt, passwordBytes);
        final byte[] passwordAndSaltBytes = merge(passwordBytes, salt);

        final Key secretKey = new SecretKeySpec(passwordAndSaltBytes, ENCRYPTION_ALGORITHM);
        final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(content);
    }

    private byte[] extractContentFromData(final byte[] data) {
        final int offset = MAX_PASSWORD_LENGTH - 1;
        final byte[] content = new byte[data.length - MAX_SALT_LENGTH];
        for (int i = 0; i < content.length; i++) {
            content[i] = data[i + offset];
        }
        return content;
    }

    private byte[] extractSaltFromData(final byte[] data) {
        final byte[] salt = new byte[MAX_SALT_LENGTH];
        for (int i = 0; i < MAX_SALT_LENGTH; i++) {
            salt[i] = data[i];
        }
        return salt;
    }

    private byte[] reduceSalt(final byte[] salt, final byte[] passwordBytes) {
        if (passwordBytes.length == MAX_PASSWORD_LENGTH) {
            return new byte[0];
        }

        final int mod = passwordBytes.length % MAX_PASSWORD_LENGTH;
        final int firstSaltByte = MAX_SALT_LENGTH - (MAX_PASSWORD_LENGTH - mod);

        final byte[] result = new byte[salt.length - firstSaltByte];
        for (int i = 0; i < result.length; i++) {
            result[i] = salt[i + firstSaltByte];
        }
        return result;
    }

    private byte[] merge(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        int saltOffset = array1.length;
        for (int i = 0; i < result.length; i++) {
            if (i < saltOffset) {
                result[i] = array1[i];
            } else {
                result[i] = array2[i - saltOffset];
            }
        }
        return result;
    }
}
