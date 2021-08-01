package com.dk.asynclib;

public class GroupTask extends AbsTask{

    private TaskCallback mTaskCallback = null;

    public GroupTask(String taskName) {
        super(taskName);
    }

    public GroupTask(String taskName, TaskCallback callback) {
        super(taskName);
        this.mTaskCallback = callback;
    }

    @Override
    public void addTask(AbsTask... task) {
        setGroupCallback(mTaskCallback);
        for (AbsTask absTask : task) {
            if (absTask instanceof GroupTask){
                absTask.setParent(this);
            }
        }
        super.addTask(task);
    }

    @Override
    public void start(TaskCallback callback) {
        super.start(callback);
    }
}
