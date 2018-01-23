
package com.flagwind.application;

import com.flagwind.application.base.ApplicationContextBase;
import com.flagwind.events.EventArgs;

/**
 * 应用程序事件参数类。
 * @author chendb
 */
public class ApplicationEventArgs extends EventArgs {
    /**
     * 获取应用程序上下文实例。
     * 
     */
    private ApplicationContextBase context;

    /**
     * 初始化应用程序事件参数类的新实例。
     * @param  type 事件类型。
     * @param  {ApplicationContextBase} context 应用程序上下文实例。
     */
    public ApplicationEventArgs(String type, ApplicationContextBase context) {
        super(type, null);

        this.context = context;
    }

	/**
	 * @return the context
	 */
	public ApplicationContextBase getContext() {
		return context;
	}

}