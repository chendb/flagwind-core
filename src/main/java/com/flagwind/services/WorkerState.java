package com.flagwind.services;

/**
 * 工作者状态
 * 
 * author：chendb
 * date：2016年12月9日 上午11:02:36
 */
public enum WorkerState {

    /// <summary>未运行/已停止。</summary>
    Stopped,

    /// <summary>运行中。</summary>
    Running,

    /// <summary>正在启动中。</summary>
    Starting,

    /// <summary>正在停止中。</summary>
    Stopping,

    /// <summary>正在暂停中。</summary>
    Pausing,

    /// <summary>暂停中。</summary>
    Paused,

    /// <summary>正在恢复中。</summary>
    Resuming,
}
