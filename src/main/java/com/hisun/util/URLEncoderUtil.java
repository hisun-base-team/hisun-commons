/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class URLEncoderUtil {
    public static String encode(String s)throws UnsupportedEncodingException {
        if (WebUtil.getRequest().getHeader("User-Agent").toUpperCase().indexOf("FIREFOX") > 0) {
            return new String(s.getBytes(), "ISO8859_1");
        }
        return URLEncoder.encode(s,"UTF-8");
    }
}
