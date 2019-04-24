package com.flagwind.associative;

import com.flagwind.application.Application;

/**
 * 提供器工厂
 */
public class AssociativeProviderFactory {

    private Discovery discovery;

    // #region 构造函数
    public AssociativeProviderFactory(Discovery discovery) {
        this.discovery = discovery;
    }
    // #endregion

    private static AssociativeProviderFactory _default;

    public static void setInstance(AssociativeProviderFactory factory) {
        _default = factory;
    }

    public static AssociativeProviderFactory instance() {
        if (_default == null) {
            _default = new AssociativeProviderFactory(new Discovery() {

                @Override
                public <T> T discover(String name) {
                    return Application.resolve(name);
                }

                @Override
                public <T> T discover(Class<?> serviceType) {
                    return Application.resolve(serviceType);
                }

            });
        }
        return _default;
    }

    /**
     * 根据关键字解析提供器
     * 
     * @param source 关键字
     * @return
     */
    public AssociativeProvider resolve(String source) {
        return this.discovery.discover(source);
    }

    /**
     * 根据关键字解析提供器
     * 
     * @param providerType 提供器类型
     * @return
     */
    public AssociativeProvider resolve(Class<?> providerType) {
        return this.discovery.discover(providerType);
    }

    /**
     * 服务或对象查找接口
     */
    public static interface Discovery {

        /**
         * 根据指定服务名称获取服务实例。
         * 
         * @param name 服务名称。
         * @return any
         */
        <T> T discover(String name);

        /**
         * 根据指定服务类型获取服务实例。
         * 
         * @param serviceType 服务类型。
         * @return T
         */
        <T> T discover(Class<?> serviceType);
    }

    /**
     * @return Discovery return the discovery
     */
    public Discovery getDiscovery() {
        return discovery;
    }

    /**
     * @param discovery the discovery to set
     */
    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }

}
