package com.flagwind.commands;


/**
 * 命令执行器上下文
 */
public class CommandExecutorContext {
    //#region 成员字段
    private CommandExecutor executor;
    private CommandExpression expression;
    private Object parameter;
    //#endregion


    public CommandExecutorContext(CommandExecutor executor, CommandExpression expression, Object parameter) {
        this.executor = executor;
        this.expression = expression;
        this.parameter = parameter;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }


    public CommandExpression getExpression() {
        return expression;
    }


    public Object getParameter() {
        return parameter;
    }


}
