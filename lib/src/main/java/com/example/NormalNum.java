package com.example;

import org.joda.time.DateTime;

/**
 * Created by mac on 2017/7/4.
 */

public class NormalNum {

    public static void main(String[] agrs) {


        for (int i = 0; i <= 23; i++) {

            DateTime nowData = new DateTime(2017, 7, 4,i, 0, 0);
            DateTime normalizationFor3Hour = getNormalizationFor3Hour(nowData);
            DateTime normalizationFor12Hour = getNormalizationFor12Hour(nowData);
            System.out.println(format(nowData)+"------" +format(normalizationFor3Hour)+"-----"+format(normalizationFor12Hour));
        }

    }

    public static String format(DateTime dateTime) {
        return dateTime.toString("yyyyMMddHH");
    }

    /**
     * 时间归一化
     * 实况 地面 三小时间隔
     * 显示时次 02, 05, 08, 11, 14, 17, 20, 23
     * @return
     */
    public static DateTime getNormalizationFor3Hour(DateTime curDataTime) {
        int[] value = new int[]{0, 2, 5, 8, 11, 14, 17, 20, 23};
        int curMinute = curDataTime.getMinuteOfDay();
        DateTime yesterday = curDataTime.minusDays(1);
        if (curMinute < 90) {

            return new DateTime(yesterday.getYear(),
                    yesterday.getMonthOfYear(), yesterday.getDayOfMonth(), 23, 0);
        }else
        {
            //当前时间减去 90 分钟后，得到的小时在哪个范围并向下取整
            DateTime minuteDate = curDataTime.minusMinutes(90);
            int year = minuteDate.getYear();
            int monthOfYear = minuteDate.getMonthOfYear();
            int dayOfMonth = minuteDate.getDayOfMonth();
            int hourOfDay = minuteDate.getHourOfDay();
            int index =-1;
            for (int i = 0; i < value.length-1; i++) {
                if (hourOfDay >= value[i] && hourOfDay < value[i + 1]) {
                    index = i;
                    break;
                }
            }
            if (index == 0 ) {
                return new DateTime(yesterday.getYear(),
                        yesterday.getMonthOfYear(), yesterday.getDayOfMonth(), 23, 0);

            }else
            {
                return new DateTime(year, monthOfYear, dayOfMonth, value[index], 0);
            }
        }

    }

    /**
     * 时间归一化
     * 实况 地面 三小时间隔
     * 显示时次 08, 20
     * @return
     */
    public static DateTime getNormalizationFor12Hour(DateTime curDataTime) {
        int[] value = new int[]{8, 20};
        int curMinute = curDataTime.getMinuteOfDay();
        DateTime yesterday = curDataTime.minusDays(1);
        if (curMinute < 180) {
            return new DateTime(yesterday.getYear(),
                    yesterday.getMonthOfYear(), yesterday.getDayOfMonth(), 20, 0);
        } else {
            //当前时间减去 3 小时后，得到的小时在哪个范围并向下取整
            DateTime minuteDate = curDataTime.minusHours(3);
            int year = minuteDate.getYear();
            int monthOfYear = minuteDate.getMonthOfYear();
            int dayOfMonth = minuteDate.getDayOfMonth();
            int hourOfDay = minuteDate.getHourOfDay();

            if (hourOfDay >= 8 && hourOfDay < 20) {
                return new DateTime(year, monthOfYear, dayOfMonth, 8, 0);
            } else if (hourOfDay >= 20) {
                return new DateTime(year, monthOfYear, dayOfMonth, 20, 0);

            } else {
                return new DateTime(yesterday.getYear(),
                        yesterday.getMonthOfYear(), yesterday.getDayOfMonth(), 20, 0);
            }
        }
    }
}
