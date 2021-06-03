package com.dk.asynclib.task;


import com.dk.asynclib.ThreadMode;

public abstract class NormalThreadTask extends AbsTask {

    public NormalThreadTask(String taskName) {
        super(taskName);
    }

    @Override
    public ThreadMode runThread() {
        return ThreadMode.normalThread;
    }

}
