package com.flagwind.commands;

import com.flagwind.commons.CharUtils;
import com.flagwind.commons.StringUtils;
import com.flagwind.io.PathAnchor;
import org.apache.commons.lang3.tuple.MutableTriple;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static com.flagwind.io.PathAnchor.*;

/**
 * 命令表达式解析类
 * @author chendb
 */
public class CommandExpressionParser {
    //#region 单例字段
    public static CommandExpressionParser Instance = new CommandExpressionParser();
    //#endregion

    //#region 私有枚举
    private enum CommandPathState {
        /**
         * 无
         */
        None,
        /**
         * 单点
         */
        Dot,
        /**
         * 双点
         */
        DoubleDot,
        /**
         * 斜杠
         */
        Slash,
        /**
         * 部分
         */
        Part,
    }

    private enum CommandPairState {
        None,
        Slash,
        Assign,
        Part,
    }
    //#endregion

    //#region 私有构造
    private CommandExpressionParser() {
    }
    //#endregion

    //#region 解析方法

    public CommandExpression parse(String text) throws IOException {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        CommandExpression result = null;
        CommandExpression current = null;

        StringReader reader = new StringReader(text);

        while (reader.read() > 0) {
            current = parse(reader);

            if (result != null) {
                CommandExpression previous = result;

                while (previous.getNext() != null) {
                    previous = previous.getNext();
                }

                previous.setNext(current);
            }  //线性查找命令表达式的管道链，并更新其指向
            else {
                result = current;
            }

        }



        return result;
    }

    private CommandExpression parse(StringReader reader) throws IOException {
        PathAnchor anchor;
        String name, path;
        List<String> arguments = new ArrayList<>();
        HashMap<String, String> options = new HashMap<>();

        //解析命令表达式中的路径部分，如果表达式有误则该解析方法内部抛出异常
        MutableTriple<PathAnchor, String, String> result = parsePath(reader);
        anchor = result.getLeft();
        name = result.getMiddle();
        path = result.getRight();
        Map.Entry<String, String> pair;

        //依次解析命令表达式中的选项和参数
        while ((pair = parsePair(reader)) != null) {
            if (pair != null) {
                if (StringUtils.isBlank(pair.getKey())) {
                    arguments.add(pair.getValue());
                } else {
                    options.put(pair.getKey(), pair.getValue());
                }
            }
        }
        String[] args = arguments.toArray(new String[arguments.size()]);
        //返回一个命令表达式
        return new CommandExpression(anchor, name, path, options.entrySet(), args);
    }
    //#endregion

    //#region 私有方法

    /**
     * 路径解析
     * @param reader
     * @return
     * @throws CommandExpressionException
     * @throws IOException
     */
    private static MutableTriple<PathAnchor, String, String> parsePath(StringReader reader) throws  IOException {
        if (reader == null) {
            throw new NullPointerException("reader");
        }
        MutableTriple<PathAnchor, String, String> result = new MutableTriple<>();
        CommandPathState state = CommandPathState.None;
        List<String> parts = new ArrayList<String>();
        int valueRead = 0;

        //设置输出参数的默认值
        PathAnchor anchor = None;
        String name = "";
        String path = "";

        while ((valueRead = reader.read()) > 0) {
            char chr = (char) valueRead;

            //首先对位于路径中间的点号进行转换，以方便后续的处理
            if (chr == '.' && state == CommandPathState.Part) {
                chr = '/';
            }

            if (chr == '.') {
                switch (state) {
                    case None:
                        state = CommandPathState.Dot;
                        anchor = Current;
                        break;
                    case Dot:
                        state = CommandPathState.DoubleDot;
                        anchor = Parent;
                        break;
                    case DoubleDot:
                        break;
                    case Slash:
                        break;
                    case Part:
                        break;
                    default:
                        throw new CommandExpressionException("Invalid anchor of command path.");
                }
            } else if (chr == '/') {
                if (state == CommandPathState.Slash) {
                    throw new CommandExpressionException("Duplicate '/' slash characters.");
                }

                if (state == CommandPathState.None) {
                    anchor = Root;
                } else if (state == CommandPathState.Part) {
                    parts.add(name);
                    name = "";
                }

                state = CommandPathState.Slash;
            } else if (Character.isLetterOrDigit(chr) || chr == '_') {
                if (state == CommandPathState.Dot || state == CommandPathState.DoubleDot) {
                    throw new CommandExpressionException("Missing '/' slash character between dot and letter or digit.");
                }

                name += chr;
                state = CommandPathState.Part;
            } else if (CharUtils.isWhiteSpace(chr)) {
                if (state == CommandPathState.None) {
                    continue;
                } else {
                    break;
                }
            } else {
                throw new CommandExpressionException("Contains '{chr}' illegal character(s) in the command path.");
            }
        }
        reader.close();

        //如果路径以斜杠符结尾，即为非法路径格式
        if (state == CommandPathState.Slash && ((parts != null && parts.size() > 0) || anchor != Root)) {
            throw new CommandExpressionException("The command path can not at the end of '/' character.");
        }

        if (parts != null && parts.size() > 0) {
            path = String.join(".", parts);
        } else if (StringUtils.isBlank(name)) {
            switch (anchor) {
                case Root:
                    name = "/";
                    break;
                case Current:
                    name = ".";
                    break;
                case Parent:
                    name = "..";
                    break;
            }

            anchor = None;
        }
        result.setLeft(anchor);
        result.setMiddle(name);
        result.setRight(path);
        return result;
    }

    private static Collection<Map.Entry<String, String>> parsePairs(String text) throws IOException {
        List<Map.Entry<String, String>> result = new ArrayList<>();
        if (text == null) {
            return null;
        }

        StringReader reader = new StringReader(text);

        do {
            result.add(parsePair(reader));
        } while (reader.read() > 0);
        reader.close();
        return result;
    }

    private static Map.Entry<String, String> parsePair(StringReader reader) throws IOException {
        if (reader == null) {
            throw new NullPointerException("reader");
        }

        char quote = '\0';
        boolean isEscaping = false;
        String key = StringUtils.EMPTY;
        String value = StringUtils.EMPTY;
        CommandPairState state = CommandPairState.None;
        int valueRead;

        while ((valueRead = reader.read()) > 0) {
            char chr = (char) valueRead;

            if (chr == '-' || chr == '/') {
                if (state == CommandPairState.Slash) {
                    throw new CommandExpressionException("Duplicate '{chr}' option indicator of command expression.");
                }

                if (state == CommandPairState.None && quote == '\0') {
                    state = CommandPairState.Slash;
                    continue;
                }
            } else if (chr == ':' || chr == '=') {
                if (state == CommandPairState.Part
                        && !StringUtils.isBlank(key)
                        && (quote == '\0' && !isEscaping)) {
                    state = CommandPairState.Assign;
                    continue;
                }
            } else if (chr == '|') {
                if (quote == '\0') {
                    if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
                        return null;
                    }

                    return new AbstractMap.SimpleEntry<String, String>(key, value);
                }
            } else if (CharUtils.isWhiteSpace(chr)) {
                if (state == CommandPairState.Slash) {
                    throw new CommandExpressionException("A white-space character at the back of the option indicator.");
                }

                if (state == CommandPairState.None) {
                    continue;
                } else if (quote == '\0') {
                    return new AbstractMap.SimpleEntry<String, String>(key, value);
                }
            } else if (isQuote(chr) && !isEscaping) {
                if (quote != '\0') {
                    quote = '\0';
                    continue;
                } else if (state != CommandPairState.Part) {
                    quote = chr;
                    continue;
                }
            }

            //设置转义状态：即当前字符为转义符并且当前状态不为转义状态
            isEscaping = chr == '\\' && (!isEscaping);

            if (isEscaping) {
                continue;
            }

            switch (state) {
                case Slash:
                    key += chr;
                    break;
                case None:
                case Assign:
                    value += chr;
                    break;
                default:
                    if (StringUtils.isBlank(value)) {
                        key += chr;
                    } else {
                        value += chr;
                    }
                    break;
            }

            state = CommandPairState.Part;
        }

        if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
            return null;
        }

        return new AbstractMap.SimpleEntry<String, String>(key, value);
    }

    private static boolean isQuote(char chr) {
        return (chr == '"' || chr == '\'');
    }

    private static char escapeChar(char chr) {
        switch (chr) {
            case 's':
                return ' ';
            case 't':
                return '\t';
            case '\\':
                return '\\';
            default:
                return '\0';
        }
    }
    //#endregion
}
