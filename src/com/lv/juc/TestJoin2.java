package com.lv.juc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestJoin2 {
    //还可以通过Condition来控制
    private static   int number = 1; //当前正在执行线程的标记
    static  ReentrantLock reentrantLock = new ReentrantLock();
    static  final Condition condition1 = reentrantLock.newCondition();
    static final Condition condition2 = reentrantLock.newCondition();
    static final Condition condition3 = reentrantLock.newCondition();
    public static synchronized Thread getThread1(){
        return new Thread(()->{
            reentrantLock.lock();
            if(number!=1){
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t1:1");
            number=2;
            condition2.signal();
            reentrantLock.unlock();
        });
    }

    public static Thread getThread2(){
        return new Thread(()->{
            reentrantLock.lock();
            try {
                if (number!=2){
                    System.out.println("thread....2");
                    condition2.await();
                }
                System.out.println("t2:1");
                number=3;
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("t2 锁释放");
                reentrantLock.unlock();
            }

        });
    }

    public static Thread getThread3(){
        return new Thread(()->{
            reentrantLock.lock();
            try {
                if(number!=3){
                    System.out.println("thread。。。3");
                    condition3.await();
                }
                System.out.println("t3:1");
                number=1;
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("t3 锁释放");
                reentrantLock.unlock();
            }

        });
    }

    public static void main(String[] args) throws InterruptedException {
        //通过join来控制线程的执行顺序
        Thread thread =   getThread1();
        Thread thread2 =   getThread2();
        Thread thread3 =   getThread3();
      /*
        thread.start();
        thread.join();
        thread3.start();
        thread3.join();
        thread2.start();*/
        //通过创建只有一个线程的线程池来控制，该线程的里面的队列是FIFO 先进先出
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
       /* executorService.submit(thread);
        executorService.submit(thread2);
        executorService.submit(thread3);
        executorService.shutdown();*/
        thread.start();
        thread2.start();
        thread3.start();





    }

}
