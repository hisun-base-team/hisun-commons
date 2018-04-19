package com.hisun.util;


public class IpRangeHelper {
	
	//将十进制整数形式转换成127.0.0.1形式的ip地址
    public static String longToIP(long longIp){
        StringBuilder sb = new StringBuilder("");
        //直接右移24位
        long temp = (longIp >> 24);
        sb.append(String.valueOf(temp >= 0 ? temp : temp + 256));
        sb.append(".");
        //将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >> 16));
        sb.append(".");
        //将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >> 8));
        sb.append(".");
        //将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
   
}
