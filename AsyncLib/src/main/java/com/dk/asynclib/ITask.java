package com.dk.asynclib;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public interface ITask {
    void addTask(AbsTask... task);
    void start(TaskCallback callback);
    boolean hasTaskList();
    AbsTask[] getTaskList();
    CountDownLatch getCountDownLatch();
    ExecutorService getExecutorService();
    void onFinish();
    String getTaskName();
}