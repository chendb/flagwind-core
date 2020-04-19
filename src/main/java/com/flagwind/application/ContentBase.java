package com.flagwind.application;

import java.util.List;

public interface ContentBase {


    /**
     * 根据指定服务名称获取服务实例。
     * @param  name 服务名称。
     * @return any
     */
     <T> T  resolve(String name);

    /**T
     * 根据指定服务类型获取服务实例。
     * @param serviceType 服务类型。
     * @return T
     */
    <T> T resolve(Class<T> serviceType);

    /**
     * 根据名称获取指定服务类型获取服务实例
     * @param serviceType 服务类型
     * @param name 名称
     * @param <T> 泛型约束
     * @return
     */
    <T> T resolve(Class<T> serviceType,String name);

    /**
     * 根据指定服务类型获取所有服务实例。
     * @param serviceType
     * @return List
     */
      <T> List<T> resolveAll(Class<T> serviceType);
}
