package com.flagwind.application;

public enum WorkbenchStatus {
    /**
     * 未开始或已关闭。
     *
     * @member
     */
    Closed,

    /**
     * 正在打开中。
     *
     * @member
     */
    Opening,

    /**
     * 正常运行。
     *
     * @member
     */
    Running,

    /**
     * 取消激活中。
     *
     * @member
     */
    Deactivating,

    /**
     * 已被取消激活。
     *
     * @member
     */
    Deactivated,

    /**
     * 正在激活中。
     *
     * @member
     */
    Activating,

    /**
     * 正在关闭中。
     *
     * @member
     */
    Closing
}
