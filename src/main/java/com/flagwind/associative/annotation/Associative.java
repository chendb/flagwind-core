package com.flagwind.associative.annotation;

import java.lang.annotation.*;

/**
 * 联想注解
 */
@Repeatable(Associatives.class)
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Associative {
    /**
     * 联想属性名称
     * @return
     */
    String name();

    /**
     * 生成联想属性的转换器
     * @return
     */
    String provider();

    /**
     * 生成联想属性的转换器（与provider同一意义）
     * @deprecated 该参数换成了provider
     * @return
     */
    @Deprecated
    String source();

    /**
     * 扩展参数
     * @return
     */
    String extras() default "";

    /**
     * 触发类型
     * @return
     */
    TriggerType tigger() default TriggerType.Json;

    /**
     * 触发联想类型
     */
    public static enum TriggerType{
        Json,Database
    }
}
