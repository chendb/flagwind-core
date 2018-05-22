package com.flagwind.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.flagwind.commons.ConverterUtils;

/**
 * 泛型命令接口
 *
 * author：chendb
 */
public class CommandExecutor {

    private CommandCollection commands; // 命令存储容器
    private CommandExpressionParser parser; // 命令解析器
    private static CommandExecutor _default; // 默认静态实例

    public CommandExecutor(CommandExpressionParser parser) {
        this.parser = parser == null ? CommandExpressionParser.Instance : parser;
    }

    public CommandCollection getCommands() {
        return commands;
    }

    public void setCommands(CommandCollection commands) {
        this.commands = commands;
    }

    public CommandExpressionParser getParser() {
        return parser;
    }

    public void setParser(CommandExpressionParser parser) {
        this.parser = parser;
    }

    public static CommandExecutor getDefault() {
        if (_default == null) {
            _default = new CommandExecutor(null);
        }
        return _default;
    }

    public static void setDefault(CommandExecutor _default) {
        CommandExecutor._default = _default;
    }

    /**
     * 注册一个命令。 注意: 如果路径已存在，则会抛出一个异常。
     * 
     * @param path    命令路径。
     * @param command 命令实例。
     * 
     */
    public void register(String path, Command<?> command) {
        this.commands.add(path, command);
    }

    /**
     * 移除指定路径的命令。
     * 
     * @param path
     * @return boolean
     */
    public boolean remove(String path) {
        return this.commands.remove(path);
    }

    /**
     * 查找指定路径的命令。
     * 
     * @param path 路径字符串。
     * @return ICommand
     */
    public Command<?> find(String path) {
        return this.commands.find(path);
    }

    /**
     * 执行命令。
     * 
     * summary: 暂不支持表达式，commandText 仅为命令路径。
     * @param commandText 指定要执行的命令表达式文本。
     * @param parameter   指定的输入参数。
     * @return any 返回命令执行的结果。
     */
    public Object execute(String commandText, Object parameter) {
        if (commandText == null) {
            throw new IllegalArgumentException();
        }

        CommandExecutorContext context = null;

        try {
            // 创建命令执行器上下文
            context = this.createExecutorContext(commandText, parameter);

            if (context == null) {
                throw new CommandException("Create executor context failed.");
            }
        } catch (Exception ex) {

            return null;
        }

        // 调用执行请求
        Object result = this.onExecute(context);

        return result;
    }

    /**
     * 当执行命令时调用。
     * 
     * @param context 命令执行上下文。
     * @return any
     */
    protected Object onExecute(CommandExecutorContext context) {
        Map<CommandExpression, Command<?>> entries = new HashMap<>();
        CommandExpression expression = context.getExpression();

        while (expression != null) {
            // 查找指定路径的命令节点
            Command<?> command = this.find(expression.getFullPath());

            // 如果指定的路径不存在的则抛出异常
            if (command == null) {
                throw new CommandException(
                        String.format("The command path '%s' can not found.", expression.getFullPath()));
            }

            // 将找到的命令表达式和对应的节点加入数组中
            entries.put(expression, command);

            // 设置下一个待搜索的命令表达式
            expression = expression.getNext();
        }

        // 初始化第一个输入参数
        Object parameter = context.getParameter();

        // 如果列表为空，则返回空
        if (entries.size() < 1) {
            return null;
        }

        for (Map.Entry<CommandExpression, Command<?>> entry : entries.entrySet()) {

            // 执行命令
            parameter = this.executeCommand(context, entry.getKey(), entry.getValue(), parameter);
        }

        // 返回最后一个命令的执行结果
        return parameter;
    }

    /**
     * 执行命令。
     * 
     * @param context
     * @param expression
     * @param command
     * @param parameter
     * @return any
     */
    protected Object executeCommand(CommandExecutorContext context, CommandExpression expression, Command<?> command,
            Object parameter) {
        if (context == null || expression == null) {
            throw new IllegalArgumentException();
        }

        if (command == null) {
            return null;
        }

        Object comandContext = this.createCommandContext(expression, command, parameter);
        Command<Object> cmd = ConverterUtils.cast(command);

		Object result =cmd.execute(comandContext);

        return result;
    }

    /**
     * 创建命令执行上下文实例。
     * 
     * 
     * @param commandText
     * @param parameter
     * @return CommandExecutorContext
     */
    protected CommandExecutorContext createExecutorContext(String commandText, Object parameter) {
        // 解析当前命令文本
        CommandExpression expression = this.onParse(commandText);

        if (expression == null) {
            throw new CommandException(String.format("Invalid command expression text: %s.", commandText));
        }

        return new CommandExecutorContext(this, expression, parameter);
    }

    /**
     * 创建命令上下文实例。
     * 
     * 
     * @param expression
     * @param command
     * @param parameter
     * @return CommandContext
     */
    protected CommandContext createCommandContext(CommandExpression expression, Command<?> command, Object parameter) {
        return new CommandContext(this, expression, command, parameter);
    }

    /**
     * 当解析命令表达式时调用。
     * 
     * 
     * @param text
     * @return CommandExpression
     */
    protected CommandExpression onParse(String text) {
        try {
            return this.parser.parse(text);
        } catch (IOException e) {
            return null;
        }
    }
}