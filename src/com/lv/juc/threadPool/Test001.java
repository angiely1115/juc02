
package com.lv.juc.threadPool;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test001 {



	@Test
	public void test01(){
		//3. �ɶ�ʱ�̳߳�
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3);
		for (int i = 0; i < 10; i++) {
			System.out.println("popo");
			int temp = i;
			newScheduledThreadPool.schedule(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + ",inewScheduledThreadPool:" + temp);
				}
			},3,TimeUnit.SECONDS);
		}
	}
	@Test
	public void test02(){
		// 1.�ɻ�����̳߳� �ظ�����
		ExecutorService newCachedThreadPool =
				Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			int temp = i;
			newCachedThreadPool.submit(new Runnable() {

				@Override
				public void run() {
					System.out.println("threadName:" + Thread.currentThread().getName() +
							",i:" + temp);
				}
			});
		}
	}
	@Test
	public void test03(){
		// 2.�ɹ̶������̳߳�
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			int temp = i;
			newFixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + ",i:" + temp);
				}
			});
		}
	}
	@Test
	public  void test04() {
		//4.���߳�
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	    for (int i = 0; i < 10; i++) {
	    	int temp=i;

	    	newSingleThreadScheduledExecutor.execute(new Runnable(){
			
				@Override
				public void run() {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						// TODO: handle exception
					}
		
				   try {
					   int j =1/0;
				} catch (Exception e) {
					e.printStackTrace();
				}
					System.out.println(Thread.currentThread().getName() + ",i:" + temp);
					    
				}
			});;
		}
	  newSingleThreadScheduledExecutor.shutdown();
	}
}
