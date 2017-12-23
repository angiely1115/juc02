package com.atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 题目：判断打印的 "one" or "two" ？
 * 
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 * 3. 新增普通方法 getThree() , 打印? //three  one   two
 * 4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
 * 5. 修改 getOne() 为静态同步方法，打印?  //two   one
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
 * 8. 两个静态同步方法，两个 Number 对象?   //one  two
 * 
 * 线程八锁的关键：
 * ①非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
 * 一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用
 * 其中的一个synchronized方法了，其它的线程都只能等待，换句话说，某一个时刻 内，只能有唯一一个线程去访问这些synchronized方法
 *锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的 synchronized方法
 */
public class TestThread8Monitor {
	
	public static void main(String[] args) {
		Number number = new Number();
		Number number2 = new Number();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				number.getOne();
			} 
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				number.getTwo();
				//number2.getTwo();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				number.getThree();
			}
		}).start();
		
	}

}

class Number{
	private byte[] aByte = new byte[1];
	public  void getOne(){//static 表示锁对象是 Number.class
		try {
			synchronized(aByte){
				Thread.sleep(3000);
				System.out.println("one");
			}

		} catch (InterruptedException e) {
		}

	}
	
	public  void getTwo(){//this
		synchronized(aByte){
			System.out.println("two");
		}

	}
	
	public void getThree(){
		System.out.println("three");
	}
	
}