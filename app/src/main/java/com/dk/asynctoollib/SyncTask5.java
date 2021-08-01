package com.dk.asynctoollib;

import android.os.SystemClock;

import com.dk.asynclib.SyncTaskAbs;
import com.dk.asynclib.TaskSubCallback;

import java.util.concurrent.CountDownLatch;

public class SyncTask5 extends SyncTaskAbs<String> {

    private String task7Response;

    public SyncTask5(String taskName, TaskSubCallback<String> callback) {
        super(taskName, callback);
    }

    @Override
    public <T> void collectData(T response) {
        task7Response = "，从已完成任务获取到的数据=》" + (String) response;
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName() + task7Response;
        super.startTask(countDownLatch, response);
    }
}
