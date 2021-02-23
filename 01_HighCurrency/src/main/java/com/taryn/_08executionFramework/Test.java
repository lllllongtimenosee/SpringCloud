package com.taryn._08executionFramework;

import java.util.List;
import java.util.Random;

public class Test {

    private final static String jobKey="taryn";
    private final static int jobLength=1000;
    private final static long expireTime=5000;

    private static class QueryRunnable implements Runnable{

        private PendingJobPool pendingJobPool;

        public QueryRunnable(PendingJobPool pendingJobPool) {
            this.pendingJobPool = pendingJobPool;
        }

        @Override
        public void run() {
            int i=0;
            while (i<300){
                List<TaskResult<Object>> taskProgress = pendingJobPool.getTaskProgress(jobKey);
                if (!taskProgress.isEmpty()){
                    System.out.println(pendingJobPool.getTaskProcess(jobKey));
                    System.out.println(taskProgress);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    private static class MyTask implements TaskProcessor<Integer,Integer>{
        @Override
        public TaskResult<Integer> taskExecute(Integer data) {
            Random random = new Random();
            int flag= random.nextInt(500);
            try {
                Thread.sleep(flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (flag<=300){
                return new TaskResult<Integer>(TaskResultType.Success,data);
            }else if(flag>300 && flag<=3400){
                return new TaskResult<Integer>(TaskResultType.Failure,-777,"Failure");
            }else{
                try {
                    throw new RuntimeException("发生未知异常！");
                } catch (RuntimeException e) {
                    return new TaskResult<Integer>(TaskResultType.Exception,-888,"Exception");
                }
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask=new MyTask();
        PendingJobPool pendingJobPool = PendingJobPool.getInstance();
        pendingJobPool.registerJob(jobKey, jobLength, myTask, expireTime);
        Random random = new Random();
        for (int i=0;i<jobLength;i++){
            pendingJobPool.putJob(jobKey, random.nextInt(500));
        }
        new Thread(new QueryRunnable(pendingJobPool)).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
