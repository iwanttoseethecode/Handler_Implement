package com.luoling.example;

import java.util.UUID;

/**
 * ע�⣺��demo��������̺߳����̵߳ĳƺ�����Եģ�����Looper�������Ƴ�֮Ϊ���̡߳�
 * 
 * */

public class HandlerTest {
	
	static Handler handler;
	
	public static void main(String[] args) {
		//��ѯ����ʼ��
		Looper.prepare();
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getName() +", received:"+msg.obj.toString() );
			}
		};
			
		startThread();
		//��ʼ��ѯ
		Looper.loop();
	}
	
	//�������߳�
	private static void startThread(){
		for (int i = 0; i < 3; i++) {
			new Thread(){
				public void run() {
					while(true){
						Message msg = new Message();
						msg.what = 1;
						synchronized (UUID.class) {
							msg.obj = Thread.currentThread().getName()+", send message:"+UUID.randomUUID().toString();
						}
						System.out.println(msg.obj.toString());
						handler.sendMessage(msg);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
			}.start();
		}
	}
}