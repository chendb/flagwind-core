package com.flagwind.services;

/**
 * 服务提供者工厂
 * 
 * @author chendb
 * @date 2015年10月21日 上午10:55:05
 */
public interface ServiceProviderFactory {
    /**
     * 获取或设置默认的服务供应程序。
     * 
     * @return ServiceProvider 提供器实现
     */
    ServiceProvider getDefault();


    /**
     * 获取指定名称的服务供应程序。
     * 
     * @param name 提供器名称
     * @return ServiceProvider 提供器实现
     */
    ServiceProvider getProvider(String name);



}
