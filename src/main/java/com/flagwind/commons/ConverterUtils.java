package com.flagwind.commons;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class ConverterUtils {

    public static class Arrays {
        public static <T> Object[] toObject(T[] arr) {
            Object[] result = new Object[arr.length];
            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i];
            }
            return result;
        }
    }


    /**
     * 强制类型转换
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public static <T, S> T to(S obj) {

        try {
            // 通过反射获取到方法
            Method declaredMethod = ConverterUtils.class.getDeclaredMethod("cast", obj.getClass());
            // 获取到方法的参数列表
            Type type = declaredMethod.getGenericReturnType();
            return to(type.getClass(), obj);
        } catch (Exception e) {
            ExceptionUtils.wrapAndThrow(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, S> T to(Class<?> clzss, S obj) {

        if (clzss == Integer.TYPE) {
            return (T) toInt(obj, null);
        } else if (clzss == Long.TYPE) {
            return (T) toLong(obj, null);
        } else if (clzss == Double.TYPE) {
            return (T) toDouble(obj, null);
        } else if (clzss == Float.TYPE) {
            return (T) toFloat(obj, null);
        } else if (clzss == String.class) {
            return (T) toString(obj, null);
        }
        return (T) obj;

    }

    /**
     * <将obj转换为string，如果obj为null则返回defaultVal>
     * 
     * @param obj        需要转换为string的对象
     * @param defaultVal 默认值
     * @return obj转换为string
     */
    public static <T> String toString(T obj, String defaultVal) {
        if (obj instanceof Timestamp) {
            return (new Monment((Timestamp) obj)).toString("yyyy-MM-dd HH:mm:ss");
        }
        if (obj instanceof Date) {
            Date date = ((Date) obj);
            if (date.getHours() == 0 && date.getMinutes() == 0 && date.getSeconds() == 0) {
                return (new Monment((Date) obj)).toString("yyyy-MM-dd HH:mm:ss");
            } else {
                return (new Monment((Date) obj)).toString("yyyy-MM-dd HH:mm:ss");
            }
        }
        if (obj instanceof Double) {
            return BigDecimal.valueOf((Double) obj).stripTrailingZeros().toPlainString();
        }
        if (obj instanceof Float) {
            return BigDecimal.valueOf((Float) obj).stripTrailingZeros().toPlainString();
        }
        return (obj != null) ? obj.toString() : defaultVal;
    }

    /**
     * <将obj转换为string，默认为空>
     * 
     * @param obj 需要转换为string的对象
     * @return 将对象转换为string的字符串
     */
    public static String toString(Object obj) {
        return toString(obj, "");
    }

    /**
     * <将对象转换为int>
     * 
     * @param obj        需要转换为int的对象
     * @param defaultVal 默认值
     * @return obj转换成的int值
     */
    public static <T> Integer toInt(T obj, Integer defaultVal) {
        try {
            if (obj == null) {
                return defaultVal;
            }

            if (obj instanceof Float) {
                return ((Float) obj).intValue();
            } else if (obj instanceof Long) {
                return ((Long) obj).intValue();
            } else if (obj instanceof Double) {
                return ((Double) obj).intValue();
            } else if (obj instanceof String) {
                return Integer.parseInt((String) obj);
            }

            return Integer.parseInt(toString(obj, "0"));
        } catch (Exception e) {
        }
        return defaultVal;
    }

    /**
     * <将对象转换为int>
     * 
     * @param obj  需要转换为int的对象
     * @return obj转换成的int值
     */
    public static Integer toInt(Object obj) {
        return toInt(obj, 0);
    }

    /**
     * <将对象转换为Integer>
     * 
     * @param obj 需要转换为Integer的对象
     * @return obj转换成的Integer值
     */
    public static Integer toInteger(Object obj) {
        return toInt(obj, null);
    }

    /**
     * <将对象转换为int>
     * 
     * @param obj        需要转换为int的对象
     * @param defaultVal 默认值
     * @return obj转换成的int值
     */
    public static <T> Float toFloat(T obj, Float defaultVal) {
        if (obj == null) {
            return defaultVal;
        }

        if (obj instanceof Integer) {
            return new Float((Integer) obj);
        } else if (obj instanceof Long) {
            return new Float((Long) obj);
        } else if (obj instanceof Double) {
            return new Float((Double) obj);
        } else if (obj instanceof String) {
            return Float.parseFloat((String) obj);
        }
        return Float.parseFloat(toString(obj, "0"));
    }

    /**
     * <将对象转换为Float>
     * 
     * @param obj 需要转换为Float的对象
     * @return obj转换成的Float值
     */
    public static Float toFloat(Object obj) {
        return toFloat(obj, null);
    }

    /**
     * <将obj转换为long>
     * 
     * @param obj        需要转换的对象
     * @param defaultVal 默认值
     * @return 如果obj为空则返回默认，不为空则返回转换后的long结果
     */
    public static <T> Long toLong(T obj, Long defaultVal) {
        if (obj == null) {
            return defaultVal;
        }

        if (obj instanceof Integer) {
            return new Long((Integer) obj);
        } else if (obj instanceof Double) {
            return Math.round((Double) obj);
        } else if (obj instanceof Float) {
            return (long) Math.round((Float) obj);
        } else if (obj instanceof String) {
            return Long.parseLong((String) obj);
        }
        return Long.parseLong(toString(obj));
    }

    /**
     * <将obj转换为long>
     * 
     * @param obj 需要转换的对象
     * @return 如果obj为空则返回默认的0l，不为空则返回转换后的long结果
     */
    public static Long toLong(Object obj) {
        return toLong(obj, null);
    }

    /**
     * 将object转换为double类型，如果出错则返回 defaultVal
     * 
     * @param obj        需要转换的对象
     * @param defaultVal 默认值
     * @return 转换后的结果
     */
    public static <T> Double toDouble(T obj, Double defaultVal) {
        try {
            if (obj == null) {
                return defaultVal;
            }

            if (obj instanceof Integer) {
                return new Double((Integer) obj);
            } else if (obj instanceof Long) {
                return new Double((Long) obj);
            } else if (obj instanceof Float) {
                return new Double((Float) obj);
            } else if (obj instanceof String) {
                return Double.parseDouble((String) obj);
            }
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * 将object转换为double类型，如果出错则返回 0d
     * 
     * @param obj 需要转换的对象
     * @return 转换后的结果
     */
    public static double toDouble(Object obj) {
        return toDouble(obj, null);
    }

}
