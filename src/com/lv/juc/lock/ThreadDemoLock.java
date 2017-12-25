
package com.lv.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Res {
	public String userName;
	public String sex;
	public boolean flag = false;
	Lock lock = new ReentrantLock();
}

class InputThread extends Thread {
	private Res res;
	Condition newCondition;
	public InputThread(Res res,	Condition newCondition) {
		this.res = res;
		this.newCondition=newCondition;
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			// synchronized (res) {

			try {
				res.lock.lock();
				if (res.flag) {
					try {
//						res.wait();
						newCondition.await();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (count == 0) {
					res.userName = "赵雅芝";
					res.sex = "女";
				} else {
					res.userName = "吕荣砖";
					res.sex = "男";
				}
				count = (count + 1) % 2;
				res.flag = true;
//				res.notify();
				newCondition.signal();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				res.lock.unlock();
			}
		}

		// }
	}
}

class OutThrad extends Thread {
	private Res res;
	private Condition newCondition;
	public OutThrad(Res res,Condition newCondition) {
		this.res = res;
		this.newCondition=newCondition;
	}

	@Override
	public void run() {
		while (true) {
//			synchronized (res) {
			try {
				res.lock.lock();
				if (!res.flag) {
					try {
//						res.wait();
						newCondition.await();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				System.out.println(res.userName + "," + res.sex);
				res.flag = false;
//				res.notify();
				newCondition.signal();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				res.lock.unlock();
			}
//			}
		}

	}
}

public class ThreadDemoLock {

	public static void main(String[] args) {
		Res res = new Res();
		Condition newCondition = res.lock.newCondition();
		InputThread inputThread = new InputThread(res,newCondition);
		OutThrad outThrad = new OutThrad(res,newCondition);
		inputThread.start();
		outThrad.start();
	}

}
