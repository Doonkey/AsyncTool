package com.dk.asynctoollib;

import android.os.SystemClock;
import android.util.Log;

import com.dk.asynclib.SubThreadTask;

import java.util.concurrent.CountDownLatch;

public class AsyncTask7 extends SubThreadTask {

    public AsyncTask7(String taskName) {
        super(taskName);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(1000);
        Log.e("TAG", "AsyncTask=>"+getTaskName());
        if (countDownLatch != null){
            countDownLatch.countDown();
        }
    }
}
