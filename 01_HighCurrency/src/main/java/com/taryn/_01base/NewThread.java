package com.taryn._01base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewThread {

    private static class UseThread extends Thread{
        @Override
        public void run() {
            System.out.println("I am extends Thread");
        }
    }

    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("I am implements Runnable");
        }

    }

    private static class UseCallable implements Callable {

        @Override
        public Object call() throws Exception {
            System.out.println("I am implements Callable");
            return "Taryn";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(new UseThread(),"Thread").start();
        new Thread(new UseRunnable(),"Runnable").start();
        FutureTask futureTask = new FutureTask<>(new UseCallable());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
