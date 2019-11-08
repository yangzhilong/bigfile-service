package com.longge.bigfile.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import lombok.NonNull;

/**
 * 
 * @author roger yang
 * @date 6/21/2019
 */
public class DateUtils {
    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();
    
    private DateUtils() {}

    /**
     * 
     * @author roger yang
     * @date 6/21/2019
     */
    public static class Pattern {
        private Pattern() {}

        public static class Date {
            private Date() {}
            
            /**
             * yyyy-MM-dd
             */
            public static final String YYYY_MM_DD = "yyyy-MM-dd";
            /**
             * yyyy-M-dd
             */
            public static final String YYYY_M_DD = "yyyy-M-dd";
            /**
             * yyyy-M-d
             */
            public static final String YYYY_M_D = "yyyy-M-d";
            /**
             * yyyy/MM/dd
             */
            public static final String YYYY_MM_DD_2 = "yyyy/MM/dd";
            /**
             * yyyy/M/dd
             */
            public static final String YYYY_M_DD_2 = "yyyy/M/dd";
            /**
             * yyyy/M/d
             */
            public static final String YYYY_M_D_2 = "yyyy/M/d";
            /**
             * yyyyMMdd
             */
            public static final String YYYYMMDD = "yyyyMMdd";
            /**
             * yyyyMdd
             */
            public static final String YYYYMDD = "yyyyMdd";
            /**
             * yyyyMd
             */
            public static final String YYYYMD = "yyyyMd";
            
            /**
             * MM-dd-yyyy
             */
            public static final String MM_DD_YYYY = "MM-dd-yyyy";
            /**
             * M-dd-yyyy
             */
            public static final String M_DD_YYYY = "M-dd-yyyy";
            /**
             * M-d-yyyy
             */
            public static final String M_D_YYYY = "M-d-yyyy";
            /**
             * MM/dd/yyyy
             */
            public static final String MM_DD_YYYY_2 = "MM/dd/yyyy";
            /**
             * M/dd/yyyy
             */
            public static final String M_DD_YYYY_2 = "M/dd/yyyy";
            /**
             * M/d/yyyy
             */
            public static final String M_D_YYYY_2 = "M/d/yyyy";
            /**
             * MMddyyyy
             */
            public static final String MMDDYYYY = "MMddyyyy";
            /**
             * Mddyyyy
             */
            public static final String MDDYYYY = "Mddyyyy";
            /**
             * Mdyyyy
             */
            public static final String MDYYYY = "Mdyyyy";
        }
        
        public static class DateTime {
            private DateTime() {}
            
            /**
             * yyyy-MM-dd HH:mm:ss
             */
            public static final String YYYY_MM_DD_SPACE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
            /**
             * yyyy-M-d H:m:s
             */
            public static final String YYYY_M_D_SPACE_H_M_S = "yyyy-M-d H:m:s";
            /**
             * yyyy/MM/dd HH:mm:ss
             */
            public static final String YYYY_MM_DD_SPACE_HH_MM_SS2 = "yyyy/MM/dd HH:mm:ss";
            /**
             * HH:mm yyyy-MM-dd
             */
            public static final String HH_MM_SPACE_YYYY_MM_DD = "HH:mm yyyy-MM-dd";
            /**
             * HH:mm yyyy/MM/dd
             */
            public static final String HH_MM_SPACE_YYYY_MM_DD2 = "HH:mm yyyy/MM/dd";
            /**
             * H:mm yyyy-MM-dd
             */
            public static final String H_MM_SPACE_YYYY_MM_DD = "H:mm yyyy-MM-dd";
            /**
            /**
             * H:mm yyyy/MM/dd
             */
            public static final String H_MM_SPACE_YYYY_MM_DD2 = "H:mm yyyy/MM/dd";
            /**
             * H:mm,yyyy-MM-dd
             */
            public static final String H_MM_COMMA_YYYY_MM_DD = "H:mm,yyyy-MM-dd";
            /**
             * H:mm,yyyy-MM-dd
             */
            public static final String H_MM_COMMA_YYYY_MM_DD2 = "H:mm,yyyy/MM/dd";
            /**
             * H:mm,yyyy-M-d
             */
            public static final String H_MM_COMMA_YYYY_M_D = "H:mm,yyyy-M-d";
            /**
             * H:m,yyyy-M-d
             */
            public static final String H_M_COMMA_YYYY_M_D = "H:m,yyyy-M-d";
            /**
             * H:mm,yyyy-M-d
             */
            public static final String H_MM_COMMA_YYYY_M_D2 = "H:mm,yyyy/M/d";
            /**
             * H:m,yyyy-M-d
             */
            public static final String H_M_COMMA_YYYY_M_D2 = "H:m,yyyy/M/d";
        }
    }

    /**
     * format no hour/minute/second date
     * @param date
     * @param pattern {@link DateUtils.Pattern.Date}
     * @return
     */
    public static String formatDate(@NonNull Date date, @NonNull String pattern) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = LocalDateTime.ofInstant(instant, zone).toLocalDate();
        return formatDate(localDate, pattern);
    }
    
    /**
     * format LocalDate
     * @param localDate
     * @param pattern {@link DateUtils.Pattern.Date}
     * @return
     */
    public static String formatDate(@NonNull LocalDate localDate, @NonNull String pattern) {
        if(StringUtils.isBlank(pattern)) {
            throw new NullPointerException("pattern is null");
        }
        
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return localDate.format(formatter);
    }
    
    /**
     * format have hour/minute/second date
     * @param date
     * @param pattern {@link DateUtils.Pattern.DateTime}
     * @return
     */
    public static String formatDateTime(@NonNull Date date, @NonNull String pattern) {
        LocalDateTime formatDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return formatDateTime(formatDateTime, pattern);
    }

    /**
     * format LocalDateTime
     * @param localDateTime
     * @param pattern {@link DateUtils.Pattern.DateTime}
     * @return
     */
    public static String formatDateTime(@NonNull LocalDateTime localDateTime, @NonNull String pattern) {
        if(StringUtils.isBlank(pattern)) {
            throw new NullPointerException("pattern is null");
        }
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * parse no hour/minute/second date
     * @param date
     * @param pattern {@link DateUtils.Pattern.Date}
     * @return
     */
    public static Date parseDate(@NonNull String date, @NonNull String pattern) {
        LocalDate localDate = parseLocalDate(date, pattern);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
    
    /**
     * parse no hour/minute/second date
     * @param date
     * @param pattern {@link DateUtils.Pattern.Date}
     * @return
     */
    public static LocalDate parseLocalDate(@NonNull String date, @NonNull String pattern) {
        if(StringUtils.isBlank(date)) {
            throw new NullPointerException("date is null");
        }
        if(StringUtils.isBlank(pattern)) {
            throw new NullPointerException("pattern is null");
        }
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDate.parse(date, formatter);
    }
    
    /**
     * parse have hour/minute/second date
     * @param dateTime
     * @param pattern {@link DateUtils.Pattern.DateTime}
     * @return
     */
    public static LocalDateTime parseLocalDateTime(@NonNull String dateTime, @NonNull String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDateTime.parse(dateTime, formatter);
    }
    
    /**
     * parse have hour/minute/second date
     * @param dateTime
     * @param pattern {@link DateUtils.Pattern.Date}
     * @return
     */
    public static Date parseDateTime(@NonNull String dateTime, @NonNull String pattern) {
        if(StringUtils.isBlank(dateTime)) {
            throw new NullPointerException("date is null");
        }
        if(StringUtils.isBlank(pattern)) {
            throw new NullPointerException("pattern is null");
        }
        LocalDateTime localDateTime = parseLocalDateTime(dateTime, pattern);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 
     * @param date
     * @param addValue
     * @param unit
     * @return
     */
    public static Date dateAdd(@NonNull Date date, int addValue, TimeUnit unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (TimeUnit.DAYS.equals(unit)) {
            cal.add(Calendar.DAY_OF_YEAR, addValue);
        } else if (TimeUnit.HOURS.equals(unit)) {
            cal.add(Calendar.HOUR_OF_DAY, addValue);
        } else if (TimeUnit.MINUTES.equals(unit)) {
            cal.add(Calendar.MINUTE, addValue);
        } else if (TimeUnit.SECONDS.equals(unit)) {
            cal.add(Calendar.SECOND, addValue);
        } else {
            throw new RuntimeException("timeunit can't support");
        }
        return cal.getTime();
    }

    /**
     * 
     * @param begin
     * @param end
     * @return
     */
    public static Period calculationPeriod(@NonNull Date begin, Date end) {
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(begin);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);

        LocalDate beginLocal =
            LocalDate.of(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DAY_OF_YEAR));
        LocalDate endLocal =
            LocalDate.of(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DAY_OF_YEAR));

        return Period.between(beginLocal, endLocal);
    }

    private static DateTimeFormatter createCacheFormatter(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        
        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if(null == formatter) {
            formatter = DateTimeFormatter.ofPattern(pattern);
            FORMATTER_CACHE.put(pattern, formatter);
        }

        return formatter;
    }
}
