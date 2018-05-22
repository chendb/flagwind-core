package com.flagwind.events;

import org.apache.commons.lang3.StringUtils;

/**
 * 事件参数
 * 
 * author：chendb
 * date：2015年10月5日 下午4:33:47
 */
public class EventArgs {

    /**
     * 空参数
     */
    public static EventArgs EMPTY;

    private String type;                      // 事件类型
    private Object source;                    // 事件源
    private Object data;                      // 事件关联的数据



    static {
        EMPTY = new EventArgs();
    }

    /**
     * 构造函数
     */
    public EventArgs() {

    }

    /**
     * 初始化 EventArgs 类的新实例。
     * @param  type 事件类型。
     * @param  data 可选数据。
     */
    public EventArgs(String type, Object data) {
        if(StringUtils.isEmpty(type))
        {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
