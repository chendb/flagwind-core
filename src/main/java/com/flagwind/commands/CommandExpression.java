package com.flagwind.commands;

import com.flagwind.commons.StringUtils;
import com.flagwind.io.PathAnchor;

import java.util.Collection;
import java.util.Map.Entry;

/**
 * 命令表达式类，提供命令的名称路径与参数选项等信息。
 * @author chendb
 */
public class CommandExpression {

    private String name;
    private String path;
    private String fullPath;
    private PathAnchor anchor;
    private CommandOptionCollection options;
    private String[] arguments;
    private CommandExpression next = null;

    //#region 构造函数
    public CommandExpression(PathAnchor anchor, String name, String path, Collection<Entry<String, String>> options,
                             String... arguments) {
        if (StringUtils.isBlank(name)) {
            throw new NullPointerException("name");
        }

        //修缮传入的路径参数值
        path = StringUtils.trim(path, "/", " ", "\t", "\r", "\n");

        this.anchor = anchor;
        this.name = name.trim();

        if (anchor == PathAnchor.Root) {
            if (StringUtils.isBlank(path)) {
                this.path = "/";
            } else {
                this.path = "/" + path + "/";
            }
        } else if (anchor == PathAnchor.Current) {
            if (StringUtils.isBlank(path)) {
                this.path = "./";
            } else {
                this.path = "./" + path + "/";
            }
        } else if (anchor == PathAnchor.Parent) {
            if (StringUtils.isBlank(path)) {
                this.path = "../";
            } else {
                this.path = "../" + path + "/";
            }
        } else {
            if (StringUtils.isBlank(path)) {
                this.path = "";
            } else {
                this.path = path + "/";
            }
        }

        this.fullPath = this.path + this.name;

        if (options == null || options.size() == 0) {
            this.options = new CommandOptionCollection();
        } else {
            this.options = new CommandOptionCollection(options);
        }

        this.arguments = arguments == null ? new String[0] : arguments;
    }


    //#endregion

    //#region 公共属性
    public PathAnchor getAnchor() {
        return this.anchor;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public String getFullPath() {
        return this.fullPath;
    }

    public CommandOptionCollection getOptions() {
        return this.options;
    }

    public String[] getArguments() {
        return this.arguments;
    }

    public CommandExpression getNext() {
        return this.next;
    }

    public void setNext(CommandExpression value) {
        this.next = value;
    }
    //#endregion

    //#region 解析方法

    public static CommandExpression parse(String text) {
        try {
            return CommandExpressionParser.Instance.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //#endregion

    //#region 重写方法
    @Override
    public String toString() {
        String result = this.getFullPath();

        if (this.options.size() > 0) {
            for (Entry<String, String> option : this.options) {
                if (StringUtils.isBlank(option.getValue())) {
                    result += String.format(" /%s", option.getKey());
                } else {
                    if (option.getValue().contains("\"")) {
                        result += String.format(" -%s:'%s'", option.getKey(), option.getValue());
                    } else {
                        result += String.format(" -%s:\"%s\"", option.getKey(), option.getValue());
                    }
                }
            }
        }

        if (arguments.length > 0) {
            for (String argument : arguments) {
                if (argument.contains("\"")) {
                    result += String.format(" '%s'", argument);
                } else {
                    result += String.format(" \"{argument}\"", argument);
                }
            }
        }


        if (this.getNext() == null) {
            return result;
        } else {
            return result + " | " + next.toString();
        }
    }
    //#endregion

}