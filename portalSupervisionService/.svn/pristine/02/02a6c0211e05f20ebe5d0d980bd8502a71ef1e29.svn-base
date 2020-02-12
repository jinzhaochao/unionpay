package com.unionpay.common.util;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/*
 * FileName：MD5Util.java
 * Description：	MD5加密类（封装jdk自带的md5加密方法）
 */
public class MD5Util {

    /**
     * 循环次数
     */
    private static final int hashIterations = 1024;

    /**
     * 加密
     *
     * @param source the source
     * @return the string
     * @version * 2017-07-14 chenchen create
     */
    public static String encrypt(String source) {
        return encodeMd5(source.getBytes());
    }

    private static String encodeMd5(byte[] source) {
        try {
            return encodeHex(MessageDigest.getInstance("MD5").digest(source));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }


    /**
     * 获取随机盐值
     *
     * @param length
     * @return
     */
    public static String getRandomSalt(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }



    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @version * 2017-07-14 chenchen create
     */
    public static void main(String[] args) {
        //System.out.println(encrypt("123456"));
    }
}
