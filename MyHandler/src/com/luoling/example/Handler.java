package com.luoling.example;




public abstract class Handler {
	
	
	private MessageQueue mQueue;
	private Looper mLooper;
	
	
	//handler的初始化是在主线程中完成的
	public Handler() {
		super();
		//获取主线程的Looper对象
		mLooper = Looper.myLooper();
		this.mQueue = mLooper.mQueue;
				
	}



	public void sendMessage(Message msg){
		msg.target = this;
		mQueue.enqueueMessage(msg);
	}
	
	public void dispatchMessage(Message msg){
		handleMessage(msg);
	}
	
	public abstract void handleMessage(Message msg);
	
}
