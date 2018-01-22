package com.flagwind.services.base;

import org.apache.commons.lang3.StringUtils;
import com.flagwind.services.ServiceProvider;
import com.flagwind.services.ServiceProviderFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServiceProviderFactory
        implements ServiceProviderFactory, Iterable<Map.Entry<String, ServiceProvider>> {

    //region 单例字段
    private static ServiceProviderFactory instance;
    //endregion

    //region 成员字段
    private String defaultName;
    private ConcurrentHashMap<String, ServiceProvider> providers;
    //endregion

    // region 构造函数
    protected DefaultServiceProviderFactory() {
        this("");
    }

    protected DefaultServiceProviderFactory(String defaultName) {
        this.defaultName = StringUtils.isEmpty(defaultName) ? "" : defaultName.trim();
        this.providers = new ConcurrentHashMap<String, ServiceProvider>();
    }
    // endregion

    // region 单例属性
    public static ServiceProviderFactory getInstance() {
        if (instance == null) {
            instance = new DefaultServiceProviderFactory();
        }
        return instance;
    }

    public static void setInstance(ServiceProviderFactory factory) {
        instance = factory;
    }
    // endregion

    //region 公共属性
    public int count() {
        return providers.size();
    }

    @Override
    public ServiceProvider getDefault() {

        return this.getProvider(defaultName);

    }

    public void setDefault(ServiceProvider provider) {
        this.register(defaultName, provider);
    }
    // endregion

    //region 保护属性
    protected String getDefaultName() {

        return defaultName;

    }
    //endregion

    // region 公共方法
    public void register(String name, ServiceProvider provider) {
        this.register(name, provider, true);
    }

    public boolean register(String name, ServiceProvider provider, boolean replaceOnExists) {

        name = StringUtils.isEmpty(name) ? "" : name.trim();

        if (replaceOnExists) {
            providers.put(name, provider);
        } else {
            providers.putIfAbsent(name, provider);
        }

        //返回成功
        return true;
    }

    /**
     * 注销服务供应程序
     * @param name 要注销服务供应程序的名称
     */
    public void unregister(String name) {
        name = StringUtils.isEmpty(name) ? "" : name.trim();
        providers.remove(name);
    }

    /**
     * 获取指定名称的服务供应程序。具体的获取策略请参考更详细的备注说明
     * @param name 待获取的服务供应程序名
     * @return 如果指定名称的供应程序回存在则返它，否则返回空(null)
     */
    @Override
    public ServiceProvider getProvider(String name) {
        ServiceProvider result;
        name = StringUtils.isEmpty(name) ? "" : name.trim();
        result = providers.get(name);
        return result;
    }
    //endregion

    @Override
    public Iterator<Map.Entry<String, ServiceProvider>> iterator() {
        return providers.entrySet().iterator();
    }
}
