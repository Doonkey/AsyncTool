package com.dk.asynctoollib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dk.asynclib.TaskCallback;
import com.dk.asynclib.task.AbsTask;
import com.dk.asynclib.task.GroupTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTask();
    }

    /**
     * 情况1：在group2之前的任务，与group不分先后，谁先来就先执行
     * 情况2：在group2之后的任务，则先执行完了group2之后才会执行
     */
    private void addTask(){
        GroupTask group1 = new GroupTask("group1");
        GroupTask group2 = new GroupTask("Group2");
        GroupTask group3 = new GroupTask("Group3");
        AbsTask absTask1 = new AsyncTask1("task1");
        AbsTask absTask2 = new AsyncTask2("task2");
        AbsTask absTask3 = new AsyncTask3("task3");
        AbsTask absTask4 = new AsyncTask4("task4");
        AbsTask absTask5 = new AsyncTask5("task5");
        AbsTask absTask6 = new AsyncTask6("task6");
        AbsTask absTask7 = new AsyncTask7("task7");
        AbsTask absTask8 = new AsyncTask8("task8");
        AbsTask absTask9 = new AsyncTask9("task9");
        group1.addTask(absTask1, absTask2, absTask3, group2);
        group2.addTask(group3, absTask4, absTask5, absTask6);
        group3.addTask(absTask7, absTask8, absTask9);
        group1.start(new TaskCallback() {
            @Override
            public void onFinish() {
                Log.e("TAG", "AsyncTask=>任务全部完成");
            }
        });
    }
}