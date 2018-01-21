package com.flagwind.commands;

public class CommandException extends  RuntimeException {
    /**
     * @param message 异常消息
     */
    public CommandException(String message) {
        super(message);
    }
}
