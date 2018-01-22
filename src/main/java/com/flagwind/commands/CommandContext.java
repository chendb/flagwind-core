package com.flagwind.commands;

import java.util.Map;

/**
 * 命令上下文
 * @author chendb
 */
public class CommandContext {

    private CommandExecutor executor ;
    private CommandExpression expression;
    private Command command ;
    private Object parameter;
    private Map<String, Object> extendedProperties ;

    public CommandContext(CommandExecutor executor, CommandExpression expression, Command command, Object parameter) {
        this.executor = executor;
        this.expression = expression;
        this.command = command;
        this.parameter = parameter;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public CommandExpression getExpression() {
        return expression;
    }

    public void setExpression(CommandExpression expression) {
        this.expression = expression;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public Map<String, Object> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(Map<String, Object> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }
}
