package com.luoling.example;

public final class Looper {
	//ÿһ�� ����Looper������߳��ж��ᱣ��һ��looper����,looper���󱣴���ThreadLocal�С�
	static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
	
	//һ��Looper�����Ӧһ����Ϣ����
	MessageQueue mQueue;
	
	private Looper(){
		mQueue = new MessageQueue();
	}
	
	/*
	 * Looper����ĳ�ʼ��
	 * */
	public static void prepare(){
		if(sThreadLocal.get()!=null){
			throw new RuntimeException("Only one Looper may be create per thread");
		}
		sThreadLocal.set(new Looper());
	}
	
	
	/*
	 * ��ȡ��ǰ�̵߳�looper����
	 * */
	public static Looper myLooper(){
		return sThreadLocal.get();
	}
	
	
	public static void loop(){
		Looper me = myLooper();
		if(me == null){
			throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this Thread.");
		}
		MessageQueue queue = me.mQueue;
		while(true){
			Message msg = queue.next();
			if(msg == null){
				continue;
			}
			
			msg.target.dispatchMessage(msg);
		}
	}
}
