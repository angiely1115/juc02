
package com.lv.juc;

class StopThread extends Thread {
	private volatile boolean flag = true;

	@Override
	public  void run() {
		System.out.println("子线程....");
		while (flag) {
			//try {
			//	wait();
			//} catch (InterruptedException e) {
			//	// e.printStackTrace();
			//	stopThread();
			//}
		}
		System.out.println("子线程结束....");
	}

	public void stopThread() {
		flag = false;
	}

}

public class StopThreadDemo {

	public static void main(String[] args) {
		StopThread stopThread = new StopThread();
		stopThread.start();
		for (int i = 1; i < 10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println("主线程i:" + i);
				if (i == 3) {
					// stopThread.stopThread();
					//stopThread.interrupt();
					stopThread.stop();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
