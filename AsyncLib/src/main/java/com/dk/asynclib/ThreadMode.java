package com.dk.asynclib;

public enum ThreadMode {
    SubThread,//任务完成后无需调用finish()

    normalThread//任务完成后需要手动调用finish()
}
