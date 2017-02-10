package com.luoling.example;




public abstract class Handler {
	
	
	private MessageQueue mQueue;
	private Looper mLooper;
	
	
	//handler�ĳ�ʼ���������߳�����ɵ�
	public Handler() {
		super();
		//��ȡ���̵߳�Looper����
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
