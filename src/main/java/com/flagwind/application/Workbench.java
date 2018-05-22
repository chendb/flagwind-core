package com.flagwind.application;


/**
 * 工作台
 */
public interface Workbench {

    /**
     * 获取工作台状态。
     * 
     */
    WorkbenchStatus  getStatus();

    /**
     * 获取工作台标题。
     * 
     */
   String getTitle();

    /**
     * 设置工作台标题。
     * 
     */
   void setTitle(String title);

    /**
     * 打开工作台。
     * @param  args
     * 
     */
    void open(String...args);

    /**
     * 关闭工作台。
     * @return boolean
     */
    boolean close();

    /**
     * 取消激活工作台。
     * 
     */
    void deactivate();

    /**
     * 激活工作台。
     * 
     */
    void activate() ;
}
