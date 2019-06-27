package com.sysu.ceres.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStringUtil {
    public  static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
