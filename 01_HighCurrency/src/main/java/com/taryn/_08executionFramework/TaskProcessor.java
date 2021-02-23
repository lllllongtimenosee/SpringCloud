package com.taryn._08executionFramework;

public interface TaskProcessor <T,R>{

    TaskResult<R> taskExecute(T data);

}
