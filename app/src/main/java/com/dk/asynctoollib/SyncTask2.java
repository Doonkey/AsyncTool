package com.dk.asynctoollib;

import android.os.SystemClock;

import com.dk.asynclib.AbsTask;
import com.dk.asynclib.SyncTaskAbs;
import com.dk.asynclib.TaskSubCallback;

import java.util.concurrent.CountDownLatch;

public class SyncTask2 extends SyncTaskAbs<String> {

    public SyncTask2(String taskName, AbsTask dispatchTask, TaskSubCallback<String> callback) {
        super(taskName, dispatchTask, callback);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName();
        super.startTask(countDownLatch, response);
    }
}
