package com.flagwind.services;



public interface Worker {
    /**
     * 获取当前工作器的名称。
     * @return
     */
    String getName();


    /**
     * 获取当前工作器的状态
     * @return
     */
    WorkerState getState();


    /**
     * 获取是否禁用工作器
     * @return
     */
    boolean getDisabled();

    /**
     * 设置是否禁用工作器
     * @param value
     */
    void setDisabled(boolean value);


    /**
     * 获取工作器是否允许暂停和继续
     * @return
     */
    boolean canPauseAndContinue();



    /**
     * 启动工作器
     * @param args 启动的参数
     */
    void start(String... args);


    /**
     * 停止工作器
     * @param args 停止的参数
     */
    void stop(String... args);


    /**
     * 暂停工作器
     */
    void pause();

    /**
     * 恢复工作器，继续运行。
     */
    void resume();
}
