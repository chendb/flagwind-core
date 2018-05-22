package com.flagwind.commons;

/**
 * 时间戳
 * 
 * author：chendb hbchendb1985@hotmail
 * date：2015年9月25日 下午5:58:38
 */
public class TimeSpan {

    // region 私有变量

    private int years;
    private int months;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private int millisecond;

    // endregion

    // region 属性
    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int d) {
        this.days = d;
    }

    public int getHours() {
        return this.hours;
    }

    public void setHours(int h) {
        this.hours = h;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int m) {
        this.minutes = m;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int s) {
        this.seconds = s;
    }

    public int getMillisecond() {
        return this.millisecond;
    }

    public void setMillisecond(int ms) {
        this.millisecond = ms;
    }
    // endregion

    // region 构造函数
    /**
     * 构造函数
     * 
     * @param totalMilliSeconds ms数
     */
    public TimeSpan(long totalMilliSeconds) {
        this.years = Math.round(totalMilliSeconds * 1.0f / (1000 * 60 * 60 * 24 * 365));
        this.months = Math.round(totalMilliSeconds * 1.0f / (1000 * 60 * 60 * 24 * 30));
        this.days = Math.round(totalMilliSeconds * 1.0f / (1000 * 60 * 60 * 24));
        this.hours = Math.round((totalMilliSeconds * 1.0f / (1000 * 60 * 60)) % 60);
        this.minutes = Math.round((totalMilliSeconds * 1.0f / (1000 * 60)) % 60);
        this.seconds = Math.round((totalMilliSeconds * 1.0f / (1000)) % 60);
        this.millisecond = Math.round(totalMilliSeconds * 1.0f % (1000));
    }

    /**
     * 构造函数
     * 
     * @param d 天
     * @param h 时
     * @param m 分
     * @param s 钞
     * @param ms 毫秒
     */
    public TimeSpan(int d, int h, int m, int s, int ms) {
        this.days = d;
        this.hours = h;
        this.minutes = m;
        this.seconds = s;
        this.millisecond = ms;
    }

    /**
     * 构造函数
     * 
     * @param d 天
     * @param h 时
     * @param m 分
     * @param s 钞
     */
    public TimeSpan(int d, int h, int m, int s) {
        this.days = d;
        this.hours = h;
        this.minutes = m;
        this.seconds = s;
    }

    /**
     * 构造函数
     * 
     * @param h 时
     * @param m 分
     * @param s 钞
     */
    public TimeSpan(int h, int m, int s) {
        this.days = 0;
        this.hours = h;
        this.minutes = m;
        this.seconds = s;
    }

    // endregion

    /**
     * 等于
     * 
     * @param ts 时间间隔
     * @return 是否等于
     * author：chendb
     * date：2016年12月9日 上午12:11:25
     */
    public Boolean equals(TimeSpan ts) {
        return this.getDays() == ts.getDays() && this.getHours() == ts.getHours()
                && this.getMinutes() == ts.getMinutes() && this.getSeconds() == ts.getSeconds();
    }

    /**
     * 增加
     * 
     * @param ts 时间间隔
     * @return 时间间隔
     * author：chendb
     * date：2016年12月9日 上午12:12:12
     */
    public TimeSpan add(TimeSpan ts) {
        int ms = this.getMillisecond() + ts.getMillisecond();
        int s = this.getSeconds() + ts.getSeconds();
        int m = this.getMinutes() + ts.getMinutes();
        int h = this.getHours() + ts.getHours();
        int d = this.getDays() + ts.getDays();

        if (ms > 999) {
            ms -= 1000;
            s += 1;
        }

        if (s > 59) {
            s -= 60;
            m += 1;
        }
        if (m > 59) {
            m -= 60;
            h += 1;
        }
        if (h > 23) {
            h -= 24;
            d += 1;
        }

        return new TimeSpan(d, h, m, s);

    }

    /**
     * 相减
     * 
     * @param ts 时间间隔
     * @return 时间间隔
     * author：chendb
     * date：2016年12月9日 上午12:12:35
     */
    public TimeSpan subtract(TimeSpan ts) {
        long ms1 = this.getMillisecond();
        long s1 = this.getSeconds() * 1000;
        long m1 = this.getMinutes() * 1000 * 60;
        long h1 = this.getHours() * 1000 * 60 * 60;
        long d1 = this.getDays() * 1L * 1000 * 24 * 60 * 60;

        long ms2 = ts.getMillisecond();
        long s2 = ts.getSeconds() * 1000L;
        long m2 = ts.getMinutes() * 1000 * 60L;
        long h2 = ts.getHours() * 1000 * 60 * 60L;
        long d2 = ts.getDays() * 1000 * 24 * 60 * 60L;

        long sd = (ms1 + s1 + m1 + h1 + d1) - (ms2 + s2 + m2 + h2 + d2);

        int d = Math.round(sd * 1.0f / (long) (24 * 60 * 60 * 100));
        sd -= (d * (24 * 60 * 60 * 1000L));
        int h = Math.round(sd * 1.0f / (int) (60 * 60 * 1000));
        sd -= (h * (60 * 60 * 1000));
        int m = Math.round(sd * 1.0f / 60 * 1000);
        sd -= (m * (60 * 1000));

        int s = Math.round(sd * 1.0f / 1000);

        sd -= (s * 1000);
        int ms = Math.round(sd * 1.0f);

        return new TimeSpan(d, h, m, s, ms);

    }

    /**
     * 总小时数
     * 
     * @return 总小时数
     * author：chendb
     * date：2016年12月9日 上午12:12:52
     */
    public long totalHours() {
        return (this.getDays() * 24) + this.getHours();
    }

    /**
     * 总分钟数
     * 
     * @return 总分钟数
     * author：chendb
     * date：2016年12月9日 上午12:13:10
     */
    public long totalMinutes() {
        return (((this.getDays() * 24) + this.getHours()) * 60) + this.getMinutes();
    }

    /**
     * 总的钞数
     * 
     * @return 总的钞数
     * author：chendb
     * date：2016年12月9日 上午12:13:32
     */
    public long totalSeconds() {
        return (((((this.getDays() * 24) + this.getHours()) * 60) + this.getMinutes()) * 60) + this.getSeconds();
    }

    /**
     * 总的ms数
     * 
     * @return 总的ms数
     * author：chendb
     * date：2016年12月9日 上午12:17:22
     */
    public long totalMillisecond() {
        return ((((((this.getDays() * 24) + this.getHours()) * 60) + this.getMinutes()) * 60) + this.getSeconds())
                * 1000 + this.getMillisecond();
    }

    @Override
    public String toString() {
        if (this.getDays() != 0) {
            return String.format("%d:%02d:%02d:%02d.%02d", this.getDays(), this.getHours(), this.getMinutes(),
                    this.getSeconds(), this.getMillisecond());
        } else {
            return String.format("%02d:%02d:%02d.%02d", this.getHours(), this.getMinutes(), this.getSeconds(),
                    this.getMillisecond());
        }
    }

}
