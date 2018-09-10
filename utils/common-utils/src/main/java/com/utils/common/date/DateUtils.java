package com.utils.common.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author tanglei
 * @date 18/9/7 下午5:49
 */
@Slf4j
public class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转换成字符串，partten为空
     * 转成默认格式yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param partten
     * @return
     */
    public static String format(Date date, String partten) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        if (StringUtils.hasText(partten)) {
            sdf.applyPattern(partten);
            return sdf.format(date);
        }
        sdf.applyPattern(DEFAULT_PATTERN);
        return sdf.format(date);
    }

    /**
     * 将指定字符串格式转成日期，partten为空
     * 默认格式yyyy-MM-dd HH:mm:ss，转成日期
     *
     * @param source
     * @param partten
     * @return
     */
    public static Date parse(String source, String partten) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            if (StringUtils.hasText(partten)) {
                sdf.applyPattern(partten);
                return sdf.parse(source);
            }
            sdf.applyPattern(DEFAULT_PATTERN);
            return sdf.parse(source);
        } catch (ParseException e) {
            log.error("日期格式转换异常", e);
        }
        return null;
    }

    /**
     * 获取日期当天开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartDayTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000000);
        return calendar.getTime();
    }

    /**
     * 获取日期当天结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndDayTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999999);
        return calendar.getTime();
    }

    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        return calendar;
    }

    private static void setStart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000000);
    }

    private static void setEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999999);
    }

    /**
     * 获取日期所在周开始的时间
     *
     * @param date
     * @return
     */
    public static Date getStartWeekTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        setStart(calendar);
        return calendar.getTime();
    }

    /**
     * 获取日期所在周结束的时间
     *
     * @param date
     * @return
     */
    public static Date getEndWeekTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        setEnd(calendar);
        return calendar.getTime();
    }

    /**
     * 获取日期所在月的开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartMonthTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        setStart(calendar);
        return calendar.getTime();
    }

    /**
     * 获取日期所在月的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndMonthTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, -1);
        setEnd(calendar);
        return calendar.getTime();
    }

    /**
     * 获取日期中的年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期中的月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期中的天
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取多少天之前或之后的时间
     *
     * @param days
     * @return
     */
    public static Date getDateAfter(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取两个时间之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getBetweenDay(Date startDate, Date endDate) {
        int days = 0;
        if (startDate != null && endDate != null) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate.compareTo(endDate) >= 0 ? endDate : startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(startDate.compareTo(endDate) >= 0 ? startDate : endDate);
            days += endCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR);
            if (startCalendar.get(Calendar.YEAR) != endCalendar.get(Calendar.YEAR)) {
                while (startCalendar.get(Calendar.YEAR) != endCalendar.get(Calendar.YEAR)) {
                    days += startCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
                    startCalendar.add(Calendar.YEAR, 1);
                }
            }
        }
        return days;
    }
}
