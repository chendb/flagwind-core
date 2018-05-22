package com.flagwind.services;

/**
 * 提供一种特定于类型的通用匹配方法，某些同类型的类通过实现此接口对其进行更进一步的匹配。
 * 
 * author：chendb
 * date：2015年10月21日 上午10:55:05
 */
public interface Matchable<T> {
    
    boolean isMatch(T parameter);

}
