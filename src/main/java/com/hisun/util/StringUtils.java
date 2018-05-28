package com.hisun.util;

/**
 * Created by zhouying on 2017/9/16.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    public static String trimNull2Empty(String s){
        return s==null?"":s;
    }

    public static String trimNullCharacter2Empty(String s){
        if(s==null){
            return "";
        }else if(s.toLowerCase().equals("null")){
            return "";
        }
        return s;
    }

    public static String trimBlankCharacter2Empty(String str) {
        if (str == null) {
            return "";
        } else {
            //去掉换行符
            str = str.replaceAll("[\\s\b\r)]*", "");
            str = str.replaceAll("[\u0007]*","");
            //去掉全角空格
            str = org.apache.commons.lang3.StringUtils.trim(str.replace((char) 12288, ' '));
            return str;
        }
    }

    public static boolean isNotDate(String dateStr){
        if(StringUtils.isNotEmpty(dateStr)) {
            int lengh = dateStr.length();
            if (lengh == 4 || lengh == 6 || lengh == 8) {
                if (StringUtils.isNumeric(dateStr)) {
                    if(lengh==6){
                        int mouth = Integer.parseInt(dateStr.substring(3,5));
                        if(mouth>=1&&mouth<=12){
                            return false;
                        }
                        return true;
                    }else if(lengh==8){
                        int mouth = Integer.parseInt(dateStr.substring(3,5));
                        int day = Integer.parseInt(dateStr.substring(5,7));
                        if(mouth>=1&&mouth<=12&&day>=1&&day<=31){
                            return false;
                        }
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
