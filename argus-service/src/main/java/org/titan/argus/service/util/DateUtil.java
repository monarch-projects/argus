package org.titan.argus.service.util;

import java.util.Date;

/**
 * @Title: DateUtil
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
public class DateUtil {

    public static long currentTime() {
        return new Date().getTime();
    }
}
