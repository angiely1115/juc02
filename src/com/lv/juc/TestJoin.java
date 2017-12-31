
package com.lv.juc;

public class TestJoin {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					System.out.println("T1,i:" + i);
				}
			}
		});
		t1.start();
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					t1.join();//指在当然线程让出给t1线程等待t1线程执行完毕
				} catch (Exception e) {
					// TODO: handle exception
				}
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(30);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("T2,i:" + i);
				}
			}
		});

		t2.start();
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					t2.join();
				} catch (Exception e) {
					// TODO: handle exception
				}
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(30);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("T3,i:" + i);
				}
			}
		});

		t3.start();
	}

}
