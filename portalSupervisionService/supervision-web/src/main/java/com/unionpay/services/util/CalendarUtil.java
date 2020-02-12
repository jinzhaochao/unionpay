package com.unionpay.services.util;

import com.unionpay.common.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
    /**
     * 获取本周开始时间 第一天00:00:00（即，上周截止时间）
     * @param calendar
     * @return
     */
    public static Date getCurrentWeekStartTime(Calendar calendar){
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取本周结束时间
     * @param calendar
     * @return
     */
    public static Date getCurrentWeekEndTime(Calendar calendar){
        calendar.setTime(getCurrentWeekStartTime(calendar));
        calendar.add(Calendar.DAY_OF_WEEK,7);
        return calendar.getTime();
    }

    /**
     * 获取上周开始时间 第一天00:00:00（即，上上周截止时间）
     * @param calendar
     * @return
     */
    public static Date getLastWeekStartTime(Calendar calendar){
        calendar.setTime(getCurrentWeekStartTime(calendar));
        calendar.add(Calendar.DAY_OF_WEEK,-7);
        return calendar.getTime();
    }

    /**
     * 获取上上周开始时间 第一天00:00:00
     * @param calendar
     * @return
     */
    public static Date getBeforeLastWeekStartTime(Calendar calendar){
        calendar.setTime(getCurrentWeekStartTime(calendar));
        calendar.add(Calendar.DAY_OF_WEEK,-14);
        return calendar.getTime();
    }

    /**
     * 获取本月开始时间 第一天的00:00:00（即，上月结束时间）
     * @param calendar
     * @return
     */
    public static Date getCurrentMonthStartTime(Calendar calendar){
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取本月结束时间
     * @param calendar
     * @return
     */
    public static Date getCurrentMonthEndTime(Calendar calendar){
        calendar.setTime(getCurrentMonthStartTime(calendar));
        calendar.add(Calendar.MONTH,1);
        return calendar.getTime();
    }

    /**
     * 获取上月开始时间 第一天的00:00:00 （即，上上月的结束时间）
     * @param calendar
     * @return
     */
    public static Date getLastMonthStartTime(Calendar calendar){
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1, calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取上上月开始时间 第一天00:00:00
     * @param calendar
     * @return
     */
    public static Date getBeforeLastMonthStartTime(Calendar calendar){
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-2, calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取本季开始时间 第一天00:00:00（即，上季结束时间）
     * @param calendar
     * @return
     */
    public static Date getCurrentQuarterStartTime(Calendar calendar){
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                calendar.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                calendar.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                calendar.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.DATE, 1);
            now = DateUtil.parseDates(DateUtil.getDay(calendar.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取本季结束时间
     * @param calendar
     * @return
     */
    public static Date getCurrentQuarterEndTime(Calendar calendar){
        calendar.setTime(getCurrentQuarterStartTime(calendar));
        calendar.add(Calendar.MONTH,3);
        return calendar.getTime();
    }

    /**
     * 获取上季开始时间 第一天 00:00:00（即，上上季结束时间）
     * @param calendar
     * @return
     */
    public static Date getLastQuarterStartTime(Calendar calendar){
        calendar.setTime(getCurrentQuarterStartTime(calendar));
        calendar.add(Calendar.MONTH,-3);
        return calendar.getTime();
    }

    /**
     * 获取上上季开始时间 第一天 00:00:00
     * @param calendar
     * @return
     */
    public static Date getBeforeLastQuarterStartTime(Calendar calendar){
        calendar.setTime(getCurrentQuarterStartTime(calendar));
        calendar.add(Calendar.MONTH,-6);
        return calendar.getTime();
    }
}
