/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unionpay.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type Date util.
 *
 * @version 2017 -12-22 chenchen create
 */
public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfmsTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private final static SimpleDateFormat allTime = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 获取YYYY格式
	 *
	 * @return year year
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY格式
	 *
	 * @param date
	 *            the date
	 * @return year year
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getYear(Date date) {
		return sdfYear.format(date);
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return day day
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @param date
	 *            the date
	 * @return day day
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getDay(Date date) {
		return sdfDay.format(date);
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return days days
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @param date
	 *            the date
	 * @return days days
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getDays(Date date) {
		return sdfDays.format(date);
	}

	/**
	 * Gets all time.
	 *
	 * @param date
	 *            the date
	 * @return the all time
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getAllTime(Date date) {
		return allTime.format(date);
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return time time
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return time time
	 * @version 2018-08-24 zhengqiang create
	 */
	public static String getStrTime(Date date) {
		return sdfTime.format(date);
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 *
	 * @return ms time
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getMsTime() {
		return sdfmsTime.format(new Date());
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return all time
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getAllTime() {
		return allTime.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @param date
	 *            the date
	 * @return time time
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getTime(Date date) {
		return sdfTime.format(date);
	}

	/**
	 * Compare date boolean.
	 *
	 * @param s
	 *            the s
	 * @param e
	 *            the e
	 * @return boolean boolean
	 * @throws @version
	 *             2017 -12-22 chenchen create
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 *            the date
	 * @return date date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date parseDate(String date) {
		try {
			return sdfDay.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 *            the date
	 * @return date date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date parseDates(String date) {
		try {
			return sdfDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Parse days date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date parseDays(String date) {
		try {
			return sdfDays.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 *            the date
	 * @return date date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date parseTime(String date) {
		try {
			return sdfTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Parse all time date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date parseAllTime(String date) {
		try {
			return allTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 *            the date
	 * @param pattern
	 *            the pattern
	 * @return date date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date parse(String date, String pattern) {
		DateFormat fmt = new SimpleDateFormat(pattern);
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 *            the date
	 * @param pattern
	 *            the pattern
	 * @return string string
	 * @version 2017 -12-22 chenchen create
	 */
	public static String format(Date date, String pattern) {
		DateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.format(date);
	}

	/**
	 * 将长整型的数据转换为日期字符串
	 * 
	 * @param val
	 * @return
	 */
	public static String longVal2Str(Long val) {
		return sdfTime.format(new Date(val));
	}

	/**
	 * 把日期转换为Timestamp
	 *
	 * @param date
	 *            the date
	 * @return timestamp timestamp
	 * @version 2017 -12-22 chenchen create
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 *
	 * @param s
	 *            the s
	 * @return boolean boolean
	 * @version 2017 -12-22 chenchen create
	 */
	public static boolean isValidDate(String s) {
		try {
			sdfTime.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * 校验日期是否合法
	 *
	 * @param s
	 *            the s
	 * @param pattern
	 *            the pattern
	 * @return boolean boolean
	 * @version 2017 -12-22 chenchen create
	 */
	public static boolean isValidDate(String s, String pattern) {
		DateFormat fmt = new SimpleDateFormat(pattern);
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * 校验日期是否合法(yyyy-MM-dd)
	 *
	 * @param s
	 *            the s
	 */
	public static boolean isTrueDate(String date) {
		if (ToolUtil.isNotEmpty(date)) {
			Pattern p = Pattern.compile("^\\d{4}\\-\\d{2}\\-\\d{2}$");
			return p.matcher(date).matches();
		}
		return false;
	}

	/**
	 * Gets diff year.
	 *
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return the diff year
	 * @version 2017 -12-22 chenchen create
	 */
	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 *            the begin date str
	 * @param endDateStr
	 *            the end date str
	 * @return long day sub
	 * @version 2017 -12-22 chenchen create
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 日期相见
	 * 
	 * @param
	 * @return
	 * @author lina
	 * @version 1.0
	 * @date 2018/1/8
	 */
	public static long getDaySub(String beginDateStr, String endDateStr, String patten) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(patten);
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDate
	 *            the begin date
	 * @param endDate
	 *            the end date
	 * @return long day sub
	 * @version 2017 -12-22 chenchen create
	 * @author Administrator
	 */
	public static long getDaySub(Date beginDate, Date endDate) {
		long day = 0;
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 *
	 * @param days
	 *            the days
	 * @return after day date
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到指定日期n天之后的日期
	 *
	 * @param days
	 *            the days
	 * @return date after day date
	 * @version 2019 -12-4 jinzhao create
	 */
	public static String addDate(String date,String day) {
		long time = 0; // 得到指定日期的毫秒数
		try {
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day2 =Integer.parseInt(day);
		long day1 = day2*24*60*60*1000; // 要加上的天数转换成毫秒数
		time+=day1; // 相加得到新的毫秒数
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)); // 将毫秒数转换成日期
	}

	/**
	 * 得到指定日期n天之前的日期
	 *
	 * @param days
	 *            the days
	 * @return date before day date
	 * @version 2019 -12-23 jinzhao create
	 */
	public static String subTractDate(String date,String day) {
		long time = 0; // 得到指定日期的毫秒数
		try {
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day2 =Integer.parseInt(day);
		long day1 = day2*24*60*60*1000; // 要加上的天数转换成毫秒数
		time-=day1; // 相加得到新的毫秒数
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)); // 将毫秒数转换成日期
	}

	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 *            the days
	 * @return after day week
	 * @version 2017 -12-22 chenchen create
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * Parse range list.
	 *
	 * @param timeRange
	 *            the time range
	 * @param separator
	 *            the separator
	 * @param pattern
	 *            the pattern
	 * @return the list
	 * @version 2017 -12-22 chenchen create
	 * @description:lina加，日期范围选择框专用
	 * @param:
	 * @return:
	 * @author: lina
	 * @version: 1.0
	 * @date: 2017 /10/13
	 */
	public static List<Date> parseRange(String timeRange, String separator, String pattern) {
		List<Date> dateList = new ArrayList<>();
		String[] timeArr = timeRange.split(separator);
		Date startDate = DateUtil.parse(timeArr[0] + " 00:00:00", pattern);
		Date endDate = DateUtil.parse(timeArr[1] + " 23:59:59", pattern);
		dateList.add(startDate);
		dateList.add(endDate);
		return dateList;

	}

	/**
	 * 将日期转化为开始日期和结束日期
	 *
	 * @param date
	 *            the date
	 * @param pattern
	 *            the pattern
	 * @return the list
	 * @version 2017 -12-22 chenchen create
	 * @return:
	 * @author: cc
	 * @version: 1.0
	 * @date: 2017 /10/13
	 * -- jinzhao 2019-12-2
	 */
	public static List<Date> parseDateToRange(String date, String pattern) {
		List<Date> dateList = new ArrayList<>();
		Date startDate = null;
		Date endDate = null;
		if (StrUtil.isNotEmpty(date) && StrUtil.isNotEmpty(pattern)) {
			startDate = DateUtil.parse(date + " 00:00:00", date);
			endDate = DateUtil.parse(date + " 23:59:59", pattern);
		}
		dateList.add(startDate);
		dateList.add(endDate);
		return dateList;

	}

	/**
	 * 返回当天的00：00：00.000
	 *
	 * @return date
	 * @version 2018-01-02 chenchen create
	 */
	public static Date getCurrentDateStart() {
		return getDateStart(null);
	}

	/**
	 * 返回指定日期的00：00：00.000
	 *
	 * @param cal
	 *            the cal
	 * @return the date start
	 * @version 2018-01-02 chenchen create
	 */
	public static Date getDateStart(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 返回当前时间多少个月前的时间
	 * -- jinzhao 2019-12-10
	 */
	public static String getOutTime(Integer time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -time);
		String time1 = sdf.format(cal.getTime());
		return time1;
	}

	/**
	 * 返回当天的00：00：00.000
	 *
	 * @return date
	 * @version 2018-01-02 chenchen create
	 */
	public static Date getCurrentDateEnd() {
		return getDateEnd(null);
	}

	/**
	 * 返回指定日期的23:59:59.999
	 *
	 * @param cal
	 *            the cal
	 * @return the date start
	 * @version 2018-01-02 chenchen create
	 */
	public static Date getDateEnd(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * Gets first day by month.
	 *
	 * @param cal
	 *            the date
	 * @return the first day by month
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getFirstDayByMonth(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	/**
	 * Get last day by month date.
	 *
	 * @param cal
	 *            the date
	 * @return the date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getLastDayByMonth(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		// 获取前月的最后一天
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设为本月第一天
		cal.add(Calendar.MONTH, 1);// 月份加一
		cal.add(Calendar.DATE, -1);// 天数加 -1 = 上一个月的最后一天
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();

	}

	/**
	 * 获取当月第一天
	 *
	 * @return the current month first day
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getCurrentMonthFirstDay() {
		return getFirstDayByMonth(null);
	}

	/**
	 * 获取当月最后一天
	 *
	 * @return the current month last day
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getCurrentMonthLastDay() {
		return getLastDayByMonth(null);
	}

	/**
	 * Gets first day by week.
	 *
	 * @param cal
	 *            the date
	 * @return the first day by month
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getFirstDayByWeek(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		cal.add(Calendar.DATE, -day_of_week + 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	/**
	 * Get last day by week date.
	 *
	 * @param cal
	 *            the date
	 * @return the date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getLastDayByWeek(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		cal.add(Calendar.DATE, -day_of_week + 7);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();

	}

	/**
	 * 获取本周第一天
	 *
	 * @return the current month first day
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getCurrentWeekFirstDay() {
		return getFirstDayByWeek(null);
	}

	/**
	 * 获取本周最后一天
	 *
	 * @return the current month last day
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getCurrentWeekLastDay() {
		return getLastDayByWeek(null);
	}

	/**
	 * Gets first day by Year.
	 *
	 * @param cal
	 *            the date
	 * @return the first day by month
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getFirstDayByYear(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	/**
	 * Get last day by Year date.
	 *
	 * @param cal
	 *            the date
	 * @return the date
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getLastDayByYear(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.set(Calendar.DAY_OF_YEAR, 1);// 本年最后一天
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();

	}

	/**
	 * 获取本周第一天
	 *
	 * @return the current Year first day
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getCurrentYearFirstDay() {
		return getFirstDayByYear(null);
	}

	/**
	 * 获取本周最后一天
	 *
	 * @return the current Year last day
	 * @version 2017 -12-22 chenchen create
	 */
	public static Date getCurrentYearLastDay() {
		return getLastDayByYear(null);
	}

	public static void main(String[] args) {
		System.out.println(format(getCurrentMonthFirstDay(), "yyyy-MM-dd HH:mm:ss.SSS"));
		System.out.println(format(getCurrentMonthLastDay(), "yyyy-MM-dd HH:mm:ss.SSS"));
		System.out.println(format(getCurrentDateStart(), "yyyy-MM-dd HH:mm:ss.SSS"));
		System.out.println(format(getCurrentDateEnd(), "yyyy-MM-dd HH:mm:ss.SSS"));
		System.out.println(format(getCurrentWeekFirstDay(), "yyyy-MM-dd HH:mm:ss.SSS"));
		System.out.println(format(getCurrentWeekLastDay(), "yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
