/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.util;

import com.aspose.cells.License;
import org.apache.log4j.Logger;

import java.io.InputStream;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class AsposeLicenseUtil {

    private final static Logger logger = Logger.getLogger(AsposeLicenseUtil.class);

    private static AsposeLicenseUtil util;

    private AsposeLicenseUtil(){

    }

    public static AsposeLicenseUtil newInstance() {
        if(util==null) {
            synchronized (AsposeLicenseUtil.class){
                if(util==null){
                    util = new AsposeLicenseUtil();
                    try {
                        util.init();
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        return util;
    }

    public void init() throws Exception {
        InputStream is = AsposeLicenseUtil.class.getClassLoader().getResourceAsStream("aspose-license.xml");
        if (is == null) {
            throw new Exception("aspose-license.xml is not found.");
        }
        License aposeLic = new License();
        aposeLic.setLicense(is);
    }
}
