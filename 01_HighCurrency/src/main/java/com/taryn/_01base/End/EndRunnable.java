package com.taryn._01base.End;

public class EndRunnable {

    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(name+" is run!");
            }
            System.out.println(name+" interrupt flag is "+Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new UseRunnable(),"EndRunnable");
        thread.start();
        Thread.sleep(1);
        thread.interrupt();    //new Thread(new UseThread("EndThread")) 包装后会响应中断 因为Thread中this是本线程EndRunnable
    }
}
