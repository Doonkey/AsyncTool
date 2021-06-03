package com.dk.asynclib;

public class GroupTask extends AbsGroupTask{

    public GroupTask(String taskName) {
        super(taskName);
    }

    @Override
    public void addTask(AbsTask... task) {
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
