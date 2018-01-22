package com.flagwind.services;
 

public class ServiceEntry {

    private String name;
    private Object service;
    private Class<?> serviceType;
    private Class<?>[] contractTypes;
    private ServiceBuilder builder;
    private ServiceLifetime lifetime;
    private final Object syncRoot = new Object();

    public ServiceEntry(String name, Object service, Class<?>[] contractTypes) {
        this.name = name;
        this.service = service;
        this.contractTypes = contractTypes;
    }

    public ServiceEntry(String name, Class<?> serviceType, Class<?>[] contractTypes) {
        this.name = name;
        this.serviceType = serviceType;
        this.contractTypes = contractTypes;
    }

    public ServiceEntry(Class<?> serviceType, Class<?>[] contractTypes) {
        this.serviceType = serviceType;
        this.contractTypes = contractTypes;
    }

    public ServiceEntry(Object service, Class<?>[] contractTypes) {
        this.service = service;
        serviceType = service.getClass();
        this.contractTypes = contractTypes;
    }

    public String name() {
        return name;
    }


    public Object getService() {
        Object result = service;

        if (result == null) {
            synchronized (syncRoot) {
                if (service == null) {
                    //创建一个新的服务实例
                    service = this.createService();

                    return service;
                }
            }
        }
        //如果没有指定服务的生命期或者当前服务是可用的则返回它
        if (lifetime == null || lifetime.isAlive(this)) {
            return result;
        }
        //至此，表明当前服务已被判定过期不可用，则重新创建一个新的服务实例(并确保当前服务没有被修改过)
        this.service = this.createService();
        return this.service;
    }


    public Class<?> getServiceType() {

        if (this.serviceType == null) {
            Object instance = this.getService();

            if (instance != null) {
                serviceType = instance.getClass();
            }
        }

        return serviceType;
    }

    public boolean hasService() {

        return service != null;

    }

    public boolean hasContracts() {

        return contractTypes != null && contractTypes.length > 0;

    }


    public Class<?>[] getContractTypes() {
        return contractTypes;
    }

    public Object createService() {


        if (builder != null) {
            Object instance = builder.build(this);

            if (instance != null) {
                serviceType = instance.getClass();
            }

            return instance;
        }

        if (serviceType != null) {
            try {
                return serviceType.newInstance();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public void setContractTypes(Class<?>[] contractTypes) {
        this.contractTypes = contractTypes;
    }

    public ServiceBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ServiceBuilder builder) {
        this.builder = builder;
    }

    public ServiceLifetime getLifetime() {
        return lifetime;
    }

    public void setLifetime(ServiceLifetime lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public String toString() {
        if (this.name() == null || this.name().length() == 0) {
            return this.getServiceType().getName();
        } else {
            return String.format("%s (%s)", this.name(), this.getServiceType().getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceEntry that = (ServiceEntry) o;

        return service != null ? service.equals(that.service) : that.service == null;
    }

    @Override
    public int hashCode() {
        return service != null ? service.hashCode() : 0;
    }
}
