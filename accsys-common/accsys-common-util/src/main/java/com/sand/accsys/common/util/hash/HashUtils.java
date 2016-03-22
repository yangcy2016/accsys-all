package com.sand.accsys.common.util.hash;

import java.security.MessageDigest;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 上午 9:40
 * @since : ${VERSION}
 */
public class HashUtils {
    private final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static String hash(byte[] orgin){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(orgin);
            byte tmp[]  = digest.digest();
            char str[] = new char[32];
            int k = 0;
            for (byte b : tmp) {
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String hash(String orgin){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(orgin.getBytes("UTF-8"));
            byte tmp[]  = digest.digest();
            char str[] = new char[32];
            int k = 0;
            for (byte b : tmp) {
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
