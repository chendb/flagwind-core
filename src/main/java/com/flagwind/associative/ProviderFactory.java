package com.flagwind.associative;

import com.flagwind.application.Application;

/**
 * 提供器工厂
 */
public class ProviderFactory {

    /**
     * 根据关键字解析提供器
     * 
     * @param source 关键字
     * @return
     */
    public static Provider resolve(String source) {
        return Application.resolve(source);
    }

    /**
     * 根据关键字解析提供器
     * 
     * @param providerType 提供器类型
     * @return
     */
    public static Provider resolve(Class<?> providerType) {
        return resolve(providerType.getSimpleName());
    }
}
