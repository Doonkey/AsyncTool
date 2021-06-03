package com.dk.asynclib.task;

import com.dk.asynclib.ThreadMode;

import java.util.concurrent.CountDownLatch;

public abstract class AbsGroupTask extends AbsTask{

    public AbsGroupTask(String taskName) {
        super(taskName);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) { }

    @Override
    public ThreadMode runThread() {
        return null;
    }
}
