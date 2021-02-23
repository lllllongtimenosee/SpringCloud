package com.taryn._08executionFramework;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class JobInfo<R> {

    private final String jobKey;

    private final int jobLength;

    private final TaskProcessor<?,R> taskProcessor;

    private final LinkedBlockingDeque<TaskResult<R>> linkedBlockingDeque;

    private final long expireTime;

    private final AtomicInteger successCount;

    private final AtomicInteger processorCount;

    public JobInfo(String jobKey, int jobLength, TaskProcessor<?, R> taskProcessor,long expireTime) {
        this.jobKey = jobKey;
        this.jobLength = jobLength;
        this.taskProcessor = taskProcessor;
        this.linkedBlockingDeque = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
        this.successCount = new AtomicInteger(0);
        this.processorCount = new AtomicInteger(0);
    }

    public TaskProcessor<?, R> getTaskProcessor() {
        return taskProcessor;
    }

    public String getTotalProcess() {
        return "Success["+successCount.get()+"]/Current["+processorCount.get()+"]   Total["+jobLength+"]";
    }

    public List<TaskResult<R>> getTaskProgress(){
        List<TaskResult<R>> taskResults=new LinkedList<>();
        TaskResult<R> taskResult;
        while ((taskResult=linkedBlockingDeque.pollFirst())!=null){
            taskResults.add(taskResult);
        }
        return taskResults;
    }

    public  void addTaskResult(TaskResult<R> taskResult,DaemonJobProcessor daemonJobProcessor){
        if (TaskResultType.Success.equals(taskResult.getTaskResultType())){
            successCount.incrementAndGet();
        }
        processorCount.incrementAndGet();
        linkedBlockingDeque.add(taskResult);
        if (processorCount.get()==jobLength){
            daemonJobProcessor.putJob(jobKey,expireTime);
        }
    }
}
