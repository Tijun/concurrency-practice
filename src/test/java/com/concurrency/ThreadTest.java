package com.concurrency;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ThreadTest {
    private static String stop = "1";

    private static volatile boolean stopVolatile = false;

    @Test
    public void stopThreadByInterrupt(){
        Runnable runnable = ()->{
            while (true){
                System.out.println("hello");
                System.out.println(Thread.currentThread().isAlive());
                TimeUtil.sleep(1L);
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    // 在接受到InterruptedException 后处理退出线程的逻辑，
                    // 此处使用break退出循环逻辑
                    System.out.println(e.getCause().getMessage());
                    System.out.println("break the thread");
                    break;
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        System.out.println("isInterrupted-----" + t.isInterrupted());
        TimeUtil.sleep(1L);
        //interrupt方法只是在当前线程中做一个线程停止的标记，并不真正停止线程(当线程中有sleep，wait,join等方法时会先清除中断状态，然后接受到InterruptedException)从而使线程退出
        //interrupted方法：测试当前线程是否已经中断,调用会清除线程的中断状态
        //isInterrupted方法：测试当前线程是否已经中断
        t.interrupt();
        TimeUtil.sleep(1L);
        // 线程是否alive
        System.out.println("isAlive-----" +  t.isAlive());

        TimeUtil.sleep(100L);

    }

    @Test
    public void stopByFlag(){
        new Thread(()->{
            // 这个线程可能永远不会退出
            while (ThreadTest.stop.equals("1")){
                System.out.println("hell");
                //TimeUtil.sleep(1L);
            }
        }).start();
        TimeUtil.sleep(2L);
        ThreadTest.stop = "0";
        System.out.println("stop flag true");
        TimeUtil.sleep(15L);
    }

    @Test
    public void stopByFlagVolatile(){
        new Thread(()->{
            while (!ThreadTest.stopVolatile){
                System.out.println("hell11");
                //TimeUtil.sleep(1L);
            }
        }).start();
        TimeUtil.sleep(2L);
        ThreadTest.stopVolatile = true;
        System.out.println("stop flag true");
        TimeUtil.sleep(100L);
    }
}
