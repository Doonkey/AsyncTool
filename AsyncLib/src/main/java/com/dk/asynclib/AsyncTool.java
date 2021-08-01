package com.dk.asynclib;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class AsyncTool {

    private static final AsyncTool asyncTool = new AsyncTool();

    public static AsyncTool get(){
        return asyncTool;
    }

    private final LinkedList<GroupRunSort> groupTaskArrayList = new LinkedList<>();

    private int subTaskIndex;

    /**
     *
     * @param task 当前组内任务
     */
    public AsyncTool addTaskGroup(AbsTask... task){
        return addTaskGroup(false, task);
    }

    /**
     *
     * @param gFirstRun true 设置下一组任务在本组之前执行，默认false
     * @param task 当前组内任务
     */
    public AsyncTool addTaskGroup(boolean gFirstRun, AbsTask... task){
        return addTaskGroup(null, gFirstRun, task);
    }

    /**
     *
     * @param callback 当前组任务全部完成回调
     * @param gFirstRun true 设置下一组任务在本组之前执行，默认false
     * @param task 当前组内任务
     */
    public AsyncTool addTaskGroup(TaskSubCallback<String> callback, boolean gFirstRun, AbsTask... task){
        GroupTask groupSubTask = new GroupTask("groupTask" + subTaskIndex, callback);
        groupSubTask.addTask(task);
        groupTaskArrayList.add(new GroupRunSort(gFirstRun, groupSubTask));
        subTaskIndex ++;
        return this;
    }

    public void start(TaskCallback callback){
        for (int i = 0; i < groupTaskArrayList.size(); i++) {
            if (i + 1 >= groupTaskArrayList.size()){
                break;
            }
            GroupRunSort groupRunSort = groupTaskArrayList.get(i);
            ArrayList<AbsTask> taskList = groupRunSort.getGroupTask().getTaskList();
            if (groupRunSort.isFirstRun()){
                taskList.add(0, groupTaskArrayList.get(i + 1).getGroupTask());
            }else {
                taskList.add(groupTaskArrayList.get(i + 1).getGroupTask());
            }
        }
        groupTaskArrayList.get(0).getGroupTask().start(callback);
    }


}
