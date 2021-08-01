package com.dk.asynctoollib;

import android.os.SystemClock;

import com.dk.asynclib.SyncTaskAbs;
import com.dk.asynclib.TaskSubCallback;

import java.util.concurrent.CountDownLatch;

public class SyncTask3 extends SyncTaskAbs<String> {

    public SyncTask3(String taskName, TaskSubCallback<String> callback) {
        super(taskName, callback);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName();
        super.startTask(countDownLatch, response);
    }
}
