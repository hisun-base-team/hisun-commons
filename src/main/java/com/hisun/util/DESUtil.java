/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class DESUtil {

    private final static String DES = "DES";
    private final static String PRIVATE_KEY = ")!(@*#&$^%";

    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String newStr = new BASE64Encoder().encode(bt);
        return newStr;
    }


    public static void encrypt(File orgFile,File encryptFile) throws Exception{
        FileInputStream fis = new FileInputStream(orgFile);
        FileOutputStream fos = new FileOutputStream(encryptFile);
        Long fileLength = orgFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        fos.write(encrypt(buffer,PRIVATE_KEY.getBytes()));
        fis.close();
        fos.flush();
        fos.close();
    }

    public static String decrypt(String data, String key) throws Exception {
        if (null == data) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt);
    }

    public static void decrypt(File encryptFile,File destFile) throws Exception{
        FileInputStream fis = new FileInputStream(encryptFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        Long fileLength = encryptFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        fos.write(decrypt(buffer,PRIVATE_KEY.getBytes()));
        fis.close();
        fos.flush();
        fos.close();
    }




    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //生成一个随机数
        SecureRandom sr = new SecureRandom();
        //根据key创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        //创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secureKey = keyFactory.generateSecret(dks);
        //解密
        Cipher cipher = Cipher.getInstance(DES);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
        return cipher.doFinal(data);
    }


    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //生成一个随机数
        SecureRandom sr = new SecureRandom();
        //从key创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        //创建一个密钥工厂，然后转换成secretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secureKey = keyFactory.generateSecret(dks);
        //实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        //初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
        return cipher.doFinal(data);
    }


    public static void main(String[] args)throws Exception {

        File orgFile = new File("/Users/zhouying/Downloads/0004/010.简历材料/0102.jpg");
        File desFile = new File("/Users/zhouying/Downloads/0004/010.简历材料/aaaa");
        File xxxFile = new File("/Users/zhouying/Downloads/0004/010.简历材料/xxx.jpg");
        DESUtil.encrypt(orgFile,desFile);
        DESUtil.decrypt(desFile,xxxFile);
    }

}
