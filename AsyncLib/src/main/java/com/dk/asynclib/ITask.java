package com.dk.asynclib;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public interface ITask {
    default <T> void collectData(T response){}
    void addTask(AbsTask... task);
    void start(TaskCallback callback);
    boolean hasTaskList();
    ArrayList<AbsTask> getTaskList();
    CountDownLatch getCountDownLatch();
    ExecutorService getExecutorService();
    void onFinish();
    String getTaskName();
}
