package com.flagwind.application;

public enum WorkbenchStatus {
    /**
     * 未开始或已关闭。
     *
     * 
     */
    Closed,

    /**
     * 正在打开中。
     *
     * 
     */
    Opening,

    /**
     * 正常运行。
     *
     * 
     */
    Running,

    /**
     * 取消激活中。
     *
     * 
     */
    Deactivating,

    /**
     * 已被取消激活。
     *
     * 
     */
    Deactivated,

    /**
     * 正在激活中。
     *
     * 
     */
    Activating,

    /**
     * 正在关闭中。
     *
     * 
     */
    Closing
}
