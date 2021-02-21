package com.taryn._01base.End;

public class EndThread {

    private static class UseThread extends Thread{
        public UseThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (!isInterrupted()){
                System.out.println(name+" is run!");
            }
            System.out.println(name+" interrupt flag is "+isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new UseThread("EndThread"));
        thread.start();
        Thread.sleep(1);
        thread.interrupt();    //new Thread(new UseThread("EndThread")) 包装后不会响应中断 因为Thread中this是包装的线程Thread-0
    }
}
