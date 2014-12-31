package de.esailors.apps.fileencryption;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

;

/**
 * Created by fbaue on 12/29/14.
 */
public class EncryptionService {

    public byte[] encrypt(final byte[] data, final String password) throws Exception {
        final byte[] passwordBytes = password.getBytes();

        if (passwordBytes.length > EncryptionUtil.MAX_PASSWORD_LENGTH) {
            // install java policy files to remove restriction
            throw new IllegalArgumentException("Password must not be longer than 16 byte");
        }

        final byte[] salt = generateSaltAsNeeded(passwordBytes);
        final byte[] passwordAndSaltBytes = EncryptionUtil.merge(passwordBytes, salt);

        final Key secretKey = new SecretKeySpec(passwordAndSaltBytes, EncryptionUtil.ENCRYPTION_ALGORITHM);
        final Cipher cipher = Cipher.getInstance(EncryptionUtil.TRANSFORMATION);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[cipher.getBlockSize()]);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        final byte[] encContent = cipher.doFinal(data);
        final byte[] result = EncryptionUtil.merge(normalizeSalt(salt), encContent);

        return result;
    }

    private byte[] normalizeSalt(final byte[] salt) {
        if (salt.length == EncryptionUtil.MAX_PASSWORD_LENGTH) {
            return salt;
        } else {
            final byte[] bytes = new byte[EncryptionUtil.MAX_SALT_LENGTH];
            final int offset = EncryptionUtil.MAX_SALT_LENGTH - salt.length;
            for (int i = 0; i < salt.length; i++) {
                bytes[i + offset] = salt[i];
            }
            return bytes;
        }
    }

    private byte[] generateSaltAsNeeded(final byte[] passwordBytes) throws Exception {
        final byte[] result;
        final int mod = passwordBytes.length % EncryptionUtil.MAX_PASSWORD_LENGTH;

        if (mod == 0) {
            result = new byte[0];
        } else {
            final byte[] salt = new byte[EncryptionUtil.MAX_PASSWORD_LENGTH - mod];
            final SecureRandom sr = SecureRandom.getInstance(EncryptionUtil.SECURE_RANDOM_ALGORITHM);
            sr.nextBytes(salt);
            result = salt;
        }
        return result;
    }
}
