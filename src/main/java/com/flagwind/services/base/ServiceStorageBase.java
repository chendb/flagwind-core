package com.flagwind.services.base;

import org.apache.commons.lang3.StringUtils;
import com.flagwind.services.ServiceEntry;
import com.flagwind.services.ServiceProvider;
import com.flagwind.services.ServiceStorage;
import java.util.*;

public abstract class ServiceStorageBase implements ServiceStorage {

    // region 成员字段
    private ServiceProvider provider;
    // endregion

    public ServiceStorageBase(ServiceProvider provider) {
        this.provider = provider;
    }

    // region 公共属性
    public abstract int count();

    public ServiceProvider getProvider() {
        return this.provider;
    }
    // endregion

    // region 公共方法

    public ServiceEntry register(Object service) {
        return this.register(service, null);
    }

    public ServiceEntry register(Object service, Class<?>[] contractTypes) {
        ServiceEntry entry = new ServiceEntry(service, contractTypes);
        this.add(entry);
        return entry;
    }

    public ServiceEntry register(Class<?> serviceType) {
        return this.register(serviceType, null);
    }

    public ServiceEntry register(Class<?> serviceType, Class<?>[] contractTypes) {
        ServiceEntry entry = new ServiceEntry(serviceType, contractTypes);
        this.add(entry);
        return entry;
    }

    public ServiceEntry register(String name, Object service) {
        return this.register(name, service, null);
    }

    public ServiceEntry register(String name, Object service, Class<?>[] contractTypes) {
        ServiceEntry entry = new ServiceEntry(name, service, contractTypes);
        this.add(entry);
        return entry;
    }

    public ServiceEntry register(String name, Class<?> serviceType) {
        return this.register(name, serviceType, null);
    }

    public ServiceEntry register(String name, Class<?> serviceType, Class<?>[] contractTypes) {
        ServiceEntry entry = new ServiceEntry(name, serviceType, contractTypes);
        this.add(entry);
        return entry;
    }
   // public abstract void add(ServiceEntry entry);

   // public abstract void clear();

    @Override
    public  ServiceEntry get(String name)
    {
        if(StringUtils.isEmpty(name)) {
            return null;
        }

        //从当前容器及其外链容器中查找指定名称的服务
        ServiceEntry result = this.find(name,Arrays.asList(this));

        //如果上面的查找失败，则尝试从默认服务容器及其外链容器中查找指定名称的服务
        if(result == null && DefaultServiceProviderFactory.getInstance().getDefault() != null && !this.equals(DefaultServiceProviderFactory.getInstance().getDefault())) {
            result = this.find(name, Arrays.asList(DefaultServiceProviderFactory.getInstance().getDefault().getStorage()));
        }

        return result;
    }

    @Override
    public  ServiceEntry get(Class<?> type)
    {
        return (ServiceEntry)this.find(type, null, false);
    }

    @Override
    public   Iterator<ServiceEntry> getAll(Class<?>  type)
    {
        return (Iterator<ServiceEntry>)this.find(type, null, true);
    }
    // endregion


    // region 查找方法
    protected Object find(Class<?> type, Object parameter, boolean isMultiplex) {
        //从当前容器及其外链容器中查找指定类型的服务
        List<ServiceStorage> storages = new ArrayList<>();
        storages.add(this);
        Object result = find(type, parameter, isMultiplex, storages);

        boolean succeed = result != null;

        if (succeed) {
            Collection<ServiceEntry> entiries = (Collection<ServiceEntry>) result;
            succeed &= entiries == null || entiries.size() > 0;
        }

        //如果上面的查找失败，则尝试从默认服务容器及其外链容器中查找指定名称的服务
        if (!succeed && DefaultServiceProviderFactory.getInstance().getDefault() != null
                && !this.equals(DefaultServiceProviderFactory.getInstance().getDefault())) {
            result = this.find(type, parameter, isMultiplex, Arrays.asList(
                    DefaultServiceProviderFactory.getInstance().getDefault().getStorage()
            ));
        }

        return result;
    }

    private Object find(Class<?> type, Object parameter, Boolean isMultiplex, List<ServiceStorage> storages) {
        if (type == null || storages == null) {
            return null;
        }

        List<ServiceEntry> weakly = new ArrayList<ServiceEntry>();
        HashSet<ServiceEntry> strong = new HashSet<ServiceEntry>();

        for (int i = 0; i < storages.size(); i++) {
            ServiceStorage storage = storages.get(i);

            for (ServiceEntry entry : storage) {
                if (entry == null || entry.getServiceType() == null) {
                    continue;
                }

                //如果服务条目声明了契约，则按契约声明进行匹配
                if (entry.hasContracts()) {
                    //契约的严格匹配
                    if (Arrays.asList(entry.getContractTypes()).contains(type)) {
                        if (!isMultiplex) {
                            return entry;
                        }

                        strong.add(entry);
                    } else //契约的弱匹配
                    {
                        for (Class<?> contract : entry.getContractTypes()) {

                            if (type.isAssignableFrom(contract)) {
                                weakly.add(entry);
                            }
                        }
                    }
                } else //处理未声明契约的服务
                {
                    //服务类型的严格匹配
                    if (entry.getServiceType() == type) {
                        if (!isMultiplex) {
                            return entry;
                        }

                        strong.add(entry);
                    } else //服务类型的弱匹配
                    {
                        if (type.isAssignableFrom(entry.getServiceType())) {
                            weakly.add(entry);
                        }
                    }
                }

                //如果只查找单个服务
                if (!isMultiplex) {
                    //如果只查找单个服务，并且弱匹配已成功则退出查找
                    if (weakly.size() > 0) {
                        break;
                    }

                    //如果当前服务项是一个服务容器
                    if (ServiceProvider.class.isAssignableFrom(entry.getServiceType())) {
                        ServiceProvider provider = (ServiceProvider) entry.getService();

                        //如果当前服务项对应的服务容器不在外部容器列表中，则将当前服务项(服务容器)加入到外部服务容器列表中
                        if (provider != null && !storages.contains(provider.getStorage())) {
                            storages.add(provider.getStorage());
                        }
                    }
                }
            }


            if (isMultiplex) {
                strong.addAll(weakly);
                return strong.toArray(new ServiceEntry[strong.size()]);
            } else if (weakly.size() > 0) {
                return weakly.get(0);
            }
        }

        //返回空(查找失败)
        return null;
    }

    private ServiceEntry find(String name, List<ServiceStorage> storages) {
        if (StringUtils.isEmpty(name) || storages == null) {
            return null;
        }

        for (int i = 0; i < storages.size(); i++) {
            ServiceStorage storage = storages.get(i);

            for (ServiceEntry entry : storage) {
                if (entry == null) {
                    continue;
                }

                //如果名称匹配成功则返回（名称不区分大小写）
                if (StringUtils.equalsIgnoreCase(entry.name(), name)) {
                    return entry;
                }

                //如果当前服务项是一个服务容器
                if (entry.getServiceType() != null && ServiceProvider.class.isAssignableFrom(entry.getServiceType())) {
                    ServiceProvider provider = (ServiceProvider) entry.getService();

                    //如果当前服务项对应的服务容器不在外部容器列表中，则将当前服务项(服务容器)加入到外部服务容器列表中
                    if (provider != null && !storages.contains(provider.getStorage())) {
                        storages.add(provider.getStorage());
                    }
                }
            }
        }


        //返回空(查找失败)
        return null;
    }
    // endregion

}