package com.biblioteca.utilidades;

import java.security.MessageDigest;

public class Encriptar {
    public static String toHexString(byte[] hash){
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for(int i = 0; i < hash.length; i++){
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String encrypt(String text){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes("UTF-8"));
            return toHexString(hash);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
