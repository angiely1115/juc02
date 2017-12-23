
package com.lv.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test007 {

	public static void main(String[] args) {
		//通过Condition来实现
		Lock lock = new ReentrantLock();
		Condition cd1 = lock.newCondition();
		Condition cd2 = lock.newCondition();
		Condition cd3 = lock.newCondition();
		Thread t1 = new Thread(new Runnable() {


			@Override
			public void run() {
				lock.lock();
				for (int i = 0; i < 20; i++) {
					System.out.println("T1,i:" + i);
				}

				cd2.signal();
				lock.unlock();
			}

		});
		t1.start();
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					cd2.await();
				} catch (Exception e) {
					// TODO: handle exception
				}
				lock.lock();
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(30);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("T2,i:" + i);
				}
				cd3.signal();
				lock.unlock();
			}
		});

		t2.start();
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					cd3.await();
				} catch (Exception e) {
					// TODO: handle exception
				}
				lock.lock();
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(30);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("T3,i:" + i);
				}
				lock.unlock();
			}
		});

		t3.start();
	}

}
