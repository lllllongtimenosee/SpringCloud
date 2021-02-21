package com.taryn._01base;

public class SynClzAndInst {

    private static class SynClass implements Runnable{
        @Override
        public void run() {
            System.out.println("TestClass is running......");
            synClass();
        }

    }

    private static class SynInstance implements Runnable{
        private SynClzAndInst synInstance;
        public SynInstance(SynClzAndInst synInstance) {
            this.synInstance = synInstance;
        }
        @Override
        public void run() {
            System.out.println("SynInstance is running......");
            synInstance.synInstance();
        }
    }

    private static class SynInstance2 implements Runnable{
        private SynClzAndInst synInstance;
        public SynInstance2(SynClzAndInst synInstance) {
            this.synInstance = synInstance;
        }
        @Override
        public void run() {
            System.out.println("SynInstance is running......");
            synInstance.synInstance2();
        }
    }

    private static synchronized void synClass() {
        try {
            Thread.sleep(3000);
            System.out.println("synClass is going...");
            Thread.sleep(3000);
            System.out.println("synClass end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void  synInstance() {
        try {
            Thread.sleep(3000);
            System.out.println("synInstance is going..."+this.toString());
            Thread.sleep(3000);
            System.out.println("synInstance end"+this.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void  synInstance2() {
        try {
            Thread.sleep(3000);
            System.out.println("synInstance2 is going..."+this.toString());
            Thread.sleep(3000);
            System.out.println("synInstance2 end"+this.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SynClzAndInst synClzAndInst=new SynClzAndInst();
        new Thread(new SynInstance(synClzAndInst)).start();
        new Thread(new SynInstance2(synClzAndInst)).start();
        new Thread(new SynClass()).start();
        new Thread(new SynClass()).start();
    }
}
