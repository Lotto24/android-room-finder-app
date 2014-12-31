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
