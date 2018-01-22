package com.flagwind.commands;

/**
 * 命令异常
 */
public class CommandException extends  RuntimeException {

    private static final long serialVersionUID = -2102256152049762975L;

	/**
     * @param message 异常消息
     */
    public CommandException(String message) {
        super(message);
    }
}
