package com.lv.juc;

class ResLocal {
    // 生成序列号共享变量
	public static Integer count = 0;
	public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
		protected Integer initialValue() {

			return 0;
		};

	};

	public Integer getNum() {
		int count = threadLocal.get() + 1;
		threadLocal.set(count);
		return count;
	}
}

public class ThreadLocaDemo extends Thread {
	private ResLocal res;

	public ThreadLocaDemo(ResLocal res) {
		this.res = res;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + "---" + "i---" + i + "--num:" + res.getNum());
		}

	}

	public static void main(String[] args) {
		ResLocal res = new ResLocal();
		ThreadLocaDemo threadLocaDemo1 = new ThreadLocaDemo(res);
		ThreadLocaDemo threadLocaDemo2 = new ThreadLocaDemo(res);
		ThreadLocaDemo threadLocaDemo3 = new ThreadLocaDemo(res);
		threadLocaDemo1.start();
		threadLocaDemo2.start();
		threadLocaDemo3.start();
	}

}
