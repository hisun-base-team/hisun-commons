/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class UserAgentDetector {

    static String[] MOBILE_BROWSER = {
            "nokia",
            "samsung",
            "midp-2",
            "cldc1.1",
            "symbianos",
            "maui",
            "untrusted/1.0",
            "windows ce",
            "iphone",
            "ipad",
            "android",
            "blackberry",
            "ucweb",
            "brew",
            "j2me",
            "yulong",
            "coolpad",
            "tianyu",
            "ty-",
            "k-touch",
            "haier",
            "dopod",
            "lenovo",
            "mobile",
            "huaqin",
            "aigo-",
            "ctc/1.0",
            "ctc/2.0",
            "cmcc",
            "daxian",
            "mot-",
            "sonyericsson",
            "gionee",
            "htc",
            "zte",
            "huawei",
            "webos",
            "gobrowser",
            "iemobile",
            "wap2.0",
            "ucbrowser",
            "ipod",
    };

    public static boolean detectMobileAgent() {
        String agent = WebUtil.getRequest().getHeader("User-Agent").toLowerCase();
        boolean isMobile = false;
        for (String browser : MOBILE_BROWSER) {
            if (agent.contains(browser)) {
                isMobile = true;
                break;
            }
        }
        return isMobile;
    }
}
