package com.flagwind.reflect.entities;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 封装字段和方法，统一调用某些方法
 */
public class EntityField {
    private String name;
    private Field field;
    private Class<?> javaType;
    private Method setter;
    private Method getter;

    /**
     * 构造方法
     *
     * @param field              字段
     * @param propertyDescriptor 字段name对应的property
     */
    public EntityField(Field field, PropertyDescriptor propertyDescriptor) {
        if (field != null) {
            this.field = field;
            this.name = field.getName();
            this.javaType = field.getType();
        }
        if (propertyDescriptor != null) {
            this.name = propertyDescriptor.getName();
            this.setter = propertyDescriptor.getWriteMethod();
            this.getter = propertyDescriptor.getReadMethod();
            this.javaType = propertyDescriptor.getPropertyType();
        }
    }

    /**
     * 先创建field，然后可以通过该方法获取property等属性
     *
     * @param other
     */
    public void copyFromPropertyDescriptor(EntityField other) {
        this.setter = other.setter;
        this.getter = other.getter;
        this.javaType = other.javaType;
        this.name = other.name;
    }


    /**
     * 是否有该注解
     *
     * @param annotationClass
     * @return
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        boolean result = false;
        if (field != null) {
            result = AnnotatedElementUtils.isAnnotated(field, annotationClass);
            //result = field.isAnnotationPresent(annotationClass);
        }
        if (!result && setter != null) {
            result = AnnotatedElementUtils.isAnnotated(setter, annotationClass);
            //result = setter.isAnnotationPresent(annotationClass);
        }
        if (!result && getter != null) {
            result = AnnotatedElementUtils.isAnnotated(getter, annotationClass);
            //result = getter.isAnnotationPresent(annotationClass);
        }
        return result;
    }

    /**
     * 获取指定的注解
     *
     * @param annotationClass
     * @param <T>
     * @return
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        T result = null;
        if (field != null) {
            result = AnnotatedElementUtils.findMergedAnnotation(field,annotationClass);
            // result = field.getAnnotation(annotationClass);
        }
        if (result == null && setter != null) {
            result = AnnotatedElementUtils.findMergedAnnotation(setter,annotationClass);
            // result = setter.getAnnotation(annotationClass);
        }
        if (result == null && getter != null) {
            result = AnnotatedElementUtils.findMergedAnnotation(getter,annotationClass);
            //result = getter.getAnnotation(annotationClass);
        }
        return result;
    }

    public <T extends Annotation> Set<T> getRepeatableAnnotations(Class<T> annotationClass) {
        Set<T> result = new HashSet<>();
        if (field != null) {
            result = AnnotatedElementUtils.getMergedRepeatableAnnotations(field, annotationClass);
        }
        if (result == null && setter != null) {
            result = AnnotatedElementUtils.getMergedRepeatableAnnotations(setter, annotationClass);
        }
        if (result == null && getter != null) {
            result = AnnotatedElementUtils.getMergedRepeatableAnnotations(getter, annotationClass);
        }
        return result;
    }


    // /**
    //  * 是否有该注解
    //  *
    //  * @param annotationClass
    //  * @return
    //  */
    // public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
    //     boolean result = false;
    //     if (field != null) {
    //         result = field.isAnnotationPresent(annotationClass);
    //     }
    //     if (!result && setter != null) {
    //         result = setter.isAnnotationPresent(annotationClass);
    //     }
    //     if (!result && getter != null) {
    //         result = getter.isAnnotationPresent(annotationClass);
    //     }
    //     return result;
    // }

    // /**
    //  * 获取指定的注解
    //  *
    //  * @param annotationClass
    //  * @param <T>
    //  * @return
    //  */
    // public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    //     T result = null;
    //     if (field != null) {
    //         result = field.getAnnotation(annotationClass);
    //     }
    //     if (result == null && setter != null) {
    //         result = setter.getAnnotation(annotationClass);
    //     }
    //     if (result == null && getter != null) {
    //         result = getter.getAnnotation(annotationClass);
    //     }
    //     return result;
    // }


    @Deprecated
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        T[] result = null;
        if (field != null) {
            result = field.getAnnotationsByType(annotationClass);
        }
        if (result == null && setter != null) {
            result = setter.getAnnotationsByType(annotationClass);
        }
        if (result == null && getter != null) {
            result = getter.getAnnotationsByType(annotationClass);
        }
        return result;
    }

    public Object getValue(Object entity, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (getter != null) {
            return getter.invoke(entity, args);
        } else {
            return field.get(entity);
        }
    }

    public void setValue(Object entity, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (setter != null) {
            setter.invoke(entity, args);
        } else {
            field.set(entity, (args != null && args.length > 0) ? args[0] : null);
        }
    }

    /**
     * 字段属性名
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取javaType
     *
     * @return
     */
    public Class<?> getJavaType() {
        return javaType;
    }

    /**
     * 设置javaType
     *
     * @param javaType
     */
    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntityField that = (EntityField) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}