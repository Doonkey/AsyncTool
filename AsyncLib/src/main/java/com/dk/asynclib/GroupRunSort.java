package com.dk.asynclib;

public class GroupRunSort {
    private boolean firstRun;
    private GroupTask groupTask;

    public GroupRunSort(boolean firstRun, GroupTask groupTask) {
        this.firstRun = firstRun;
        this.groupTask = groupTask;
    }

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    public GroupTask getGroupTask() {
        return groupTask;
    }

    public void setGroupTask(GroupTask groupTask) {
        this.groupTask = groupTask;
    }
}
