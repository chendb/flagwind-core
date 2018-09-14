package com.flagwind.services.base;

import java.util.ArrayList;

import com.flagwind.commons.ConverterUtils;
import com.flagwind.commons.StringUtils;
import com.flagwind.events.CancelEventArgs;
import com.flagwind.events.EventArgs;
import com.flagwind.events.EventProvider;
import com.flagwind.services.InvalidOperationException;
import com.flagwind.services.ServiceBuilder;
import com.flagwind.services.ServiceEntry;
import com.flagwind.services.ServiceProvider;
import com.flagwind.services.ServiceStorage;

public abstract class ServiceProviderBase extends EventProvider<EventArgs> implements ServiceProvider {

    //region 事件声明
    public static final String REGISTERED = "registered";
    public static final String UNREGISTERED = "unregistered";

    public static final String RESOLVING = "resolving";
    public static final String RESOLVED = "resolved";

    //endregion

    //region 成员字段
    private ServiceStorage storage;
    private ServiceBuilder builder;

    //endregion

    //region 构造函数
    protected ServiceProviderBase() {
        super(null);
    }

    protected ServiceProviderBase(ServiceStorage storage, ServiceBuilder builder) {
        super(null);
        if (storage == null)
            throw new IllegalArgumentException("storage");

        this.storage = storage;
        this.builder = builder;
    }
    //endregion

    //region 公共属性

    @Override
    public ServiceStorage getStorage() {
        return storage;
    }

    public void setStorage(ServiceStorage storage) {
        this.storage = storage;
    }

    public ServiceBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ServiceBuilder builder) {
        this.builder = builder;
    }
    //endregion

    //region 注册方法
    public void register(String name, Class<?> serviceType) {
        this.register(name, serviceType, (Class<?>[]) null);
    }

    public void register(String name, Class<?> serviceType, Class<?>[] contractTypes) {
        //创建一个服务描述项对象
        com.flagwind.services.ServiceEntry entry = this.createEntry(name, serviceType, contractTypes);

        //执行具体的注册操作
        this.register(entry);
    }

    public void register(String name, Object service) {
        this.register(name, service, (Class<?>[]) null);
    }

    public void register(String name, Object service, Class<?>[] contractTypes) {
        //创建一个服务描述项对象
        com.flagwind.services.ServiceEntry entry = this.createEntry(name, service, contractTypes);

        //执行具体的注册操作
        this.register(entry);
    }

    public void register(Class<?> serviceType, Class<?>[] contractTypes) {
        //创建一个服务描述项对象
        com.flagwind.services.ServiceEntry entry = this.createEntry(serviceType, contractTypes);

        //执行具体的注册操作
        this.register(entry);
    }

    public void register(Object service, Class<?>[] contractTypes) {
        //创建一个服务描述项对象
        com.flagwind.services.ServiceEntry entry = this.createEntry(service, contractTypes);

        //执行具体的注册操作
        this.register(entry);
    }

    public void Unregister(String name) {
        com.flagwind.services.ServiceEntry entry = storage.remove(name);

        if (entry != null) {
            this.dispatchEvent(new EventArgs( UNREGISTERED, entry));
        }
    }
    //endregion

    //region 解析方法
    public Class<?> GetServiceType(String name) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("name");

        com.flagwind.services.ServiceEntry entry = this.storage.get(name);
        return entry == null ? null : entry.getServiceType();
    }

    public Object resolve(String name) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("name");

        CancelEventArgs args = new CancelEventArgs(RESOLVING, name);

        //激发“Resolving”事件
        this.dispatchEvent(args);

        if (args.getCancel())
            return args.getData();

        Object result = null;
        com.flagwind.services.ServiceEntry entry = storage.get(name);

        if (entry != null)
            result = this.getService(entry);

        //激发“Resolved”事件
        this.dispatchEvent(new EventArgs(REGISTERED, result));

        //返回解析的结果
        return result;
    }

    @Override
    public <T> T resolve(Class<?> type) {
        return ConverterUtils.cast(this.resolve(type, null));
    }

    public Object resolve(Class<?> type, Object parameter) {
        if (type == null)
            throw new IllegalArgumentException("type");

        CancelEventArgs args = new CancelEventArgs(type.getSimpleName(), parameter);

        //激发“Resolving”事件
        this.dispatchEvent(args);

        if (args.getCancel())
            return args.getData();

        Object result = null;
        com.flagwind.services.ServiceEntry entry = this.storage.get(type);

        if (entry != null)
            result = this.getService(entry);

        //激发“Resolved”事件
        this.dispatchEvent(new EventArgs(REGISTERED, result));

        //返回解析的结果
        return result;
    }

    public <T> Iterable<T> resolveAll(Class<?> type) {
        return ConverterUtils.cast(this.resolveAll(type, null));
    }

    public Iterable<Object> resolveAll(Class<?> type, Object parameter) {
        if (type == null)
            throw new IllegalArgumentException("type");

        CancelEventArgs args = new CancelEventArgs(RESOLVING, parameter);
        //激发“Resolving”事件
        this.dispatchEvent(args);

        if (args.getCancel())
            return ConverterUtils.cast(args.getData());

        final ArrayList<Object> result = new ArrayList<>();
        Iterable<ServiceEntry> entries = this.storage.getAll(type);

        if (entries != null) {
            entries.forEach(entry -> {
                result.add(this.getService(entry));
            });
        }

        //激发“Resolved”事件
        this.dispatchEvent(new EventArgs(RESOLVED, result));

        //返回结果集
        return result;
    }

    //endregion

    //region 虚拟方法
    protected Object getService(ServiceEntry entry) {
        if (entry == null)
            return null;

        Object result = entry.getService();

        if (result == null) {

            if (builder != null) {
                result = builder.build(entry);
            }
        }

        return result;
    }

    protected ServiceEntry createEntry(Object service, Class<?>[] contractTypes) {
        return new ServiceEntry(service, contractTypes);
    }

    protected ServiceEntry createEntry(Class<?> serviceType, Class<?>[] contractTypes) {
        return new ServiceEntry(serviceType, contractTypes);
    }

    protected ServiceEntry createEntry(String name, Object service, Class<?>[] contractTypes) {
        return new ServiceEntry(name, service, contractTypes);
    }

    protected ServiceEntry createEntry(String name, Class<?> serviceType, Class<?>[] contractTypes) {
        return new ServiceEntry(name, serviceType, contractTypes);
    }
    //endregion

    //region 私有方法
    private void register(com.flagwind.services.ServiceEntry entry) {
        if (entry == null)
            throw new InvalidOperationException("Can not register for the serviceEntry ,it is null");

        //将服务描述项保存到服务容器中
        this.storage.add(entry);

        //激发“Registered”事件
        this.dispatchEvent(new EventArgs(REGISTERED, entry));
    }
    //endregion
}