package com.flagwind.application;


/**
 * 工作台
 */
public interface Workbench {
//    /**
//     * 当工作台正在打开时产生的事件。
//     * @event EventArgs
//     */
//    String OPENING = null;
//
//    /**
//     * 当工作台被打开后产生的事件。
//     * @event EventArgs
//     */
//    String OPENED = null;
//
//    /**
//     * 当工作台正在取消激活时产生的事件。
//     * @event EventArgs
//     */
//    String DEACTIVATING = null;
//
//    /**
//     * 当工作台取消激活后产生的事件。
//     * @event EventArgs
//     */
//    String DEACTIVATED = null;
//
//    /**
//     * 当工作台正在激活时产生的事件。
//     * @event EventArgs
//     */
//    String ACTIVATING= null;
//
//    /**
//     * 当工作台正在关闭时产生的事件。
//     * @event CancelEventArgs
//     */
//    String CLOSING = null;
//
//    /**
//     * 当工作台被关闭后产生的事件。
//     * @event EventArgs
//     */
//    String CLOSED= null;
//
//    /**
//     * 当工作台标题被更改后产生的事件。
//     * @event EventArgs
//     */
//    String TITLE_CHANGED= null;

    /**
     * 获取工作台状态。
     * @property
     */
    WorkbenchStatus  getStatus();

    /**
     * 获取工作台标题。
     * @property
     */
   String getTitle();

    /**
     * 设置工作台标题。
     * @property
     */
   void setTitle(String title);

    /**
     * 打开工作台。
     * @param  {Array<string>} args
     * @returns void
     */
    void open(String...args);

    /**
     * 关闭工作台。
     * @async
     * @returns boolean
     */
    boolean close();

    /**
     * 取消激活工作台。
     * @returns void
     */
    void deactivate();

    /**
     * 激活工作台。
     * @returns void
     */
    void activate() ;
}
