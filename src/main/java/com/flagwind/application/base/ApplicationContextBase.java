package com.flagwind.application.base;

import com.flagwind.application.ApplicationModule;
import com.flagwind.application.Workbench;
import com.flagwind.security.Principal;
import com.flagwind.services.base.DefaultServiceProviderFactory;
import com.flagwind.services.ServiceProviderFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 表示实现该抽象类的是一个应用程序上下文
 */
public abstract class ApplicationContextBase {

    /**
     * 应用程序唯一代号
     */
    private String applicationId;

    /**
     * 应用程序标题
     */
    private String title;

    /**
     * 应用程序的模块集合
     */
    private Set<ApplicationModule> modules;

    /**
     * 当前应用的状态字典
     */
    private HashMap<String, Object> states;

    /**
     * 工作台实例
     */
    private Workbench workbench;

    /**
     * 当前应用的安全主体
     */
    private Principal principal;

    /**
     * @return the applicationId
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the modules
     */
    public Set<ApplicationModule> getModules() {
        if (modules == null) {
            modules = new HashSet<>();
        }
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(Set<ApplicationModule> modules) {
        this.modules = modules;
    }

    /**
     * @return the states
     */
    public HashMap<String, Object> getStates() {
        if (states == null) {
            states = new HashMap<>();
        }
        return states;
    }

    /**
     * @param states the states to set
     */
    public void setStates(HashMap<String, Object> states) {
        this.states = states;
    }

    /**
     * @return the workbench
     */
    public Workbench getWorkbench(String... args) {
        if (workbench == null) {
            this.workbench = this.createWorkbench(args);
        }
        return workbench;
    }

    /**
     * @param workbench the workbench to set
     */
    public void setWorkbench(Workbench workbench) {
        this.workbench = workbench;
    }

    /**
     * @return the principal
     */
    public Principal getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    /**
      * 获取当前应用程序的服务管理对象。
      * @property
      * @returns IServiceProviderFactory
      */
    public ServiceProviderFactory getServiceFactory() {
        return DefaultServiceProviderFactory.getInstance();
    }

    /**
         * 创建一个主窗体对象。
         * 通常子类中实现的该方法只是创建空的工作台对象，并没有构建出该工作台下面的子构件。
         * 具体构建工作台子构件的最佳时机通常在 Workbench 类的 Open 方法内进行。
         * @abstract
         * @returns IWorkbench
         */
    protected abstract Workbench createWorkbench(String... args);

}
