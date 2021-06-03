package com.dk.asynclib.task;

import com.dk.asynclib.ThreadMode;

public abstract class SubThreadTask extends AbsTask {

    public SubThreadTask(String taskName) {
        super(taskName);
    }

    @Override
    public ThreadMode runThread() {
        return ThreadMode.SubThread;
    }
}
