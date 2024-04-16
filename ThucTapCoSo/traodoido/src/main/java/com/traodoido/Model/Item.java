package com.traodoido.Model;

import java.sql.Date;

public class Item implements Comparable<Item>{
	private int iid, userID, acceptType, tradePrice, sellPrice, tradeRange, category;
	private int avaiable;
	private String title, descr, img1, img2, img3;
	private Date createddate;

	public Item() {

	}

	public Item(int iid, int userID, int acceptType, int tradePrice, int sellPrice, int pTradeRange, int category,
			int avaiable, String title, String descr, String img1, String img2, String img3, Date createddate) {
		this.iid = iid;
		this.userID = userID;
		this.acceptType = acceptType;
		this.tradePrice = tradePrice;
		this.sellPrice = sellPrice;
		this.tradeRange = pTradeRange;
		this.category = category;
		this.avaiable = avaiable;
		this.title = title;
		this.descr = descr;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.createddate = createddate;
	}

	public int setImgByNumber(int imgNumber, String src) {
		int result = 0;
		if (imgNumber == 1) {
			this.img1 = src;
		} else if (imgNumber == 2) {
			this.img2 = src;
		} else if (imgNumber == 3) {
			this.img3 = src;
		}
		return result;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(int acceptType) {
		this.acceptType = acceptType;
	}

	public int getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getTradeRange() {
		return tradeRange;
	}

	public void setTradeRange(int pTradeRange) {
		this.tradeRange = pTradeRange;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getAvaiable() {
		return avaiable;
	}

	public void setAvaiable(int avaiable) {
		this.avaiable = avaiable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	@Override
	public String toString() {
		return "Item [iid=" + iid + ", userID=" + userID + ", acceptType=" + acceptType + ", tradePrice=" + tradePrice
				+ ", sellPrice=" + sellPrice + ", tradeRange=" + tradeRange + ", category=" + category + ", avaiable="
				+ avaiable + ", title=" + title + ", descr=" + descr + ", img1=" + img1 + ", img2=" + img2 + ", img3="
				+ img3 + ", createddate=" + createddate + "]";
	}

	@Override
	public int compareTo(Item o) {
		return this.createddate.compareTo(o.createddate);
	}

	
	
}
