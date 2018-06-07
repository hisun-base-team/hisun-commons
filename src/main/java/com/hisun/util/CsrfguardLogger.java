/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;

import org.apache.log4j.Logger;
import org.owasp.csrfguard.log.ILogger;
import org.owasp.csrfguard.log.LogLevel;

import java.util.Date;

public class CsrfguardLogger implements ILogger {

    private Logger logger = Logger.getLogger(CsrfguardLogger.class);

    @Override
    public void log(String msg) {
        log(LogLevel.Info, msg);
    }

    @Override
    public void log(LogLevel level, String msg) {
        if(LogLevel.Error.equals(level)) {
            logger.error(String.format("[%s] [%s] %s", new Date(), String.valueOf(level), msg));
        } else {
            logger.info(String.format("[%s] [%s] %s", new Date(), String.valueOf(level), msg));
        }
    }

    @Override
    public void log(Exception exception) {
        log(LogLevel.Error, exception);
    }

    @Override
    public void log(LogLevel level, Exception exception) {
        String.format("[%s] [%s] %s", new Date(), String.valueOf(level), String.valueOf(exception));
    }
}
