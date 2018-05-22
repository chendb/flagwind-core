package com.flagwind.services;

import com.flagwind.events.EventArgs;

public class WorkerStateChangedEventArgs  extends EventArgs
{
    /**
     * 操作名称。
     * 
     */
    public String actionName ;

    /**
     * 发生改变的状态。
     * 
     */
    public WorkerState state ;

    /**
     * 表示在发生状态改变时产生的异常。
     * 
     * 
     */
    public Exception error ;

    /**
     * 初始化 WorkerStateChangedEventArgs 类的新实例。
     * @param type 事件类型。
     * @param actionName 操作名称。
     * @param state 发生改变的状态。
     * @param error 发生状态改变时产生的异常。
     */
    public WorkerStateChangedEventArgs(String type, String actionName, WorkerState state, Exception error) {
        super(type, null);
        this.actionName = actionName;
        this.state = state;
        this.error = error;
    }

    public String getActionName() {
        return actionName;
    }

    public WorkerState getState() {
        return state;
    }

    public Exception getError() {
        return error;
    }
}
