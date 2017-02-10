package com.luoling.example;

public class Message {
	public int what;
	public Object obj;
	Handler target;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return obj.toString();
	}
}
