package com.lv.juc.kongzhongwang;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class Test3 {
      
        public static void main(String[] args) throws InterruptedException {
            SynchronousQueue<String> synchronousQueue = new SynchronousQueue();
            Semaphore semaphore = new Semaphore(1);
            //首先创建10个线程
            for(int i=0;i<10;i++){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            semaphore.acquire();
                            String input = synchronousQueue.take();
                            String output = TestDo.doSome(input);
                            System.out.println(Thread.currentThread().getName()+ ":" + output);
                            semaphore.release();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
              
            System.out.println("begin:"+(System.currentTimeMillis()/1000));  
            for(int i=0;i<10;i++){  //这行不能改动  
                String input = i+"";  //这行不能改动
                synchronousQueue.put(input);
            }
        }  
    }  
      
    //不能改动此TestDo类  
    class TestDo {  
        public static String doSome(String input){  
              
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            String output = input + ":"+ (System.currentTimeMillis() / 1000);  
            return output;  
        }  
    }  