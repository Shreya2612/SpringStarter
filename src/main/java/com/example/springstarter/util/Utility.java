package com.example.springstarter.util;

import com.example.springstarter.model.request.UserModel;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public interface Utility {
    String AUTH_TOKEN = "S3cretT0k3N@=";

    String REGEX_ALPHANUMERIC = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@=]))";

    static boolean userValidation(UserModel model) {
        return
                model.getFirstName().toString() != null &&
                        model.getPassword().toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,10})") &&
                        model.getContact().toString().length() == 10 && model.getContact().toString().matches("[0-9]+");

    }


    public static String generateKey() {  //used for generating unique key of 128 bit
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[128];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

    public static String computeHash(String password, String salt) throws Exception {
        try {
            String concat =
                    (salt == null ? "" : salt) +
                            password;
            MessageDigest md = MessageDigest.getInstance("SHA-512"); //MessageDigest used to generate hash value
            byte[] bHash = md.digest(concat.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bHash);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        }
    }

    static String bytesToHex(byte[] bytes) {   //converting byte array to hexadecimal string
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int x = 0xFF & bytes[i];
            chars[i * 2] = HEX_CHARS[x >>> 4];
            chars[1 + i * 2] = HEX_CHARS[0x0F & x];
        }
        return new String(chars);
    }

    static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static String generateSalt() {
        byte[] salt = new byte[32];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG"); //SecureRandom is a Java class for generate random bytes and here using SHA1 Algo.// PRNG=Pseudo random no. generator.
            sr.nextBytes(salt); //generate a unique salt
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bytesToHex(salt); //salt will always be a bytes so converting to hexadecimal string.
    }


} //whatever stays inside utility is always an independent piece of code.

