package com.flagwind.reflect;

import com.flagwind.lang.CodeType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.flagwind.commons.Monment;

/**
 * 简单类型判断工具
 */
public class SimpleTypeUtils {
    private static final Set<Class<?>> SIMPLE_TYPE_SET = new HashSet<Class<?>>();

    /**
     * 特别注意：由于基本类型有默认值，因此在实体类中不建议使用基本类型作为数据库字段类型
     */
    static {
        SIMPLE_TYPE_SET.add(byte[].class);
        SIMPLE_TYPE_SET.add(String.class);
        SIMPLE_TYPE_SET.add(Byte.class);
        SIMPLE_TYPE_SET.add(Short.class);
        SIMPLE_TYPE_SET.add(Character.class);
        SIMPLE_TYPE_SET.add(int.class);
        SIMPLE_TYPE_SET.add(Integer.class);
        SIMPLE_TYPE_SET.add(long.class);
        SIMPLE_TYPE_SET.add(Long.class);
        SIMPLE_TYPE_SET.add(float.class);
        SIMPLE_TYPE_SET.add(Float.class);
        SIMPLE_TYPE_SET.add(double.class);
        SIMPLE_TYPE_SET.add(Double.class);
        SIMPLE_TYPE_SET.add(boolean.class);
        SIMPLE_TYPE_SET.add(Boolean.class);
        SIMPLE_TYPE_SET.add(Date.class);
        SIMPLE_TYPE_SET.add(LocalDate.class);
        SIMPLE_TYPE_SET.add(LocalDateTime.class);
        SIMPLE_TYPE_SET.add(Timestamp.class);
        SIMPLE_TYPE_SET.add(Monment.class);
        // SIMPLE_TYPE_SET.add(Class.class);
        SIMPLE_TYPE_SET.add(BigInteger.class);
        SIMPLE_TYPE_SET.add(BigDecimal.class);
        SIMPLE_TYPE_SET.add(CodeType.class);
    }

    /**
     * 注册新的类型
     *
     * @param clazz
     */
    public static void register(Class<?> clazz){
        SIMPLE_TYPE_SET.add(clazz);
    }

    /**
     * 注册新的类型
     *
     * @param classes
     */
    public static void register(String classes){
        if(StringUtils.isNotEmpty(classes)){
            String[] cls = classes.split(",");
            for (String c : cls) {
                try {
                    SIMPLE_TYPE_SET.add(Class.forName(c));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("注册类型出错:" + c, e);
                }
            }
        }
    }


    public static boolean isSimpleType(Class<?> clazz) {
        if (clazz.isEnum()) {
            return true;
        }
        if (java.util.Date.class.isAssignableFrom(clazz)) {
            return true;
        }
        if (CodeType.class.isAssignableFrom(clazz)) {
            return true;
        }
        return SIMPLE_TYPE_SET.contains(clazz);
    }

}