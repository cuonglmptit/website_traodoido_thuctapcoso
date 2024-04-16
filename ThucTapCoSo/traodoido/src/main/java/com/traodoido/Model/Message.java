package com.traodoido.Model;

import java.sql.Timestamp;

public class Message implements Comparable<Message>{
	private int messid;
	private String from;
	Timestamp time;
	private int trans;
	private String content;
	
	public Message(int messid, String from, Timestamp time, int trans, String content) {
		this.messid = messid;
		this.from = from;
		this.time = time;
		this.trans = trans;
		this.content = content;
	}
	
	public Message() {
		
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getMessid() {
		return messid;
	}
	public void setMessid(int messid) {
		this.messid = messid;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getTrans() {
		return trans;
	}
	public void setTrans(int trans) {
		this.trans = trans;
	}
	@Override
	public String toString() {
		return "Message [messid=" + messid + ", from=" + from + ", time=" + time + ", trans=" + trans + ", content="
				+ content + "]";
	}
	@Override
	public int compareTo(Message o) {
		return this.time.compareTo(o.time);
	}
	
}
