package com.flagwind.persistent.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 被标识为该注解的对象将不会作为查询条件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, METHOD, FIELD })
public @interface ConditionIgnore {

}
