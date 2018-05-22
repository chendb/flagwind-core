package com.flagwind.commands;

/**
 * 泛型命令接口
 * 
 * author：chendb
 * date：2016年12月9日 上午9:29:56
 * @param <T>
 */
public interface Command<T> {

    /**
     * 命令名
     * 
     * @return 名称
     * author：chendb
     * date：2016年12月9日 上午10:28:02
     */
    String getName();

    /**
     * 获取启动属性
     * 
     * @return 返回执行结果
     * author：chendb
     * date：2016年12月9日 上午10:29:20
     */
    boolean getEnabled();

    /**
     * 设置启用属性
     * 
     * @param enabled 是否启用
     * author：chendb
     * date：2016年12月9日 上午10:29:34
     */
    void setEnabled(boolean enabled);

    /**
     * 判断当前命令能否依据给定的选项和参数执行。
     * 
     * @param parameter 判断命令能否执行的参数对象
     * @return 返回能否执行的结果
     * author：chendb
     * date：2016年12月9日 上午10:26:18
     */
    boolean canExecute(T parameter);

    /**
     * 执行命令 对实现着的要求：应该在本方法的实现中首先调用canExecute方法，以确保阻止非法的调用。
     * 
     * @param parameter 执行命令的参数对象
     * @return 返回执行的返回结果
     * author：chendb
     * date：2016年12月9日 上午10:26:55
     */
    Object execute(T parameter);
}
