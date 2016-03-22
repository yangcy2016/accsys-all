package com.sand.accsys.common.util.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : huanghy
 * @create : 2016/3/14 0014 下午 5:18
 * @since : ${VERSION}
 */
public class DateTimeHelper {

    public static String nowDateTimeString(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

}
