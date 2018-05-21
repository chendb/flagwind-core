package com.flagwind.application.base;

import com.flagwind.application.Workbench;
import com.flagwind.application.WorkbenchStatus;
import com.flagwind.events.CancelEventArgs;
import com.flagwind.events.EventArgs;
import com.flagwind.events.EventProvider;
import org.apache.commons.lang3.StringUtils;


/**
 * 工作台基础类
 */
public abstract class WorkbenchBase extends EventProvider<EventArgs> implements Workbench {


    // region 成员变量
    private WorkbenchStatus status; // 工作台状态
    private String title; // 工作台标题
    private ApplicationContextBase applicationContext; // 应用程序上下文实例

    /**
     * 当工作台正在打开时产生的事件。
     *
     * event：EventArgs
     */
    public final String OPENING = "opening";

    /**
     * 当工作台被打开后产生的事件。
     *
     * event：EventArgs
     */
    public final String OPENED = "opened";

    /**
     * 当工作台正在取消激活时产生的事件。
     *
     * event：EventArgs
     */
    public final String DEACTIVATING = "deactivating";

    /**
     * 当工作台取消激活后产生的事件。
     *
     * event：EventArgs
     */
    public final String DEACTIVATED = "deactivated";

    /**
     * 当工作台正在激活时产生的事件。
     *
     * event：EventArgs
     */
    public final String ACTIVATING = "activating";

    /**
     * 当工作台正在关闭时产生的事件。
     *
     * event：CancelEventArgs
     */
    public final String CLOSING = "closing";

    /**
     * 当工作台被关闭后产生的事件。
     *
     * event：EventArgs
     */
    public final String CLOSED = "closed";

    /**
     * 当工作台标题被更改后产生的事件。
     *
     * event：EventArgs
     */
    public final String TITLE_CHANGED = "title_changed";

    // endregion

    public WorkbenchBase(String title) {
        super(null);
        this.title = title;
    }

    @Override
    public WorkbenchStatus getStatus() {
        return status;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        // 如果与之前的标题相等，则不处理
        if (StringUtils.equals(title, this.title)) {
            return;
        }

        this.title = title;

        // 通知标题被更改
        this.onTitleChanged();
    }

    /**
    * @return the applicationContext
    */
    public ApplicationContextBase getApplicationContext() {
        return applicationContext;
    }

    public WorkbenchBase(ApplicationContextBase applicationContext) {
        super(null);
        if (applicationContext == null) {
            throw new IllegalArgumentException();
        }

        this.status = WorkbenchStatus.Closed;
        this.title = applicationContext.getTitle();
        this.applicationContext = applicationContext;
    }

    @Override
    public void open(String... args) {
        // 如果工作台已经启动过则不处理
        if (this.status != WorkbenchStatus.Closed) {
            return;
        }

        try {
            // 通知工作台正在打开中
            this.onOpening();
        } catch (Exception ex) {
            // 注意：可能因为预打开事件处理程序或工作台构建过程出错，都必须重置工作台状态为"closed"
            this.status = WorkbenchStatus.Closed;

            // 重抛异常，导致后续的关闭代码不能继续
            throw ex;
        }

        try {
            // 调用虚拟方法以执行实际启动的操作
            this.onOpen(args);
        } catch (Exception ex) {
            // 注意：如果在实际启动操作中，子类可能已经通过 onOpened 方法设置了工作台状态为运行，则无需再重置工作台状态；
            // 否则必须重置工作台状态为"closed"
            if (this.status == WorkbenchStatus.Opening) {
                this.status = WorkbenchStatus.Closed;
            }

            // 重抛异常，导致后续的关闭代码不能继续，故而上面代码重置了工作台状态
            throw ex;
        }

        if (this.status == WorkbenchStatus.Opening) {
            // 通知工作台打开完成
            this.onOpened();
        }
    }

    @Override
    public boolean close() {
        // 保存原来的状态
        WorkbenchStatus originalStatus = this.status;

        // 如果工作台正在关闭或已经关闭，则直接退出
        if (originalStatus == WorkbenchStatus.Closing || originalStatus == WorkbenchStatus.Closed) {
            return false;
        }

        if (originalStatus == WorkbenchStatus.Opening) {
            throw new IllegalStateException("Opening");
        }

        // 创建 "closing" 事件
        CancelEventArgs args = new CancelEventArgs(CLOSING, null);

        try {
            this.onClosing(args);
        } catch (Exception ex) {
            // 注意：由于事件处理程序出错，必须还原工作台状态
            this.status = originalStatus;

            // 重抛异常，导致后续的关闭代码不能继续，故而上面代码重置了工作台状态
            throw ex;
        }

        // 如果事件处理程序要取消后续的关闭操作，则重置工作台状态
        if (args.getCancel() == true) {
            // 还原工作台状态
            this.status = originalStatus;

            // 因为取消关闭，所以退出后续关闭操作
            return false;
        }

        try {
            // 调用虚拟方法以进行实际的关闭操作
            this.onClose();
        } catch (Exception ex) {
            // 注意：如果在实际关闭操作中，子类可能已经通过 onClosed 方法设置了工作台状态为关闭，则无需再重置工作台状态；
            // 否则必须还原工作台状态
            if (this.status == WorkbenchStatus.Closing) {
                this.status = originalStatus;
            }

            // 重抛异常，导致后续的关闭代码不能继续，故而上面代码重置了工作台状态
            throw ex;
        }

        if (this.status != WorkbenchStatus.Closed) {
            // 通知工作台关闭完成
            this.onClosed();
        }

        // 返回成功
        return true;
    }

    public void onOpening() {
        // 更改工作台状态为"opening"
        this.status = WorkbenchStatus.Opening;

        // 激发工作台"opening"事件
        this.dispatchEvent(new EventArgs(this.OPENING, null));
    }

    public void onOpened() {
        // 更改工作台状态为"running"
        this.status = WorkbenchStatus.Running;

        // 激发工作台"opened"事件
        this.dispatchEvent(new EventArgs(this.OPENED, null));
    }

    public void onClosing(CancelEventArgs event) {
        // 设置工作台的状态为"closing"
        this.status = WorkbenchStatus.Closing;

        // 激发工作台"closing"事件
        this.dispatchEvent(event);
    }

    public void onClosed() {
        // 更改工作台状态为"closed"
        this.status = WorkbenchStatus.Closed;

        // 激发工作台"closed"事件
        this.dispatchEvent(new EventArgs(this.CLOSED, null));
    }

    public abstract void onOpen(String[] args);

    public abstract void onClose();

    protected void onDeactivateing() {
        // 设置工作台的状态为"deactivating"
        this.status = WorkbenchStatus.Deactivating;

        // 激发工作台"deactivating"事件
        this.dispatchEvent(new EventArgs(this.DEACTIVATING, null));
    }

    @Override
    public void deactivate() {

    }

    /**
        * 当工作台失去焦点后调用。
        * 
        * 
        * 
        */
    protected void onDeactivated() {
        // 设置工作台的状态为"deactivated"
        this.status = WorkbenchStatus.Deactivated;

        // 激发工作台"deactivated"事件
        this.dispatchEvent(new EventArgs(this.DEACTIVATED, null));
    }

    /**
    * 当工作台准备获得焦时调用。
    * 
    * 
    * 
    */
    protected void onActivating() {
        // 设置工作台的状态为"activating"
        this.status = WorkbenchStatus.Activating;

        // 激发工作台"activating"事件
        this.dispatchEvent(new EventArgs(this.ACTIVATING, null));
    }

    /**
     * 当工作台获得焦后调用。
     * 
     * 
     * 
     */
    protected void onActivated() {
        // 设置工作台的状态为"running"
        this.status = WorkbenchStatus.Running;
    }

    @Override
    public void activate() {

    }

    /**
    * 当工作台标题更改后调用。
    * 
    * 
    * 
    */
    protected void onTitleChanged() {
        // 激发工作台"titleChanged"事件
        this.dispatchEvent(new EventArgs(this.TITLE_CHANGED, null));
    }

}
