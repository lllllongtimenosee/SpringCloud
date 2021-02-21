package com.taryn._01base;

public class DaemonThread {

    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            try {
                for (;;){
                    System.out.println("I love feier");
                }
            } finally {
                System.out.println("finally..........");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new UseRunnable());
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(1000);
    }
}
