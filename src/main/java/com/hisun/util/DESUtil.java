/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;


import com.sun.crypto.provider.AESKeyGenerator;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

public class DESUtil {

    private final  String DES = "DES";
    private final  String PRIVATE_KEY = "HisunUnion";
    private byte[] keybytes;

    public DESUtil(String key){
        keybytes = (key+PRIVATE_KEY).getBytes();
    }

    public  static DESUtil getInstance(String key){
        return new DESUtil(key);
    }

    public  String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(),keybytes);
        String newStr = new BASE64Encoder().encode(bt);
        return newStr;
    }


    public  void encrypt(File orgFile,File encryptFile) throws Exception{
        FileInputStream fis = new FileInputStream(orgFile);
        FileOutputStream fos = new FileOutputStream(encryptFile);
        Long fileLength = orgFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        fos.write(encrypt(buffer,keybytes));
        fis.close();
        fos.flush();
        fos.close();
    }

    public  String decrypt(String data) throws Exception {
        if (null == data) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,keybytes);
        return new String(bt);
    }

    public  void decrypt(File encryptFile,File destFile) throws Exception{
        FileInputStream fis = new FileInputStream(encryptFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        Long fileLength = encryptFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        fos.write(decrypt(buffer,keybytes));
        fis.close();
        fos.flush();
        fos.close();
    }

    public  void decrypt(File encryptFile,OutputStream os) throws Exception{
        FileInputStream fis = new FileInputStream(encryptFile);
        Long fileLength = encryptFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        os.write(decrypt(buffer,keybytes));
        fis.close();
        os.flush();
        os.close();
    }



    private  byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //生成一个随机数
        SecureRandom sr = new SecureRandom();
        //根据key创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        //创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secureKey = keyFactory.generateSecret(dks);
        //解密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, secureKey,new IvParameterSpec(dks.getKey()),sr);
        return cipher.doFinal(data);
    }


    private  byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //生成一个随机数
        SecureRandom sr = new SecureRandom();
        //从key创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        //创建一个密钥工厂，然后转换成secretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secureKey = keyFactory.generateSecret(dks);
        //实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secureKey,new IvParameterSpec(dks.getKey()),sr);
        return cipher.doFinal(data);
    }

    public  static  void main(String[] args)throws Exception {
        File orgFile = new File("D:/0102.jpg");
        File desFile = new File("D:/aaaa");
        File xxxFile = new File("D:/xxx.jpg");
        DESUtil.getInstance("aaa").encrypt(orgFile,desFile);
        DESUtil.getInstance("aaa").decrypt(desFile,xxxFile);

        System.out.println(DESUtil.getInstance("").encrypt("asddsfsfddsaa"));
    }

}
