package com.dk.asynclib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbsTask implements ITask{

    private ArrayList<AbsTask> mTasks;
    private CountDownLatch mCountDownLatch;
    private ExecutorService mExecutorService;
    private final String mTaskName;
    private TaskCallback mCallback;
    private AbsTask mParentTask;

    public abstract void startTask(CountDownLatch countDownLatch);
    public abstract ThreadMode runThread();

    public AbsTask(String taskName) {
        this.mTaskName = taskName;
    }

    @Override
    public void addTask(AbsTask... task) {
        mTasks = new ArrayList<>();
        mCountDownLatch = new CountDownLatch(task.length);
        mExecutorService = Executors.newFixedThreadPool(2);
        Collections.addAll(mTasks, task);
    }

    @Override
    public void start(TaskCallback callback){
        this.mCallback = callback;
        CountDownLatch countDownLatch = getCountDownLatch();
        getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                start(countDownLatch);
            }
        });
    }

    @Override
    public boolean hasTaskList(){
        return mTasks != null;
    }

    @Override
    public AbsTask[] getTaskList() {
        return mTasks == null ? new AbsTask[]{}: (AbsTask[]) mTasks.toArray();
    }

    @Override
    public CountDownLatch getCountDownLatch() {
        return mCountDownLatch;
    }

    @Override
    public ExecutorService getExecutorService() {
        return mExecutorService;
    }

    @Override
    public void onFinish() {
        if (getParent() == null) {
            if (mCallback != null){
                mCallback.onFinish();
            }
        }else {
            getParent().countDown();
        }
    }

    @Override
    public String getTaskName() {
        return this.mTaskName;
    }

    protected void setParent(AbsTask groupTask) {
        this.mParentTask = groupTask;
    }

    private AbsTask getParent(){
        return mParentTask;
    }

    private void countDown() {
        getCountDownLatch().countDown();
    }

    void start(CountDownLatch countDownLatch) {
        if (hasTaskList()){
            for (AbsTask task : mTasks) {
                if (task.hasTaskList()){
                    task.start(task.getCountDownLatch());
                }else {
                    task.start(countDownLatch);
                }
            }
            if (countDownLatch != null) {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onFinish();
            }
        }else {
            switch (runThread()){
                case SubThread:
                    ExecutorService thread = createThread();
                    thread.execute(() -> startTask(countDownLatch));
                    thread.shutdown();
                    break;
                case normalThread:
                    startTask(countDownLatch);
                    break;
            }
        }
    }

    ExecutorService createThread() {
        return Executors.newSingleThreadExecutor();
    }

    @Override
    public String toString() {
        if (!hasTaskList()){
            return getTaskName() + "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTaskName()).append("{");
        for (ITask task : mTasks) {
            stringBuilder.append(task.toString());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
