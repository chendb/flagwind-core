package com.flagwind.events;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * author：hbche
 */
public class EventProvider<T extends EventArgs> {

    private Object source;                                               // 事件源
    private ConcurrentMap<String, List<EventEntry<T>>> events;           // 事件监听器字典


    public EventProvider(Object source) {
        this.source = source == null ? this : source;
        this.events = new ConcurrentHashMap<>();
    }

    /**
     * 为指定的事件类型注册一个侦听器，以使侦听器能够接收事件通知。
     * summary: 如果不再需要某个事件侦听器，可调用 removeListener() 删除它，否则会产生内存问题。
     * 由于垃圾回收器不会删除仍包含引用的对象，因此不会从内存中自动删除使用已注册事件侦听器的对象。
     * @param  type 事件类型。
     * @param  listener 处理事件的侦听器函数。
     * @param  scope 侦听函数绑定的 this 对象。
     * @param  once 是否添加仅回调一次的事件侦听器，如果此参数设为 true 则在第一次回调时就自动移除监听。
     * 
     */
    public void addListener(String type, Consumer<T> listener,Object scope,boolean once) {
        if (StringUtils.isEmpty(type) || listener == null) {
            throw new IllegalArgumentException();
        }

        List<EventEntry<T>> entries = this.events.get(type);

        if (entries == null) {
            entries = new ArrayList<>();

            this.events.put(type, entries);
        }

        for (EventEntry<T> entry : entries) {
            // 防止添加重复的侦听函数
            if (entry.getListener().equals(listener) && entry.getScope().equals(scope)) {
                return;
            }
        }

        entries.add(new EventEntry<T>(type, listener, scope, once));
    }

    /**
     * 移除侦听器。如果没有注册任何匹配的侦听器，则对此方法的调用没有任何效果。
     * @param  type 事件类型。
     * @param listener 处理事件的侦听器函数。
     * @param  scope 侦听函数绑定的 this 对象。
     * 
     */
    public void removeListener(String type,Consumer<T> listener,Object scope)
    {
        if(StringUtils.isEmpty(type) || listener==null)
        {
            throw new IllegalArgumentException();
        }

        List<EventEntry<T>> entries = this.events.get(type);

        if(entries==null||entries.size()==0)
        {
            return;
        }

        for(int i = 0, len = entries.size(); i < len; i++)
        {
            EventEntry<T> entry = entries.get(i);

            if(entry.getListener().equals(listener)&& entry.getScope().equals(scope))
            {
                entries.remove(entry);
                break;
            }
        }

        // 如果事件项为空，则需要释放资源
        if(entries.size()==0)
        {
            this.events.remove(type);
        }
    }

    /**
     * 检查是否为特定事件类型注册了侦听器。
     * @param  type 事件类型。
     * @return boolean 如果指定类型的侦听器已注册，则值为 true；否则，值为 false。
     */
    public boolean hasListener(String type) {
        List<EventEntry<T>> entries = this.events.get(type);

        return entries != null && entries.size() > 0;
    }

    // /**
    //  * 派发一个指定类型的事件。
    //  * @param  type 事件类型。
    //  * @param  data? 事件数据。
    //  * 
    //  */
    // public void dispatchEvent(String type,Object data) {
    //     // 参数匹配: type: string, data: any
    //     EventArgs args = new EventArgs(type, data);
    //     this.dispatchEvent(args);
    // }

    /**
     * 派发一个指定参数的事件。
     * @param  eventArgs 事件参数实例。
     * 
     */
    public void dispatchEvent(T eventArgs ) {
        // 设置事件源
        eventArgs.setSource(this.source);

        // 根据事件类型获取所有事件项
        List<EventEntry<T>> entries = this.events.get(eventArgs.getType());

        if (entries == null || entries.size() == 0) {
            return;
        }

        // 临时数组用于保存只回掉一次的事件项
        PriorityQueue<EventEntry<T>> onces = new PriorityQueue<>();

        for (EventEntry<T> entry : entries) {
            entry.getListener().accept(eventArgs);
            if (entry.once) {
                onces.add(entry);
            }
        }

        // 清除所有只回调一次的事件项
        while (onces.size() > 0) {
            EventEntry<T> entry = onces.poll();

            this.removeListener(entry.getType(), entry.getListener(), entry.getScope());
        }
    }

}
