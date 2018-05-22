package com.flagwind.commands;

import java.util.Optional;

import com.flagwind.services.Predication;

/**
 * 泛型命令基类
 * 
 * author：chendb
 * date：2016年12月9日 上午9:30:29
 * @param <TContext>
 */
public abstract class CommandBase<TContext extends CommandContext> implements Command<TContext>, Predication<TContext> {

    // region 私有变量
    private boolean enabled;
    private Predication<TContext> predication;
    private String name;
    // endregion

    // region 公共属性

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        Optional<String> n = Optional.of(name);
        this.name = n.get();
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Predication<TContext> getPredication() {
        return predication;
    }

    public void setPredication(Predication<TContext> predication) {
        this.predication = predication;
    }

    @Override
    public boolean canExecute(TContext parameter) {
        // 如果断言对象是空则返回是否可用变量的值
        if (predication == null) {
            return enabled;
        }

        // 返回断言对象的断言测试的值
        return enabled && predication.predicate(parameter);
    }

    // }}

    // region 外部方法

    @Override
    public final Object execute(TContext parameter) {
        // 在执行之前首先判断是否可以执行
        if (!this.canExecute(parameter)) {
            return null;
        }

        Object result = null;

        // 执行具体的工作
        result = this.onExecute(parameter);

        // 返回执行成功的结果
        return result;
    }

    /**
     * 要执行的操作
     * 
     * @param parameter 执行操作
     * @return 执行的返回结果
     * author：chendb
     * date：2016年12月9日 上午10:33:53
     */
    public abstract Object onExecute(TContext parameter);

    // }}

    // region 重载方法
 

    @Override
    public boolean predicate(TContext parameter) {
        return this.canExecute(parameter);
    }

    // }}

}
