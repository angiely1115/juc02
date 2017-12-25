
package com.lv.juc;

public class Test005 {

	/**
	 * �û��߳� �����̴߳������̡߳� ���ػ��߳� �ػ��߳� �����߳�һ�����١�
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 30; i++) {
					try {
						Thread.sleep(300);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("t1子线程,i:" + i);
				}
			}
		});
		t1.setDaemon(true);//设置为守护线程
		t1.start();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("主线程,i:" + i);
		}
		System.out.println("主线程结束.....");
	}

}
