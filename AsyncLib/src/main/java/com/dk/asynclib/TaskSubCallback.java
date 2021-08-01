package com.dk.asynclib;

public interface TaskSubCallback<T> extends TaskCallback{
    void onResponse(String taskName, T response);
}
