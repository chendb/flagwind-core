package com.flagwind.runtime;

public interface Disposable {
    /**
     * 执行与释放或重置非托管资源关联的应用程序定义的任务。
     *
     * @returns void
     */
    void dispose();
}
