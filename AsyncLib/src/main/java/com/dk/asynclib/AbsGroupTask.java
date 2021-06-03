package com.dk.asynclib;

import java.util.concurrent.CountDownLatch;

abstract class AbsGroupTask extends AbsTask{

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
