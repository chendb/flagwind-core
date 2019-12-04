package com.flagwind.application;

import com.flagwind.application.base.ApplicationContextBase;
import com.flagwind.application.base.WorkbenchBase;
import com.flagwind.events.CancelEventArgs;
import com.flagwind.events.EventArgs;
import com.flagwind.events.EventProvider;
import com.flagwind.services.ServiceProvider;
import com.flagwind.services.ServiceProviderFactory;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

/**
 * author：chendb
 */
public class Application {

    // region 私有变量

    /**
     * 标识应用程序是否启动完成
     */
    private static boolean isStarted = false;

    /**
     * 应用程序上下文实例
     */
    private static ApplicationContextBase context = null;

    /**
     * 事件提供程序
     */
    private static EventProvider<EventArgs> eventProvider;

    // endregion

    // region 公共属性
    /**
     * 获取一个事件提供程序实例。
     * 
     * 
     * @return IEventProvider
     */
    public static EventProvider<EventArgs> getEventProvider() {
        if (eventProvider == null) {
            eventProvider = new EventProvider<>(null);
        }

        return eventProvider;
    }

    /**
     * @return the isStarted
     */
    public static boolean isStarted() {
        return isStarted;
    }

    /**
     * @return the context
     */
    public static ApplicationContextBase getContext() {
        return context;
    }

    public static ServiceProviderFactory getServiceFactory() {
        ApplicationContextBase context = getContext();
        if (context != null) {
            return context.getServiceFactory();
        }
        return null;
    }

    public static ServiceProvider getServiceProvider(String name) {
        ServiceProviderFactory factory = getServiceFactory();
        if (factory != null) {
            return StringUtils.isBlank(name) ? factory.getDefault() : factory.getProvider(name);
        }
        return null;
    }

    // endregion

    // region 事件名称
    /**
     * 当应用程序启动时产生的事件。 event：ApplicationEventArgs
     */
    public static String STARTING = "starting";

    /**
     * 当应用程序启动后产生的事件。 event：ApplicationEventArgs
     */
    public static String STARTED = "started";

    /**
     * 当应用程序即将退出时产生的事件。 event：CancelEventArgs
     */
    public static String EXITING = "exiting";

    // endregion

    // region 启动/退出

    /**
     * 启动应用程序。
     * 
     * @param applicationContext 应用程序上下文实例。
     * @param args               启动参数。
     * 
     */
    public static void start(ApplicationContextBase applicationContext, String... args) {
        if (applicationContext == null) {
            throw new IllegalArgumentException("context");
        }

        if (isStarted) {
            return;
        }

        // 激发 "starting" 事件
        dispatchEvent(new ApplicationEventArgs(STARTING, applicationContext));

        try {
            // 保存应用程序上下文
            context = applicationContext;

            // 将应用上下文对象注册到默认服务容器中

            // if (context.getServiceFactory().getDefault() != null) {
            // context.getServiceFactory().getDefault().register("applicationContext",
            // applicationContext);
            // }

            // 初始化全局模块
            initializeGlobalModules(context);

            // 获取工作台对象
            WorkbenchBase workbench = (WorkbenchBase) context.getWorkbench(args);

            // 如果工作台对象不为空则运行工作台
            if (workbench != null) {
                // 挂载工作台打开事件
                workbench.addListener(workbench.OPENED, (e) -> {
                    // 标识应用程序启动完成
                    isStarted = true;
                    // 激发 "started" 事件
                    dispatchEvent(new ApplicationEventArgs(STARTED, context));
                }, null, false);

                // 挂载工作台关闭事件
                workbench.addListener(workbench.CLOSED, (e) -> {
                    exit();
                }, null, false);

                // 启动工作台
                workbench.open(args);
            }
        } catch (Exception ex) {
            // 重抛异常
            throw ex;
        }
    }

    /**
     * 关闭当前应用程序。
     * 
     * 
     */
    public static void exit() {

        // 如果上下文对象为空，则表示尚未启动
        if (context == null) {
            return;
        }

        // 重置启动标记
        isStarted = false;

        // 创建取消事件参数
        CancelEventArgs args = new CancelEventArgs(EXITING, null);

        // 激发 "exiting" 事件
        dispatchEvent(args);

        // 判断是否取消退出，如果是则退出
        if (args.getCancel()) {
            return;
        }

        // 关闭工作台
        if (context.getWorkbench() != null) {
            context.getWorkbench().close();
        }

        // 卸载全局模块
        disposeGlobalModules(context);

        // 释放应用程序上下文
        context = null;
    }

    // endregion

    // region 对象获取

    /**
     * 根据名称与对象提供器解析对象
     * 
     * @param name         名称
     * @param providerName 对象提供器解析
     * @param <T>          解析对象类型
     * @return 解析后对象 author：chendb 2016年12月9日 上午9:22:58
     */
    public static <T> T resolve(String name, String providerName) {
        ServiceProvider provider = getServiceProvider(providerName);
        assert provider != null;
        return provider.resolve(name);
    }

    /**
     * 根据名称解析对象
     * 
     * @param name 名称
     * @param <T>  解析对象类型
     * @return 解析后对象 author：chendb 2016年12月9日 上午9:22:58
     */
    public static <T> T resolve(String name) {
        ServiceProvider provider = getServiceProvider(null);
        assert provider != null;
        return provider.resolve(name);
    }

    public static <T> T resolve(Class<?> serviceType) {
        ServiceProvider provider = getServiceProvider(null);
        assert provider != null;
        return provider.resolve(serviceType);
    }

    public static <T> List<T> resolveAll(Class<?> serviceType) {
        ServiceProvider provider = getServiceProvider(null);
        assert provider != null;
        return provider.resolveAll(serviceType);
    }

    /**
     * 向容器中注册对象
     */
    public static void register(String name, Object service) {
        ServiceProvider provider = getServiceProvider(null);
        assert provider != null;
        provider.register(name, service);
    }

    // endregion

    // region 事件监听与触发

    public static void addListener(String type, Consumer<EventArgs> listener, Object scope, boolean once) {
        getEventProvider().addListener(type, listener, scope, once);
    }

    public static void removeListener(String type, Consumer<EventArgs> listener, Object scope) {
        getEventProvider().removeListener(type, listener, scope);
    }

    /**
     * 派发一个指定参数的事件。
     * 
     * @param eventArgs 事件参数实例。
     * 
     */
    public static void dispatchEvent(EventArgs eventArgs) {
        getEventProvider().dispatchEvent(eventArgs);
    }

    // endregion

    // region Module 调用
    private static void disposeGlobalModules(ApplicationContextBase context) {
        if(context!=null) {
            context.getModules().forEach(p -> {
                if (p != null) {
                    try {
                        p.close();
                    } catch (IOException e) {
                        throw new RuntimeException("关闭module异常", e);
                    }
                }
            });
        }
    }

    private static void initializeGlobalModules(ApplicationContextBase context) {
        context.getModules().forEach(p -> {
            if (p != null) {
                p.initialize(context);
            }
        });
    }
    // endregion

}