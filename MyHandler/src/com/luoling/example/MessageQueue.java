package com.luoling.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
	
	//通过数组结构存储Message对象
	private Message[] items;
	
	//计数器
	private AtomicInteger count = new AtomicInteger(0);
	//互斥锁
	private Lock lock;
	//不为空的条件变量
	private Condition notEmpty;
	//没装满的条件变量
	private Condition notFull;
	
	/**
	 * 入队出队元素索引位置
	 * */
	int putIndex;
	int takeIndex;
	
	public MessageQueue(){
		this.items = new Message[50];
		lock = new ReentrantLock();
		notEmpty = lock.newCondition();
		notFull = lock.newCondition();
	}
	
	/**
	 * 加入队列
	 * */
	public void enqueueMessage(Message msg){
		lock.lock();
		//消息队列满了，子线程发送到消息阻塞
		while( count.get() == items.length){
			try {
				notFull.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		items[putIndex++] = msg;
		putIndex = (putIndex == items.length) ? 0 : putIndex;
		count.incrementAndGet();
		notEmpty.signal();
		lock.unlock();
	}
	
	
	/**
	 * 出队列
	 * */
	public Message next(){
		lock.lock();
		//消息队列为空，主线程轮询阻塞
		while(count.get() == 0){
			try {
				notEmpty.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Message msg = items[takeIndex];
		items[takeIndex] = null;
		takeIndex = (takeIndex++ == items.length) ? 0 : takeIndex;
		count.decrementAndGet();
		notFull.signal();
		lock.unlock();
		return msg;
	}
}
