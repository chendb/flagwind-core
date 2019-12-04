package com.flagwind.lang;

import com.flagwind.commons.StringUtils;
import com.flagwind.reflect.EntityTypeHolder;
import com.flagwind.reflect.entities.EntityField;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;

public class CodeTypes {

    /**
     * 获取CodeType的值
     */
    public static String value(CodeType codeType) {
        return codeType == null ? null : codeType.getValue();
    }

    /**
     * 获取CodeType的文本
     */
    public static String text(CodeType codeType) {
        return codeType == null ? null : codeType.getText();
    }

    /**
     * 等值判断
     */
    public static boolean equals(CodeType codeType, String value) {
        if (codeType == null && StringUtils.isEmpty(value)) {
            return true;
        }
        if (codeType == null || !StringUtils.isEmpty(value)) {
            return false;
        }
        return value.equals(codeType.getValue());
    }

    /**
     * 等值判断
     */
    public static boolean equals(CodeType codeType1, CodeType codeType2) {
        if (codeType1 == null && codeType2==null) {
            return true;
        }
        if (codeType1 == null || codeType2 == null) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(codeType1.getValue(),codeType2.getValue());
    }

    public static <T extends CodeType> T valueOf(Class<T> tClass,String value) throws IllegalClassFormatException {
        try {
            T t = tClass.newInstance();
            EntityField field = EntityTypeHolder.getField(tClass,"value");
            field.setValue(t, new Object[]{value});
            return t;
        } catch (InstantiationException e) {
            throw new IllegalClassFormatException("该类型没有无参的构造函数，无法使用该方式转换！");
        } catch (IllegalAccessException e) {
            throw new IllegalClassFormatException("value字段没有公开的Set方法，无法赋值！");
        } catch (InvocationTargetException e) {
            throw new IllegalClassFormatException("value字段无法赋值！");
        }
    }


}
