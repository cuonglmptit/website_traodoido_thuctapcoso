package com.traodoido.Model;

import java.sql.Date;

public class User {
	private int uid;
	private String uname, uaddress, username, password;
	private Date ubirth;
	private boolean isadmin;
	public User(){
		
	}
	public User(int uid, String uname, String uaddress, String username, String password, Date ubirth,
			boolean isadmin) {
		this.uid = uid;
		this.uname = uname;
		this.uaddress = uaddress;
		this.username = username;
		this.password = password;
		this.ubirth = ubirth;
		this.isadmin = isadmin;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}
	public int getUid() {
		return uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUaddress() {
		return uaddress;
	}
	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getUbirth() {
		return ubirth;
	}
	public void setUbirth(Date ubirth) {
		this.ubirth = ubirth;
	}
	public boolean isIsadmin() {
		return isadmin;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", uaddress=" + uaddress + ", username=" + username
				+ ", password=" + password + ", ubirth=" + ubirth + ", isadmin=" + isadmin + "]";
	}
	
	
}
