package com.dk.asynclib;

import java.util.concurrent.CountDownLatch;

public abstract class AsyncTaskAbs<T> extends AbsTask {

    private TaskSubCallback<T> mCallback;
    private AbsTask mDispatchTask;

    public AsyncTaskAbs(String taskName) {
        super(taskName);
    }
    public AsyncTaskAbs(String taskName, TaskSubCallback<T> callback) {
        super(taskName);
        this.mCallback = callback;
    }
    public AsyncTaskAbs(String taskName, AbsTask dispatchTask) {
        super(taskName);
        this.mDispatchTask = dispatchTask;
    }
    public AsyncTaskAbs(String taskName, AbsTask dispatchTask, TaskSubCallback<T> callback) {
        super(taskName);
        this.mCallback = callback;
        this.mDispatchTask = dispatchTask;
    }

    @Override
    public ThreadMode runThread() {
        return ThreadMode.SubThread;
    }

    protected void startTask(CountDownLatch countDownLatch, T response){
        if (this.mDispatchTask != null){
            this.mDispatchTask.collectData(response);
        }
        if (mCallback != null){
            mCallback.onResponse(getTaskName(), response);
        }
        super.startTask(countDownLatch);
        if (mCallback != null){
            mCallback.onFinish(getTaskName());
        }
    }
}
