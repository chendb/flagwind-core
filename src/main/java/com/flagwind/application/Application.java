package com.flagwind.application;

import com.flagwind.events.CancelEventArgs;
import com.flagwind.events.EventArgs;
import com.flagwind.events.EventProvider;
import java.util.function.Consumer;

public class Application {
    private static boolean isStarted = false; // 标识应用程序是否启动完成
    private static ApplicationContextBase context = null; // 应用程序上下文实例
    private static EventProvider eventProvider; // 事件提供程序

    /**
    * 获取一个事件提供程序实例。
    * @private
    * @property
    * @returns IEventProvider
    */
    public static EventProvider getEventProvider() {
        if (eventProvider == null) {
            eventProvider = new EventProvider(null);
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

    /**
       * 当应用程序启动时产生的事件。
       * @event ApplicationEventArgs
       */
    public static String STARTING = "starting";

    /**
     * 当应用程序启动后产生的事件。
     * @event ApplicationEventArgs
     */
    public static String STARTED = "started";

    /**
     * 当应用程序即将退出时产生的事件。
     * @event CancelEventArgs
     */
    public static String EXITING = "exiting";

    /**
     * 启动应用程序。
     * @static
     * @param  {ApplicationContextBase} context 应用程序上下文实例。
     * @param  {Array<string>} args 启动参数。
     * @returns void
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
            context.getServiceFactory().getDefault().register("applicationContext", applicationContext);

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

    public static void addListener(String type, Consumer<EventArgs> listener, Object scope, boolean once) {
        eventProvider.addListener(type, listener, scope, once);
    }

    public static void removeListener(String type, Consumer<EventArgs> listener, Object scope) {
        eventProvider.removeListener(type, listener, scope);
    }

    /**
         * 关闭当前应用程序。
         * @static
         * @returns void
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

    /**
    * 派发一个指定参数的事件。
    * @param  {EventArgs} eventArgs 事件参数实例。
    * @returns void
    */
    public static void dispatchEvent(EventArgs args) {
        eventProvider.dispatchEvent(args);
    }

    private static void disposeGlobalModules(ApplicationContextBase context) {
        context.getModules().forEach(p -> {
            if (p != null) {
                p.dispose();
            }
        });
    }

    private static void initializeGlobalModules(ApplicationContextBase context) {
        context.getModules().forEach(p -> {
            if (p != null) {
                p.initialize(context);
            }
        });
    }

}