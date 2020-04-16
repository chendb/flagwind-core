package com.flagwind.commons;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 参照Monment.js实现的日期类型
 */
public class Monment extends java.util.Date {

    private static final long serialVersionUID = -5157189375568030873L;

    // region 构造函数

    /**
     * 功能：用java.util.Date进行构造。
     *
     * @param date
     */
    public Monment(java.util.Date date) {
        super(date.getTime());
    }

    public Monment(Timestamp date) {
        super(date.getTime());
    }

    public Monment(LocalDateTime localDateTime) {
        super(localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
    }

    /**
     * 功能：用毫秒进行构造。
     *
     * @param timeInMillis
     */
    public Monment(long timeInMillis) {
        super(timeInMillis);
    }

    /**
     * 功能：默认构造函数。
     */
    public Monment() {
        super();
    }

    public Monment(String dateStr, String format) {
        super(Monment.parseDate(dateStr, format).getTime());
    }

    // endregion

    // region 时间片段枚举

    public static enum DatePart {
        Years, Months, Weeks, Days, Hours, Minutes, Seconds, Milliseconds
    }

    // endregion

    /**
     * 功能：转换为Calendar。
     * <p>
     * author：沙琪玛 QQ：862990787 Aug 21, 2013 8:58:31 AM
     *
     * @return Calendar
     */
    public Calendar toCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        return c;
    }

    // region 年、月、日、时、分钞赋值

    /**
     * 功能：将当前日期的秒数进行重新设置。
     * <p>
     * author：chendb
     *
     * @param second 秒数
     * @return 设置后的日期
     */
    public Monment second(int second) {
        long ms = toLocalDateTime().withSecond(second).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.SECOND, second);
//        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：将当前日期的秒数进行重新设置。
     * <p>
     * author：chendb
     *
     * @param millisecond 秒数
     * @return 设置后的日期
     */
    public Monment millisecond(int millisecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.MILLISECOND, millisecond);
        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：将当前日期的分钟进行重新设置。
     * <p>
     * author：chendb
     *
     * @param minute 分钟数
     * @return 设置后的日期
     */
    public Monment minute(int minute) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.MINUTE, minute);
//        return new Monment(c.getTimeInMillis());
        long ms = toLocalDateTime().withMinute(minute).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
    }

    /**
     * 功能：将当前日期的小时进行重新设置。
     * <p>
     * author：chendb
     *
     * @param hour 小时数 (24小时制)
     * @return 设置后的日期
     */
    public Monment hour(int hour) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.HOUR_OF_DAY, hour);
//        return new Monment(c.getTimeInMillis());
        long ms = toLocalDateTime().withHour(hour).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
    }


    /**
     * 功能：将当前日期的天进行重新设置。
     * <p>
     * author：chendb
     *
     * @param day 某一天
     * @return 设置后的日期
     */
    public Monment day(int day) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.DATE, day);
//        return new Monment(c.getTimeInMillis());
        long ms = toLocalDateTime().withDayOfMonth(day).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
    }

    /**
     * 功能：将当前日期的月进行重新设置。
     * <p>
     * author：chendb
     *
     * @param month 某一月
     * @return 设置后的日期
     */
    public Monment month(int month) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.MONTH, month - 1);
//        return new Monment(c.getTimeInMillis());
        long ms = toLocalDateTime().withMonth(month).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
    }

    /**
     * 功能：将当前日期的年进行重新设置。
     * <p>
     * author：chendb
     *
     * @param year 某一年
     * @return 设置后的日期
     */
    public Monment year(int year) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.YEAR, year);
//        return new Monment(c.getTimeInMillis());
        long ms = toLocalDateTime().withYear(year).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
    }

    // endregion

    /**
     * 功能：得到当月有多少天。
     * <p>
     * author：chendb
     *
     * @return int
     */
    public int dayOfMonth() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(this);
//        return cal.getActualMaximum(Calendar.DATE);
        return toLocalDateTime().getDayOfMonth();

    }

    // region 字符串转时间

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
     * <p>
     * author：chendb
     *
     * @param dateStr    时间字符串
     * @param dataFormat 当前时间字符串的格式。
     * @return net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Monment parseDate(String dateStr, SimpleDateFormat dataFormat) {
        try {
            java.util.Date d = dataFormat.parse(dateStr);
            return new Monment(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
     * <p>
     * author：chendb
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @param format  时间格式如：yyyy-MM-dd HH:mm:ss
     * @return net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Monment parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return parseDate(dateStr, sdf);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
     * <p>
     * author：chendb
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Monment parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }
    // endregion

    /**
     * 该月的天数
     * <p>
     * author：chendb
     *
     * @return int
     */
    public int daysInMonth() {
        Calendar c = this.toCalendar();
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当时对象时间是否在指定时间之后
     *
     * @param date 要比较的时间
     * @return boolean
     */
    public boolean isAfter(Monment date) {

        return this.getTime() - date.getTime() > 0;
    }

    /**
     * 当时对象时间是否在指定时间之前
     *
     * @param date 要比较的时间
     * @return boolean
     */
    public boolean isBefore(Monment date) {
        return this.getTime() - date.getTime() < 0;
    }

    /**
     * 是否在指定的区间之意
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return boolean
     */
    public boolean isBetween(Monment start, Monment end) {
        return this.getTime() >= start.getTime() && this.getTime() <= end.getTime();
    }

    // region 类型判断

    /**
     * 是否是Moment对象
     *
     * @param date
     * @return boolean
     */
    public boolean isMoment(Date date) {
        return date instanceof Monment;
    }

    /**
     * 是否为Date对象
     *
     * @param date
     * @return boolean
     */
    public boolean isDate(Date date) {
        return !(date instanceof Monment);
    }

    // endregion

    // region 开始与结束时间

    /**
     * 开始时间
     *
     * @param part
     * @return Monment
     */
    public Monment startOf(DatePart part) {
        switch (part) {
            case Years:
                return this.month(1).day(1).hour(0).minute(0).second(0).millisecond(0);
            case Months:
                return this.day(1).hour(0).minute(0).second(0).millisecond(0);
            case Weeks:
                return this.weekStart();
            case Days:
                return this.hour(0).minute(0).second(0).millisecond(0);
            case Hours:
                return this.minute(0).second(0).millisecond(0);
            case Minutes:
                return this.second(0).millisecond(0);
            case Seconds:
                return this.millisecond(0);
            default:
                return this;
        }
    }

    /**
     * 结束时间
     *
     * @param part
     * @return Monment
     */
    public Monment endOf(DatePart part) {
        switch (part) {
            case Years:
                return this.month(1).day(1).hour(0).minute(0).second(0).millisecond(0).addYears(1).addMilliseconds(-1);
            case Months:
                return this.day(1).hour(0).minute(0).second(0).millisecond(0).addMonths(1).addMilliseconds(-1);
            case Weeks:
                return this.weekEnd();
            case Days:
                return this.hour(0).minute(0).second(0).millisecond(0).addDays(1).addMilliseconds(-1);
            case Hours:
                return this.minute(0).second(0).millisecond(0).addHours(1).addMilliseconds(-1);
            case Minutes:
                return this.second(0).millisecond(0).addMinutes(1).addMilliseconds(-1);
            case Seconds:
                return this.millisecond(0).addSeconds(1).addMilliseconds(-1);
            default:
                return this;
        }
    }

    // endregion

    // region 时间差操作

    /**
     * 功能：计算两个时间的时间差。
     * <p>
     * author：chendb
     *
     * @param time 另一个时间。
     * @return TimeSpan 时间间隔
     */
    public TimeSpan diff(Monment time) {
        return new TimeSpan(Math.abs(this.getTime() - time.getTime()));
    }

    /**
     * 取得两个时间的时间差的指定时段类型的数值
     * 如：m1:2018-05-31 08：30：30, m2:2018-05-31 08：25:10
     * m1.diff(m2,DatePart.Hours) 为 0
     * m1.diff(m2,DatePart.Minutes) 为 5
     * m1.diff(m2,DatePart.Seconds) 为 20
     */
    @Deprecated
    public long diff(Monment time, DatePart part) {
        TimeSpan ts = new TimeSpan(this.getTime() - time.getTime());
        if (part == null) {
            return ts.totalMillisecond();
        }

        switch (part) {
            case Years:
                return ts.getYears();
            case Months:
                return ts.getMonths();
            case Days:
                return ts.getDays();
            case Hours:
                return ts.getHours();
            case Minutes:
                return ts.getMinutes();
            case Seconds:
                return ts.getSeconds();
            case Milliseconds:
                return ts.getMillisecond();
            default:
                return ts.totalMillisecond();
        }
    }

    // endregion

    // region 时间增加、减法操作

    /**
     * 时间加法
     *
     * @param num  数据
     * @param part 时间片段
     * @return Monment
     */
    public Monment substract(int num, DatePart part) {
        return this.add(-num, part);
    }

    /**
     * 时间加法
     *
     * @param num  数据
     * @param part 时间片段
     * @return Monment
     */
    public Monment add(int num, DatePart part) {
        switch (part) {
            case Years:
                return this.addYears(num);
            case Months:
                return this.addMonths(num);
            case Days:
                return this.addDays(num);
            case Hours:
                return this.addHours(num);
            case Minutes:
                return this.addMinutes(num);
            case Seconds:
                return this.addSeconds(num);
            case Milliseconds:
                return this.addMilliseconds(num);
            default:
                return this.addDays(num);
        }
    }

    /**
     * 功能：当前时间增加毫秒数。
     * <p>
     * author：chendb
     *
     * @param milliseconds 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addMilliseconds(int milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND) + milliseconds);
        this.setTime(c.getTimeInMillis());
        return this;
    }

    /**
     * 功能：当前时间增加秒数。
     * <p>
     * author：chendb
     *
     * @param seconds 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addSeconds(int seconds) {
        long ms = toLocalDateTime().plusSeconds(seconds).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
//        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加分钟数。
     * <p>
     * author：chendb
     *
     * @param minutes 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addMinutes(int minutes) {
        long ms = toLocalDateTime().plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;

//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
//        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加小时数。
     * <p>
     * author：chendb
     *
     * @param hours 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addHours(int hours) {
        long ms = toLocalDateTime().plusHours(hours).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
//        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加天数。
     * <p>
     * author：chendb
     *
     * @param days 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addDays(int days) {
        long ms = toLocalDateTime().plusDays(days).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
//        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加月数。
     * <p>
     * author：chendb
     *
     * @param months 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addMonths(int months) {
        long ms = toLocalDateTime().plusMonths(months).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
//        return new Monment(c.getTimeInMillis());
    }

    /**
     * 功能：当前时间增加年数。注意遇到2月29日情况，系统会自动延后或者减少一天。
     * <p>
     * author：chendb
     *
     * @param years 正值时时间延后，负值时时间提前。
     * @return Monment
     */
    public Monment addYears(int years) {
        long ms = toLocalDateTime().plusYears(years).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.setTime(ms);
        return this;
//        Calendar c = Calendar.getInstance();
//        c.setTime(this);
//        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
//        return new Monment(c.getTimeInMillis());
    }

    // endregion

    // region 取值操作

    /**
     * 得到秒。格式：56<br/>
     *
     * @return int
     */
    public int seconds() {
        return Integer.parseInt(toString("ss"));
    }

    /**
     * 得到分钟。格式：56<br/>
     *
     * @return int
     */
    public int minutes() {
        return Integer.parseInt(toString("mm"));
    }

    /**
     * 得到小时。格式：23<br/>
     *
     * @return int
     */
    public int hours() {
        return Integer.parseInt(toString("HH"));
    }

    /**
     * 得到日。格式：26<br/>
     * 注意：这里1日返回1,2日返回2。
     *
     * @return int
     */
    public int days() {
        return Integer.parseInt(toString("dd"));
    }

    /**
     * 得到月。格式：5<br/>
     * 注意：这里1月返回1,2月返回2。
     *
     * @return int
     */
    public int months() {
        return Integer.parseInt(toString("MM"));
    }

    /**
     * 得到年。格式：2013
     *
     * @return int
     */
    public int years() {
        return Integer.parseInt(toString("yyyy"));
    }

    /**
     * 得到短时间。格式：12:01
     *
     * @return String
     */
    public String shortTime() {
        return toString("HH:mm");
    }

    /**
     * 得到长时间。格式：12:01:01
     *
     * @return String
     */
    public String longTime() {
        return toString("HH:mm:ss");
    }

    // endregion

    /**
     * 得到今天的第一秒的时间。
     * <p>
     * author：chendb
     *
     * @return Date
     */
    public Monment dayStart() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
//        return new Monment(c.getTimeInMillis());
        this.setTime(c.getTimeInMillis());
        return this;
    }

    /**
     * 本周的开始时间
     */
    public Monment weekStart() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        int day_of_week = this.dayOfWeekInt();
        c.add(Calendar.DATE, -day_of_week + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        //return new Monment(c.getTimeInMillis());
        this.setTime(c.getTimeInMillis());
        return this;
    }

    /**
     * 本周的结束时间
     */
    public Monment weekEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        int day_of_week = this.dayOfWeekInt();
        c.add(Calendar.DATE, -day_of_week + 7);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        // return new Monment(c.getTimeInMillis());
        this.setTime(c.getTimeInMillis());
        return this;
    }

    /**
     * 得到当前所在自然月的第一天的开始,格式为长日期格式。例如：2012-03-01 00:00:00。
     *
     * @return Date
     */
    public Monment monthStart() {
        Calendar c = Calendar.getInstance();
        String startStr = toString("yyyy-M-") + c.getActualMinimum(Calendar.DATE) + " 00:00:00";
//        return Monment.parseDate(startStr);
        this.setTime(Monment.parseDate(startStr).getTime());
        return this;
    }

    /**
     * 得到今天的最后一秒的时间。
     *
     * @return Date
     */
    public Monment dayEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        // return new Monment(c.getTimeInMillis());
        this.setTime(c.getTimeInMillis());
        return this;
    }

    /**
     * 根据日期得到星期几,得到数字。<br/>
     * 7, 1, 2, 3, 4, 5, 6
     *
     * @return Integer 如：6
     */
    public int dayOfWeekInt() {
        Integer dayNames[] = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        }
        return dayNames[dayOfWeek];
    }

    /**
     * 获取一年的第几周
     */
    public int weekOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 将日期转换成长日期字符串 例如：2009-09-09 01:01:01
     *
     * @return String
     */
    public String toLongDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(this);
    }

    /**
     * 将日期按照一定的格式进行格式化为字符串。<br/>
     * 例如想将时间格式化为2012-03-05 12:56 ,则只需要传入formate为yyyy-MM-dd HH:mm即可。
     *
     * @param format 格式化格式，如：yyyy-MM-dd HH:mm
     * @return String 格式后的日期字符串。如果当前对象为null，则直接返回null。
     */
    public String toString(String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(this);
    }

    /**
     * 得到某个时间的时间戳yyyyMMddHHmmss。
     *
     * @return String 如果当前对象为null，则直接返回null。
     */
    public String toTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(this);
    }

    /**
     * 将日期转换成短日期字符串,例如：2009-09-09。
     *
     * @return String ,如果当前对象为null，返回null。
     */
    public String toShortDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault());
    }


    public Monment clone() {
        return new Monment(this.getTime());
    }

    /***
     * 静态构建方法
     * @return Monment实例
     */
    public static Monment build() {
        return new Monment();
    }

    public static Monment now() {
        return new Monment(System.currentTimeMillis());
    }


    public static Monment ofLocalDateTime(LocalDateTime localDateTime) {
        Monment date = new Monment(localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        return date;
    }

    public static void main(String[] args) {
        Monment monment = Monment.parseDate("2019-03-30", "yyyy-MM-dd").addMonths(-1);
        System.out.println("2019-03-30 减一个月：" + monment.toString("yyyy-MM-dd HH:mm:ss"));
        Monment start = new Monment(System.currentTimeMillis() - 10000);
        Monment current = new Monment();
        Monment end = new Monment(System.currentTimeMillis() + 100000);
        System.out.println("start:" + start.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("end:" + end.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("current:" + current.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("week of year:" + current.weekOfYear());
        System.out.println("week start:" + current.weekStart().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("week end:" + current.weekEnd().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("start:" + start.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("current:" + current.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("end:" + end.toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println("end-start Minutes:" + end.diff(start, DatePart.Minutes));
        System.out.println("end-start Seconds:" + end.diff(start, DatePart.Seconds));
        System.out.println("start is after end:" + Boolean.toString(start.isAfter(end)));
        System.out.println("start is before end:" + Boolean.toString(start.isBefore(end)));
        System.out.println("current is between start and end:" + Boolean.toString(current.isBetween(start, end)));
    }

}
