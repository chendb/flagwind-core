package com.flagwind.commands;


/**
 * 命令表达式异常
 * author：chendb
 */
public class CommandExpressionException extends  RuntimeException {
    
    private static final long serialVersionUID = 4482827695438527417L;

	/**
     * @param message 异常消息
     */
    public CommandExpressionException(String message) {
        super(message);
    }
}
