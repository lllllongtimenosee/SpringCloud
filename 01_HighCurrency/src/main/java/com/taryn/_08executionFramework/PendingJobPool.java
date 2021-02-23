package com.taryn._08executionFramework;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class PendingJobPool {

    private static final int THREAD_COUNTS=Runtime.getRuntime().availableProcessors();

    private static BlockingQueue<Runnable> blockingQueue=new ArrayBlockingQueue<>(7777);

    private static ExecutorService executorService=new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60, TimeUnit.SECONDS,blockingQueue);

    private static ConcurrentHashMap<String,JobInfo<?>> concurrentHashMap=new ConcurrentHashMap<>();

    private PendingJobPool(){}

    private static class InnerClass{
        public static PendingJobPool pendingJobPool=new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return InnerClass.pendingJobPool;
    }

    public static Map<String,JobInfo<?>> getMap(){
        return concurrentHashMap;
    }

    private static class PendingRunnable<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T data;

        public PendingRunnable(JobInfo<R> jobInfo, T data) {
            this.jobInfo = jobInfo;
            this.data = data;
        }

        @Override
        public void run() {
            TaskResult<R> taskResult=null;
            TaskProcessor<T,R> taskProcessor= (TaskProcessor<T, R>) jobInfo.getTaskProcessor();
            try {
                taskResult = taskProcessor.taskExecute(data);
                if (taskResult==null){
                    taskResult=new TaskResult<>(TaskResultType.Exception, null,"result is null");
                }
                if (taskResult.getTaskResultType()==null){
                    if (taskResult.getReason() == null) {
                        taskResult=new TaskResult<>(TaskResultType.Exception, null,"reason is null");
                    }else{
                        taskResult=new TaskResult<>(TaskResultType.Exception, null,"result and result is null");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                taskResult=new TaskResult<>(TaskResultType.Exception, null,e.getMessage());
            } finally {
                jobInfo.addTaskResult(taskResult, DaemonJobProcessor.getInstance());
            }
        }
    }

    public <T,R>  void registerJob(String jobKey,int jobLength,TaskProcessor<?,?> taskProcessor,long expireTime){
        JobInfo<R> jobInfo=new JobInfo(jobKey, jobLength,taskProcessor,expireTime);
        if (concurrentHashMap.putIfAbsent(jobKey, jobInfo)!=null){
            throw new RuntimeException(jobKey+"已注册！");
        }
    }


    private <R> JobInfo<R> getJob(String jobKey) {
        JobInfo<R> jobInfo = (JobInfo<R>) concurrentHashMap.get(jobKey);
        if (jobInfo==null){
            throw new RuntimeException(jobKey+"非法任务！");
        }
        return jobInfo;
    }

    public <T,R> void putJob(String jobKey,T data){
        JobInfo<R> jobInfo=getJob(jobKey);
        PendingRunnable<T,R> pendingRunnable=new PendingRunnable<>(jobInfo, data);
        executorService.execute(pendingRunnable);
    }

    public <R> List<TaskResult<R>> getTaskProgress(String jobKey){
        JobInfo<R> jobInfo=getJob(jobKey);
        return jobInfo.getTaskProgress();
    }

    public <R> String getTaskProcess(String jobKey){
        JobInfo<R> jobInfo=getJob(jobKey);
        return jobInfo.getTotalProcess();
    }
}
