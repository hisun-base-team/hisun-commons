/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;


import org.apache.log4j.Logger;
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

    private final static Logger logger = Logger.getLogger(DESUtil.class);

    private final static String DES = "DES";
    private final static String PRIVATE_KEY = ")H!I(S@U*N#U&N$I^O%N";
    private Cipher cipher;
    private SecretKey secureKey;
    private SecureRandom sr;

    private DESUtil() throws Exception {
        //生成一个随机数
        sr = new SecureRandom();
        //根据key创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(PRIVATE_KEY.getBytes());
        //创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        secureKey = keyFactory.generateSecret(dks);
        //解密
        cipher = Cipher.getInstance(DES);
    }

    public static DESUtil getInstance() {
        try {
            return new DESUtil();
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    public String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes());
        String newStr = new BASE64Encoder().encode(bt);
        return newStr;
    }


    public void encrypt(File orgFile, File encryptFile) throws Exception {
        FileInputStream fis = new FileInputStream(orgFile);
        FileOutputStream fos = new FileOutputStream(encryptFile);
        Long fileLength = orgFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        fos.write(encrypt(buffer));
        fis.close();
        fos.flush();
        fos.close();
    }

    public String decrypt(String data) throws Exception {
        if (null == data) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf);
        return new String(bt);
    }

    public void decrypt(File encryptFile, File destFile) throws Exception {
        FileInputStream fis = new FileInputStream(encryptFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        Long fileLength = encryptFile.length();
        byte[] buffer = new byte[fileLength.intValue()];
        fis.read(buffer);
        fos.write(decrypt(buffer));
        fis.close();
        fos.flush();
        fos.close();
    }


    private byte[] decrypt(byte[] data) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
        return cipher.doFinal(data);
    }


    private byte[] encrypt(byte[] data) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
        return cipher.doFinal(data);
    }

}
