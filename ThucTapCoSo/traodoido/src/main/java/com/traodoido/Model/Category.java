package com.traodoido.Model;

public class Category {
	private int catid;
	private String catname, catdescribe;
	
	public Category() {

	}
	public Category(int catid, String catname, String catdescribe) {
		this.catid = catid;
		this.catname = catname;
		this.catdescribe = catdescribe;
	}
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	public String getCatname() {
		return catname;
	}
	public void setCatname(String catname) {
		this.catname = catname;
	}
	public String getCatdescribe() {
		return catdescribe;
	}
	public void setCatdescribe(String catdescribe) {
		this.catdescribe = catdescribe;
	}
	@Override
	public String toString() {
		return "Category [catid=" + catid + ", catname=" + catname + ", catdescribe=" + catdescribe + "]";
	}
	
}
