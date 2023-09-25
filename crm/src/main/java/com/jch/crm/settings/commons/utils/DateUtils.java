package com.jch.crm.settings.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date类型数据进行处理的工具类
 */
public class DateUtils {

    /**
     * 对Date对象格式化
     * @param date
     * @return
     */
    public static String formateDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = sdf.format(date);
        return dataStr;
    }

    
}
