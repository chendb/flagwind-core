package com.flagwind.services.base;

import com.flagwind.events.EventArgs;
import com.flagwind.events.EventProvider;
import com.flagwind.services.Worker;
import com.flagwind.services.WorkerState;
import com.flagwind.services.WorkerStateChangedEventArgs;

public abstract class WorkerBase extends EventProvider<EventArgs> implements Worker {

    private String name;                                          // 工作器名称
    private WorkerState state;                                    // 工作器状态
    private boolean disabled;                                     // 是否被禁用
    private boolean canPauseAndContinue;                          // 是否能暂停恢复

    /**
     * 表示当工作器状态改变后产生的事件。
     *
     * @event WorkerStateChangedEventArgs
     */
    public static final String STATE_CHANGED = "stateChanged";

    public WorkerBase(String name) {
        super(null);
        this.name = name;
        this.state = WorkerState.Stopped;
        this.canPauseAndContinue = false;
        this.disabled = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public WorkerState getState() {
        return state;
    }

    @Override
    public boolean getDisabled() {
        return disabled;
    }

    @Override
    public void setDisabled(boolean value) {
        this.disabled = value;
    }

    @Override
    public boolean canPauseAndContinue() {
        return canPauseAndContinue;
    }

    /**
     * 启动工作器
     * @param args 启动的参数
     */
    @Override
    public void start(String... args) {
        if (this.disabled || this.state != WorkerState.Stopped) {
            return;
        }

        // 更新当前状态为“启动中”
        this.state = WorkerState.Starting;

        try {
            // 调用启动抽象方法，已执行实际的启动操作
            //await this.onStart(...args);

            // 更新当前状态为“运行中”
            this.state = WorkerState.Running;

            // 激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(WorkerBase.STATE_CHANGED, "start", WorkerState.Running, null));
        } catch (Exception ex) {
            this.state = WorkerState.Stopped;

            // 激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(WorkerBase.STATE_CHANGED, "start", WorkerState.Stopped, ex));

            throw ex;
        }

    }


    /**
     * 停止工作器
     * @param args 停止的参数
     */
    @Override
    public void stop(String... args) {

        if (disabled || state == WorkerState.Stopping || state == WorkerState.Stopped) {
            return;
        }

        //保存原来的状态
        WorkerState originalState = state;

        //更新当前状态为“正在停止中”
        state = WorkerState.Stopping;

        try {
            //调用停止抽象方法，以执行实际的停止操作
            this.onStop(args);

            //更新当前状态为已停止
            state = WorkerState.Stopped;

            //激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(STATE_CHANGED, "Stop", WorkerState.Stopped, null));
        } catch (Exception ex) {
            //还原状态
            state = originalState;

            //激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(STATE_CHANGED, "Stop", originalState, ex));

            throw ex;
        }
    }


    /**
     * 暂停工作器
     */
    @Override
    public void pause() {
        if (disabled || (!canPauseAndContinue)) {
            return;
        }

        if (state != WorkerState.Running) {
            return;
        }

        //保存原来的状态
        WorkerState originalState = state;

        //更新当前状态为“正在暂停中”
        state = WorkerState.Pausing;

        try {
            //执行暂停操作
            this.onPause();

            //更新当前状态为“已经暂停”
            state = WorkerState.Paused;

            //激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(STATE_CHANGED, "Pause", WorkerState.Paused, null));
        } catch (Exception ex) {
            //还原状态
            state = originalState;

            //激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(STATE_CHANGED, "Pause", originalState, ex));

            throw ex;
        }
    }


    /**
     * 恢复工作器，继续运行
     */
    @Override
    public void resume() {
        if (disabled || (!canPauseAndContinue)) {
            return;
        }

        if (state != WorkerState.Paused) {
            return;
        }

        //保存原来的状态
        WorkerState originalState = state;

        //更新当前状态为“正在恢复中”
        state = WorkerState.Resuming;

        try {
            //执行恢复操作
            this.onResume();

            state = WorkerState.Running;

            //激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(STATE_CHANGED, "Resume", WorkerState.Running, null));
        } catch (Exception ex) {
            //还原状态
            state = originalState;

            //激发“StateChanged”事件
            this.onStateChanged(new WorkerStateChangedEventArgs(STATE_CHANGED, "Resume", originalState, ex));

            throw ex;
        }
    }

    protected abstract void onStop(String...args);

    protected abstract void onPause();

    protected abstract void onResume();

    /**
     * 当工作器状态发生改变时调用
     *
     * @param eventArgs
     */
    private void onStateChanged(WorkerStateChangedEventArgs eventArgs) {
        this.dispatchEvent(new EventArgs(STATE_CHANGED, eventArgs));
    }
}

