package com.company.project.core.layui;

/**
 * layui时间段解析
 *
 * @author : libaogang
 * @since 2019-07-29 9:11
 */
public class LayuiDurationResolver {
    public static String getBeginTime(String duration) {
        if (duration != null && duration.trim().length() != 0) {
            return duration.substring(0, 10);
        }
        return null;
    }

    public static String getEndTime(String duration) {
        if (duration != null && duration.trim().length() != 0) {
            return duration.substring(13, 23);
        }
        return null;
    }

}