package com.flagwind.commands;

import java.util.*;
import java.util.function.Consumer;

/**
 * 命令集合
 */
public class CommandCollection implements Iterable<Map.Entry<String, Command>> {

    private Map<String, Command> items;

    /**
     * 获取命令的总数量。
     * @property
     * @returns number
     */
    public int size() {
        return this.items.size();
    }

    public CommandCollection() {
        this.items = new HashMap<>();
    }

    @Override
    public Iterator<Map.Entry<String, Command>> iterator() {
        return this.items.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Command>> action) {
        this.forEach(action);
    }

    /**
     * 将一个命令实例挂载至指定的的路径。
     * @summary 如果指定的路径已存在命令将会抛出异常。
     * @param  path 路径字符串。
     * @param  command 命令。
     */
    public void add(String path, Command command) {
        if (this.items.containsKey(path)) {
            throw new IllegalArgumentException(String.format("The command path '%s' is existed.", path));
        }

        this.items.put(path, command);
    }

    /**
     * 移除指定路径的命令。
     * @param  path 路径字符串。
     */
    public boolean remove(String path) {
        this.items.remove((Object) path);
        return true;
    }

    /**
     * 根据指定的路径获取一个命令。
     * @param  path 路径字符串。
     */
    public Command find(String path) {
        Command command = this.items.get(path);

        return command;
    }

    /**
     * 检测是否包含指定的路径的命令。
     * @param  path
     */
    public boolean contains(String path) {
        return this.items.containsKey(path);
    }

}
