package com.flagwind.commons;

/**
 * 时间戳
 * 
 * author：chendb hbchendb1985@hotmail date：2015年9月25日 下午5:58:38
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

        long ms = totalMilliSeconds;
        {
            long yearMS = 1000 * 60 * 60 * 24 * 365L;
            if (ms >= yearMS) {
                this.years = Math.round(totalMilliSeconds / yearMS);
                ms = ms % yearMS;
            }
        }
        {
            long monthMS = 1000 * 60 * 60 * 24 * 30L;
            if (ms >= monthMS) {
                this.months = Math.round(ms / monthMS);
                ms = ms % monthMS;
            }
        }
        {
            long dayMS = 1000 * 60 * 60 * 24L;
            if (ms >= dayMS) {
                this.days = Math.round(ms / dayMS);
                ms = ms % dayMS;
            }
        }
        {
            long hourMS = 1000 * 60 * 60L;
            if (ms >= hourMS) {
                this.hours = Math.round(ms / hourMS);
                ms = ms % hourMS;
            }
        }
        {
            long minuteMS = 1000 * 60L;
            if (ms >= minuteMS) {
                this.minutes = Math.round(ms / minuteMS);
                ms = ms % minuteMS;
            }
        }
        {
            long secondMS = 1000L;
            if (ms >= secondMS) {
                this.minutes = Math.round(ms / secondMS);
                ms = ms % secondMS;
            }
        }

        {

            this.millisecond = Math.round(ms / 1000);

        }
        // this.days = Math.round(totalMilliSeconds * 1.0f / (1000 * 60 * 60 * 24));
        // this.hours = Math.round((totalMilliSeconds * 1.0f / (1000 * 60 * 60)) % 60);
        // this.minutes = Math.round((totalMilliSeconds * 1.0f / (1000 * 60)) % 60);
        // this.seconds = Math.round((totalMilliSeconds * 1.0f / (1000)) % 60);
        // this.millisecond = Math.round(totalMilliSeconds * 1.0f % (1000));
    }

    /**
     * 构造函数
     * 
     * @param d  天
     * @param h  时
     * @param m  分
     * @param s  钞
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
     * @return 是否等于 author：chendb date：2016年12月9日 上午12:11:25
     */
    public Boolean equals(TimeSpan ts) {
        return this.totalMillisecond() == ts.totalMillisecond();
    }

    /**
     * 增加
     * 
     * @param ts 时间间隔
     * @return 时间间隔 author：chendb date：2016年12月9日 上午12:12:12
     */
    public TimeSpan add(TimeSpan ts) {
        long ms = this.totalMillisecond() + ts.totalMillisecond();
        return new TimeSpan(ms);
    }

    /**
     * 相减
     * 
     * @param ts 时间间隔
     * @return 时间间隔 author：chendb date：2016年12月9日 上午12:12:35
     */
    public TimeSpan subtract(TimeSpan ts) {
        long ms1 = this.totalMillisecond();

        long ms2 = ts.totalMillisecond();
        if (ms2 > ms1) {
            throw new RuntimeException("不能减一个大小自身的时间间隔");
        }
        return new TimeSpan(ms1 - ms2);
    }

    public long totalDays() {
        return (this.getYears() * 365 + this.getMonths() * 30 + this.getDays());
    }

    /**
     * 总小时数
     * 
     * @return 总小时数 author：chendb date：2016年12月9日 上午12:12:52
     */
    public long totalHours() {
        return (this.totalDays() * 24) + this.getHours();
    }

    /**
     * 总分钟数
     * 
     * @return 总分钟数 author：chendb date：2016年12月9日 上午12:13:10
     */
    public long totalMinutes() {
        return (this.totalHours() * 60) + this.getMinutes();
    }

    /**
     * 总的钞数
     * 
     * @return 总的钞数 author：chendb date：2016年12月9日 上午12:13:32
     */
    public long totalSeconds() {
        return (this.totalMinutes() * 60) + this.getSeconds();
    }

    /**
     * 总的ms数
     * 
     * @return 总的ms数 author：chendb date：2016年12月9日 上午12:17:22
     */
    public long totalMillisecond() {
        return this.totalSeconds() * 1000 + this.getMillisecond();
    }

    @Override
    public String toString() {
        if (this.totalDays() != 0) {
            return String.format("%d:%02d:%02d:%02d.%02d", this.totalDays(), this.getHours(), this.getMinutes(),
                    this.getSeconds(), this.getMillisecond());
        } else {
            return String.format("%02d:%02d:%02d.%02d", this.getHours(), this.getMinutes(), this.getSeconds(),
                    this.getMillisecond());
        }
    }

}
