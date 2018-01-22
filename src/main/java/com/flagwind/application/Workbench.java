package com.flagwind.application;


/**
 * 工作台
 */
public interface Workbench {

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
