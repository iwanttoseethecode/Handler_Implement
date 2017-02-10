package com.luoling.example;

public final class Looper {
	//每一个 创建Looper对象的线程中都会保存一个looper对象,looper对象保存在ThreadLocal中。
	static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
	
	//一个Looper对象对应一个消息队列
	MessageQueue mQueue;
	
	private Looper(){
		mQueue = new MessageQueue();
	}
	
	/*
	 * Looper对象的初始化
	 * */
	public static void prepare(){
		if(sThreadLocal.get()!=null){
			throw new RuntimeException("Only one Looper may be create per thread");
		}
		sThreadLocal.set(new Looper());
	}
	
	
	/*
	 * 获取当前线程的looper对象
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
