package com.taryn._01base.End;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class EndCallable {

    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static class UseCallable implements Callable{

        @Override
        public Object call() throws Exception {
            String name = Thread.currentThread().getName();
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(name+format.format(new Date()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(name+" interrupt   flag is "+Thread.currentThread().isInterrupted()+
                            " at "+format.format(new Date()));
                    Thread.currentThread().interrupt();
                    System.out.println(name+" interrupt() after flag is "+Thread.currentThread().isInterrupted()+
                            " at "+format.format(new Date()));
                    e.printStackTrace();
                }
            }
            System.out.println(name+" interrupt   flag is "+Thread.currentThread().isInterrupted()+
                    " at "+format.format(new Date()));
            return "Taryn";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FutureTask futureTask=new FutureTask(new UseCallable());
        Thread thread = new Thread(futureTask, "HasInterruptException");
        thread.start();
        Thread.sleep(5000);
        System.out.println("Main begin interrupt at "+format.format(new Date()));
        thread.interrupt();
    }


}
