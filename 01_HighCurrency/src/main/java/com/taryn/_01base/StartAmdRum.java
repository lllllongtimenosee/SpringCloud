package com.taryn._01base;

public class StartAmdRum {

    public static class UseThread extends Thread{

        @Override
        public void run() {
            int i=0;
            while (i<70){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I am "+Thread.currentThread().getName()
                +" and now the i="+i++);
            }
        }
    }

    public static void main(String[] args) {
        UseThread useThread=new UseThread();
        useThread.run();
        useThread.start();
    }
}
