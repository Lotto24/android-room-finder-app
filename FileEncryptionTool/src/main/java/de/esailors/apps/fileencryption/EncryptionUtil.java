package de.esailors.apps.fileencryption;

/**
 * Created by fbaue on 12/30/14.
 */
public class EncryptionUtil {

    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final int MAX_SALT_LENGTH = MAX_PASSWORD_LENGTH - 1;
    public static final String ENCRYPTION_ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

    static byte[] merge(byte[] array1, byte[] array2) {
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
