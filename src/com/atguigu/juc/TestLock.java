package com.atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 一、用于解决多线程安全问题的方式：
 * 
 * synchronized:隐式锁
 * 1. 同步代码块
 * 
 * 2. 同步方法
 * 
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class TestLock {
	
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		new Thread(ticket, "1号窗口").start();
		new Thread(ticket, "2号窗口").start();
		new Thread(ticket, "3号窗口").start();
	}

}

class Ticket implements Runnable{
	
	private int tick = 100;

	private Lock lock = new ReentrantLock();

	public void tick(){
		while (tick>0) {
			//System.out.println(Thread.currentThread().getName());
			lock.lock();//上锁
			if(tick>0){
				System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);

				try {
					Thread.sleep(20);



				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					lock.unlock();//释放锁
				}

			}

		}
	}

	public synchronized void tick2(){
		if(tick>0){
			System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public  void tick3(){
		while (tick>0) {
			synchronized(this) {
				if (tick > 0) {
					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	@Override
	public void run() {
		//tick();
		while (tick>0) {

			tick2();
		}
		//tick3();
	}

	/*@Override
	public void run() {
		while(tick > 0){
			
			lock.lock(); //上锁 体验下在方法体里面加锁
			
			try{
				if(tick > 0){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
					}
					//tick();
					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				}
			}finally{
				lock.unlock(); //释放锁
			}
		}
	}
	*/
}