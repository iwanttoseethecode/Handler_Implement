package com.luoling.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
	
	//ͨ������ṹ�洢Message����
	private Message[] items;
	
	//������
	private AtomicInteger count = new AtomicInteger(0);
	//������
	private Lock lock;
	//��Ϊ�յ���������
	private Condition notEmpty;
	//ûװ������������
	private Condition notFull;
	
	/**
	 * ��ӳ���Ԫ������λ��
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
	 * �������
	 * */
	public void enqueueMessage(Message msg){
		lock.lock();
		//��Ϣ�������ˣ����̷߳��͵���Ϣ����
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
	 * ������
	 * */
	public Message next(){
		lock.lock();
		//��Ϣ����Ϊ�գ����߳���ѯ����
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
