package com.dk.asynclib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbsTask implements ITask{

    private ArrayList<AbsTask> mTasks;
    private final ExecutorService mExecutorService;
    private final String mTaskName;
    private CountDownLatch mCountDownLatch;
    private TaskCallback mCallback;
    private AbsTask mParentTask;

    public void startTask(CountDownLatch countDownLatch){
        if (countDownLatch != null){
            countDownLatch.countDown();
        }
    }

    public ThreadMode runThread(){
        return ThreadMode.normalThread;
    }

    public AbsTask(String taskName) {
        this.mTaskName = taskName;
        mExecutorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void addTask(AbsTask... task) {
        if (mTasks == null){
            mTasks = new ArrayList<>();
        }
        Collections.addAll(mTasks, task);
        mCountDownLatch = new CountDownLatch(mTasks.size());
    }

    @Override
    public void start(TaskCallback callback){
        this.mCallback = callback;
        getExecutorService().execute(() -> start(getCountDownLatch()));
    }

    @Override
    public boolean hasTaskList(){
        return mTasks != null;
    }

    @Override
    public ArrayList<AbsTask> getTaskList() {
        return hasTaskList() ? mTasks: new ArrayList<>();
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
            if (getGroupCallback() != null){
                getGroupCallback().onFinish(getTaskName());
            }
            if (mCallback != null){
                mCallback.onFinish(getTaskName());
            }
        }else {
            getParent().countDown();
            if (getGroupCallback() != null){
                getGroupCallback().onFinish(getTaskName());
            }
        }
    }

    @Override
    public String getTaskName() {
        return this.mTaskName;
    }

    protected void setParent(AbsTask groupTask) {
        this.mParentTask = groupTask;
    }

    protected AbsTask getParent(){
        return mParentTask;
    }

    private TaskCallback mGroupCallback;
    protected void setGroupCallback(TaskCallback mTaskCallback){
        this.mGroupCallback = mTaskCallback;
    }

    private TaskCallback getGroupCallback(){
        return mGroupCallback;
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

}
