package org.ae2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Utils {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes of salt
        random.nextBytes(salt);
        return bytesToString(salt);
    }

    public static String bytesToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] stringToBytes(String string) {
        return Base64.getDecoder().decode(string);
    }

    public static byte[] extendArray(byte[] originalArray, byte[] extension) {
        // extend array originalArray to originalArray+extension
        byte[] newArray = new byte[originalArray.length + extension.length];
        System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
        System.arraycopy(extension, 0, newArray, originalArray.length, extension.length);

        return newArray;
    }

    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();

        // Iterate over each byte in the byte array
        for (byte b : byteArray) {
            // Convert each byte to a two-digit hexadecimal
            // representation and add it to the string
            hexString.append(String.format("%02X", b & 0xFF));
        }

        return hexString.toString();
    }

    public static String encrypt(String originalPassword, String salt, String ID) {
        // encrypt: SHA-512(SHA-256(password)+salt+id))

        // From String to bytes.
        byte[] bytesOriginalPassword = originalPassword.getBytes(); // Errors can occur with non-fixed-length arrays
        byte[] bytesSalt = Utils.stringToBytes(salt); // 16 bytes arrays.
        byte[] bytesID = ID.getBytes();

        byte[] saltAndID = Utils.extendArray(bytesSalt, bytesID);
        try {
            // Indicate the algorithm SHA-512
            MessageDigest digestInner = MessageDigest.getInstance("SHA-256");
            MessageDigest digestOuter = MessageDigest.getInstance("SHA-512");

            // Get SHA-512(password)
            byte[] innerPassword = digestInner.digest(bytesOriginalPassword);

            // Get SHA-512(password)+salt+id
            byte[] toBeEncrypt = Utils.extendArray(innerPassword, saltAndID);

            byte[] outerPassword = digestOuter.digest(toBeEncrypt);

            return byteArrayToHexString(outerPassword);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
