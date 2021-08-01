# 异步组件[![](https://jitpack.io/v/Doonkey/AsyncTool.svg)](https://jitpack.io/#Doonkey/AsyncTool)

### 组件说明

按组执行的异步任务

#### 导入依赖

Step 1. 添加JitPack仓库到根目录build.gradle文件

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. 在module添加依赖

dependencies {
	        implementation 'com.github.Doonkey:AsyncTool:[![](https://jitpack.io/v/Doonkey/AsyncTool.svg)](https://jitpack.io/#Doonkey/AsyncTool)'
	}

### 功能描述

> 组与组串行，组内遵循以下规则：  


> 1>.继承SubThreadTask实现组内并行，如（task7, task8, task9）  


> 2>.继承NormalThreadTask实现组内串行，task1 -> task2 -> task3  


> 3>.在group之前的任务，与group不分先后，谁先来就先执行；在group之后的任务，则先执行完了该group之后才会执行  


> 此例中执行顺序 task1 -> task2 ->task3 -> task4 -> task5 -> task6 -> (task7, task8, task9)；  


> 则可以在后面执行的组中的任务里获取前面执行组中的任务TaskSubCallback.onResponse()结果,  


> 如在absTask5可以获取task1,task2,task3,task4,但不能获取task6,task7,task8,task9  


> 注意TaskSubCallback.onFinish()表示当前组已经执行完毕并可能已经进入到下一个组任务；


### 使用

```Java
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
```


#### 串行实现：继承SyncTaskAbs

```Java
public class SyncTask1 extends SyncTaskAbs<String> {

    public SyncTask1(String taskName, TaskSubCallback<String> callback) {
        super(taskName, callback);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName();
        super.startTask(countDownLatch, response);
    }
}
```


#### 并行实现：继承AsyncTaskAbs

```Java
public class AsyncTask7 extends AsyncTaskAbs<String> {

    public AsyncTask7(String taskName, TaskSubCallback<String> callback) {
        super(taskName, callback);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName();
        super.startTask(countDownLatch, response);
    }
}
```

#### 任务结果传递见SyncTask2与SyncTask5代码实现

```Java
public class SyncTask2 extends SyncTaskAbs<String> {

    public SyncTask2(String taskName, AbsTask dispatchTask, TaskSubCallback<String> callback) {
        super(taskName, dispatchTask, callback);
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName();
        super.startTask(countDownLatch, response);
    }
}
```


```Java
public class SyncTask5 extends SyncTaskAbs<String> {

    private String task7Response;

    public SyncTask5(String taskName, TaskSubCallback<String> callback) {
        super(taskName, callback);
    }

    @Override
    public <T> void collectData(T response) {
        task7Response = "，从已完成任务获取到的数据=》" + (String) response;
    }

    @Override
    public void startTask(CountDownLatch countDownLatch) {
        SystemClock.sleep(2000);
        String response = "AsyncTask=>"+getTaskName() + task7Response;
        super.startTask(countDownLatch, response);
    }
}
```


在SyncTask2中传递需要结果的SyncTask5的实例，然后在SyncTask5实现collectData（T response）方法，强转为SyncTask2结果的类型使用。

#### 重要的事情说3遍：

在startTask（CountDownLatch countDownLatch）函数中完成任务都需手动调用super.startTask(countDownLatch, response);

在startTask（CountDownLatch countDownLatch）函数中完成任务都需手动调用super.startTask(countDownLatch, response);

在startTask（CountDownLatch countDownLatch）函数中完成任务都需手动调用super.startTask(countDownLatch, response);



