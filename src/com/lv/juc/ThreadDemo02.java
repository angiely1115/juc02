
package com.lv.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


class Res2 {
	public String userName;
	public String sex;
	public boolean flag = false;
}

class InputThread2 extends Thread {
	private Res2 res;
	private ReadWriteLock lock;
	Condition input = null;
	Condition out = null;
	public InputThread2(Res2 res,ReadWriteLock lock) {
		this.res = res;
		this.lock = lock;
		input = lock.writeLock().newCondition();
		//out = lock.readLock().newCondition();
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			lock.writeLock().lock();
			if (res.flag) {
				try {
					//res.wait();
					lock.writeLock().wait();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			if (count == 0) {
				res.userName = "吕荣砖";
				res.sex = "男";
			} else {
				res.userName = "赵雅芝";
				res.sex = "女";
			}
			count = (count + 1) % 2;
			res.flag = true;
			lock.writeLock().notify();
			lock.writeLock().unlock();
			//res.notify();
			}
	}
}

class OutThrad2 extends Thread {
	private Res2 res;
	private ReadWriteLock lock;
	Condition input = null;
	Condition out = null;
	public OutThrad2(Res2 res,ReadWriteLock lock) {
		this.res = res;
		this.lock = lock;
		input = lock.writeLock().newCondition();
		//out = lock.readLock().newCondition(); 读锁此方法就是抛异常
	}

	@Override
	public void run() {
		while (true) {
			lock.readLock().lock();
				if (!res.flag) {
					try {
						//res.wait();
						lock.readLock().wait();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				System.out.println(res.userName + "," + res.sex);
				res.flag = false;
				//input.signal();
				//res.notify();
			lock.readLock().notify();
			lock.writeLock().newCondition().signal();
			lock.readLock().unlock();
		}

	}
}

public class ThreadDemo02 {

	public static void main(String[] args) {
		Res2 res = new Res2();
		ReadWriteLock lock = new ReentrantReadWriteLock();
		InputThread2 inputThread = new InputThread2(res,lock);
		OutThrad2 outThrad = new OutThrad2(res,lock);
		inputThread.start();
		outThrad.start();
	}

}
