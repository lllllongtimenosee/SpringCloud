package com.taryn._08executionFramework;

import java.util.concurrent.DelayQueue;

public class DaemonJobProcessor {

    private static DelayQueue<ItemVo<String>> delayQueue=new DelayQueue<>();

    private DaemonJobProcessor() {
    }

    private static class InnerClass{
        public static DaemonJobProcessor daemonJobProcessor=new DaemonJobProcessor();
    }

    public static DaemonJobProcessor getInstance() {
        return InnerClass.daemonJobProcessor;
    }

    private static class DelayRunnable implements Runnable{

        @Override
        public void run() {
            for(;;){
                try {
                    ItemVo<String> stringItemVo = delayQueue.take();
                    String date = stringItemVo.getDate();
                    PendingJobPool.getMap().remove(date);
                    System.out.println(date+"已过期!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void putJob(String jobKey, long expireTime) {
        ItemVo<String> stringItemVo = new ItemVo<>(expireTime, jobKey);
        delayQueue.offer(stringItemVo);
        System.out.println(jobKey+"已放入过期队列");
    }

    static {
        Thread thread = new Thread(new DelayRunnable());
        thread.setDaemon(true);
        thread.start();
        System.out.println("已开启守护线程!");
    }
}
