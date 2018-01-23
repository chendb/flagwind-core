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
     * 
     */
    void open(String...args);

    /**
     * 关闭工作台。
     * @async
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
