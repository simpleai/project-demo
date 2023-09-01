package com.xiaoruiit.project.demo.infrastructure.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final int FOUR = 4;
    public static final DateTimeFormatter DATE_TIME_FORMATTER;
    private static String[] parsePatterns;

    public DateUtils() {
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(String format, Date date) {
        return (new SimpleDateFormat(format)).format(date);
    }

    public static final Date dateTime(String format, String ts) {
        try {
            return (new SimpleDateFormat(format)).parse(ts);
        } catch (ParseException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        } else {
            try {
                return parseDate(str.toString(), parsePatterns);
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 86400000L;
        long nh = 3600000L;
        long nm = 60000L;
        long diff = endDate.getTime() - nowDate.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String convertDateToString(Date date) {
        return date == null ? null : DATE_TIME_FORMATTER.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static String minusFourHours(String targetTime) {
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern(YYYYMMDD);
        LocalDateTime time = LocalDateTime.parse(targetTime, targetFormatter);
        LocalDateTime resultDateTime = time.minusHours(4L);
        return resultFormatter.format(resultDateTime);
    }

    public static String plusDay(String targetTime, Long dayNum) {
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        LocalDate time = LocalDate.parse(targetTime, targetFormatter);
        LocalDate resultDateTime = time.plusDays(dayNum);
        return targetFormatter.format(resultDateTime);
    }

    public static String minusDay(String targetTime, Long dayNum) {
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        LocalDate time = LocalDate.parse(targetTime, targetFormatter);
        LocalDate resultDateTime = time.minusDays(dayNum);
        return targetFormatter.format(resultDateTime);
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getLastMaxMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, -1);
        calendar.set(5, calendar.getActualMaximum(5));
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static Date getBirthday(Date date, LocalDate now) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(1, now.getYear());
        return cal.getTime();
    }

    public static List<String> getBirthdayList() {
        List<String> birthdayList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, -1);

        for(int i = 0; i < 8; ++i) {
            Integer month = calendar.get(2) + 1;
            String monthString = month + "";
            if (month < 10) {
                monthString = "0" + month.toString();
            }

            int day = calendar.get(5);
            String dayString = day + "";
            if (day < 10) {
                dayString = "0".concat(dayString);
            }

            birthdayList.add(monthString.toString() + dayString);
            calendar.add(5, 1);
        }

        return birthdayList;
    }

    public static Date getLastDate(Date date) {
        if (null == date) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            return calendar.getTime();
        }
    }

    public static Date getNowDateDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    public static Date getInvaliddate(Date validdate, Integer effectiveDays, String applicableEndTime) {
        Date getInvaliddate = getLastDate(org.apache.commons.lang3.time.DateUtils.addDays(validdate, effectiveDays - 1));
        if (StringUtils.isNoneBlank(new CharSequence[]{applicableEndTime})) {
            Date endTime = parseDate(applicableEndTime);
            if (endTime.before(getInvaliddate)) {
                return getLastDate(endTime);
            }
        }

        return getLastDate(getInvaliddate);
    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / 86400000L;
        return Integer.parseInt(String.valueOf(between_days));
    }

    static {
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss"};
    }
}
