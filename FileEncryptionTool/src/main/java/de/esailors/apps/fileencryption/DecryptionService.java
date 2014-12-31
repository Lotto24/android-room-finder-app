/*
 * Copyright 2014 eSailors IT Solutions GmbH
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.esailors.apps.fileencryption;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by fbaue on 12/29/14.
 */
public class DecryptionService {

    public byte[] decrypt(final byte[] data, final String password) throws Exception {
        final byte[] passwordBytes = password.getBytes();

        final byte[] normalizedSalt = extractSaltFromData(data);
        final byte[] content = extractContentFromData(data);
        final byte[] salt = reduceSalt(normalizedSalt, passwordBytes);
        final byte[] passwordAndSaltBytes = EncryptionUtil.merge(passwordBytes, salt);

        final Key secretKey = new SecretKeySpec(passwordAndSaltBytes, EncryptionUtil.ENCRYPTION_ALGORITHM);
        final Cipher cipher = Cipher.getInstance(EncryptionUtil.TRANSFORMATION);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[cipher.getBlockSize()]);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        return cipher.doFinal(content);
    }

    private byte[] extractContentFromData(final byte[] data) {
        final int offset = EncryptionUtil.MAX_PASSWORD_LENGTH - 1;
        final byte[] content = new byte[data.length - EncryptionUtil.MAX_SALT_LENGTH];
        for (int i = 0; i < content.length; i++) {
            content[i] = data[i + offset];
        }
        return content;
    }

    private byte[] extractSaltFromData(final byte[] data) {
        final byte[] salt = new byte[EncryptionUtil.MAX_SALT_LENGTH];
        for (int i = 0; i < EncryptionUtil.MAX_SALT_LENGTH; i++) {
            salt[i] = data[i];
        }
        return salt;
    }

    private byte[] reduceSalt(final byte[] salt, final byte[] passwordBytes) {
        if (passwordBytes.length == EncryptionUtil.MAX_PASSWORD_LENGTH) {
            return new byte[0];
        }

        final int mod = passwordBytes.length % EncryptionUtil.MAX_PASSWORD_LENGTH;
        final int firstSaltByte = EncryptionUtil.MAX_SALT_LENGTH - (EncryptionUtil.MAX_PASSWORD_LENGTH - mod);

        final byte[] result = new byte[salt.length - firstSaltByte];
        for (int i = 0; i < result.length; i++) {
            result[i] = salt[i + firstSaltByte];
        }
        return result;
    }
}
