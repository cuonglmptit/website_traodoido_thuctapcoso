package com.traodoido.Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Transaction implements Comparable<Transaction>{
	private int transid;
	private String fromuser, touser;
	private int item1id, item2id, amount, tradetype, status;
	private Timestamp created;
	public Transaction(int transid, String fromuser, String touser, int item1id, int item2id, int amount,
			int tradetype, int status, Timestamp created) {
		this.transid = transid;
		this.fromuser = fromuser;
		this.touser = touser;
		this.item1id = item1id;
		this.item2id = item2id;
		this.amount = amount;
		this.tradetype = tradetype;
		this.status = status;
		this.created = created;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Transaction() {

	}
	public int getTransid() {
		return transid;
	}
	public void setTransid(int transid) {
		this.transid = transid;
	}
	public String getFromuser() {
		return fromuser;
	}
	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public int getItem1id() {
		return item1id;
	}
	public void setItem1id(int item1id) {
		this.item1id = item1id;
	}
	public int getItem2id() {
		return item2id;
	}
	public void setItem2id(int item2id) {
		this.item2id = item2id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getTradetype() {
		return tradetype;
	}
	public void setTradetype(int tradetype) {
		this.tradetype = tradetype;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Transaction [transid=" + transid + ", fromuser=" + fromuser + ", touser=" + touser + ", item1id="
				+ item1id + ", item2id=" + item2id + ", amount=" + amount + ", tradetype=" + tradetype + ", status="
				+ status + ", created=" + created + "]";
	}
	@Override
	public int compareTo(Transaction o) {
		return this.created.compareTo(o.created);
	}
}
