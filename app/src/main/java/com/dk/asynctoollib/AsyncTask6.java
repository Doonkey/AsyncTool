package com.dk.asynctoollib;

import android.os.SystemClock;
import android.util.Log;

import com.dk.asynclib.task.SubThreadTask;

import java.util.concurrent.CountDownLatch;

public class AsyncTask6 extends SubThreadTask {

    public AsyncTask6(String taskName) {
        super(taskName);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(3000);
        Log.e("TAG", "AsyncTask=>"+getTaskName());
        if (countDownLatch != null){
            countDownLatch.countDown();
        }
    }
}
