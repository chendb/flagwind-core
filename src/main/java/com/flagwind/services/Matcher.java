package com.flagwind.services;

/**
 * 服务提供者工厂
 * 
 * author：chendb
 * date：2015年10月21日 上午10:55:05
 */
public interface Matcher<T> {
    
    boolean match(Object target, T parameter);

}
