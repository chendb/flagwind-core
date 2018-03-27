package com.flagwind.services;

public interface ServiceProvider {
    /**
     * 获取服务仓储实例。
     * @property
     */
    ServiceStorage  getStorage();

    /**
     * 注册一个服务至服务容器中。
     * @param  name 服务名称。
     * @param  {Function} serviceType 服务类型。
     * 
     */
   void register(String name,  Class<?>serviceType);
    /**
     * 注册一个服务至服务容器中。
     * @param  name 服务名称。
     * @param  {Function} serviceType 服务类型。
     * @param  {Array<Function>} contractTypes? 契约类型。
     * 
     */
    void register(String name,Class<?> serviceType, Class<?>[] contractTypes);
    /**
     * 注册一个服务至服务容器中。
     * @param  name 服务名称。
     * @param  service 服务实例。
     * 
     */
    void register(String name,Object service);
    /**
     * 注册一个服务至服务容器中。
     * @param  name 服务名称。
     * @param  service 服务实例。
     * @param  {Array<Function>} contractTypes? 契约类型。
     * 
     */
   void register(String name,Object service,Class<?>[] contractTypes);
    /**
     * 注册一个服务至服务容器中。
     * @param  {Function} serviceType 服务类型。
     * @param  {Array<Function>} contractTypes? 契约类型。
     * 
     */
   void register(Class<?>serviceType, Class<?>[] contractTypes);
    /**
     * 注册一个服务至服务容器中。
     * @param  service 服务实例。
     * @param  {Array<Function>} contractTypes? 契约类型。
     * 
     */
   void register(Object service, Class<?>[] contractTypes);

    /**
     * 移除指定名称的服务。
     * @param  name 服务名称。
     * 
     */
   void unregister(String name);

    /**
     * 根据指定服务名称获取服务实例。
     * @param  name 服务名称。
     * @return any
     */
     <T> T  resolve(String name);

    /**
     * 根据指定服务类型获取服务实例。
     * @param  {Function|string} serviceType 服务类型。
     * @return T
     */
    <T> T resolve(Class<?> serviceType);

    /**
     * 根据指定服务类型获取所有服务实例。
     * @param  {Function} serviceType
     * @return IEnumerable
     */
      <T> Iterable<T> resolveAll(Class<?> serviceType);
}
