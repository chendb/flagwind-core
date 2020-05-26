package com.flagwind.application;

import java.util.List;
import java.util.Map;

/**
 * author：chendb
 */
public class Application {

    private static ContentBase content;

    private static ContentBase getContent() {
        return content;
    }


    public static void start(ContentBase content) {
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

    public static boolean contains(String name) {
        return getContent().contains(name);
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
    public static <T> T resolve(Class<T> serviceType) {
        return getContent().resolve(serviceType);
    }

    /**
     * 根据名称获取指定服务类型获取服务实例
     *
     * @param serviceType 服务类型
     * @param name        名称
     * @param <T>         泛型约束
     * @return 对象
     */
    public static <T> T resolve(Class<T> serviceType, String name) {
        return getContent().resolve(serviceType, name);
    }

    /**
     * 根据指定服务类型获取所有服务实例。
     *
     * @param serviceType 类型
     * @return List
     */
    public static <T> List<T> resolveAll(Class<T> serviceType) {
        return getContent().resolveAll(serviceType);
    }

    public static <T> Map<String,T> resolveMap(Class<T> serviceType) {
        return getContent().resolveMap(serviceType);
    }


}