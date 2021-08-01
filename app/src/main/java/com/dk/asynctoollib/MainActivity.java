package com.dk.asynctoollib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dk.asynclib.AbsTask;
import com.dk.asynclib.AsyncTool;
import com.dk.asynclib.TaskCallback;
import com.dk.asynclib.TaskSubCallback;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asyncTool();
    }

    /**
     * 组与组串行，组内遵循以下规则：
     *  1>.继承SubThreadTask实现组内并行，如（task7, task8, task9）
     *  2>.继承NormalThreadTask实现组内串行，task1 -> task2 -> task3
     *  3>.在group之前的任务，与group不分先后，谁先来就先执行；在group之后的任务，则先执行完了该group之后才会执行
     * 此例中执行顺序 task1 -> task2 ->task3 -> task4 -> task5 -> task6 -> (task7, task8, task9)；
     * 则可以在后面执行的组中的任务里获取前面执行组中的任务TaskSubCallback.onResponse()结果,
     * 如在absTask5可以获取task1,task2,task3,task4,但不能获取task6,task7,task8,task9
     * 注意TaskSubCallback.onFinish()表示当前组已经执行完毕并可能已经进入到下一个组任务；
     */
    private void asyncTool(){
        AbsTask task4 = new SyncTask4("task4", subCallback);
        AbsTask task5 = new SyncTask5("task5", subCallback);
        AbsTask task6 = new SyncTask6("task6", subCallback);

        AbsTask task1 = new SyncTask1("task1", subCallback);
        AbsTask task2 = new SyncTask2("task2", task5, subCallback);
        AbsTask task3 = new SyncTask3("task3", subCallback);

        AbsTask task7 = new AsyncTask7("task7", subCallback);
        AbsTask task8 = new AsyncTask8("task8", subCallback);
        AbsTask task9 = new AsyncTask9("task9", subCallback);
        AsyncTool.get().addTaskGroup(task1, task2, task3)
                .addTaskGroup(task4, task5, task6)
                .addTaskGroup(task7, task8, task9)
                .start(new TaskCallback() {
                    @Override
                    public void onFinish(String taskName) {
                        System.out.println("AsyncTask=>任务全部完成" + taskName);
                    }
                });
    }

    private final TaskSubCallback<String> subCallback =new TaskSubCallback<String>() {
        @Override
        public void onResponse(String taskName, String response) {
            System.out.println("response=>" + response);
        }

        @Override
        public void onFinish(String taskName) {
            //System.out.println("response onFinish" + taskName);
        }
    };

    private static final TaskCallback taskCallback = new TaskCallback() {

        @Override
        public void onFinish(String taskName) {
            System.out.println("response=>任务完成" + taskName);
        }
    };
}