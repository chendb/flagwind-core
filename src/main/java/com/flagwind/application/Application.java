package com.flagwind.application;

import java.util.List;

/**
 * author：chendb
 */
public class Application {

    private static ApplicationContentBase content;

    private static ApplicationContentBase getContent() {
        return content;
    }


    public static void start(ApplicationContentBase content) {
        isStarted = true;
        Application.content = content;
    }

    /**
     * 标识应用程序是否启动完成
     */
    private static boolean isStarted = false;


    /**
     * @return the isStarted
     */
    public static boolean isStarted() {
        return isStarted;
    }


    /**
     * 关闭当前应用程序。
     */
    public static void exit() {

        isStarted = false;
        content = null;
    }


    public static <T> T resolve(String name) {
        return getContent().resolve(name);
    }

    /**
     * 根据指定服务类型获取服务实例。
     *
     * @param serviceType 服务类型。
     * @return T
     */
    public static <T> T resolve(Class<?> serviceType) {
        return getContent().resolve(serviceType);
    }

    /**
     * 根据名称获取指定服务类型获取服务实例
     *
     * @param serviceType 服务类型
     * @param name        名称
     * @param <T>         泛型约束
     * @return
     */
    public static <T> T resolve(Class<?> serviceType, String name) {
        return getContent().resolve(serviceType, name);
    }

    /**
     * 根据指定服务类型获取所有服务实例。
     *
     * @param serviceType
     * @return List
     */
    public static <T> List<T> resolveAll(Class<?> serviceType) {
        return getContent().resolveAll(serviceType);
    }


}