package com.traodoido.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.traodoido.Model.User;

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/ttcs";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";

	private static final String SELECT_ALL_USERS = "SELECT * FROM user;";
	private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = ?;";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE uid = ?;";
	private static final String CREATE_USER = "INSERT INTO `ttcs`.`user` (`uname`, `username`, `password`) VALUES (?, ?, ?);";
	private static final String VALID_USER = "SELECT * FROM user WHERE username=? and password=?;";
	private static final String UPDATE_USER_SQL = "UPDATE user SET `uname` = ?, `uaddress` = ?, `ubirth` = ? WHERE (`uid` = ?);";
	public UserDAO() {

	}
	
	protected Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPasword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public User selectUserByUsername(String username){
		User user = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_USERNAME)){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUid(rs.getInt("uid"));
				user.setUname(rs.getString("uname"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUaddress(rs.getString("uaddress"));
				user.setUbirth(rs.getDate("ubirth"));
				user.setIsadmin(rs.getInt("isadmin") == 0? false:true);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User selectUserByID(int uid){
		User user = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID)){
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUid(rs.getInt("uid"));
				user.setUname(rs.getString("uname"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUaddress(rs.getString("uaddress"));
				user.setUbirth(rs.getDate("ubirth"));
				user.setIsadmin(rs.getInt("isadmin") == 0? false:true);
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public int updateUser(User user) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL)){
			ps.setString(1, user.getUname());
			ps.setString(2, user.getUaddress());
			ps.setDate(3, user.getUbirth());
			ps.setInt(4, user.getUid());
			result = ps.executeUpdate();
			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createUser(String username, String password) {
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(CREATE_USER)){
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, password);
			int result = ps.executeUpdate();
			ps.close();
			conn.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int checkValidUser(String username, String password) {
		int valid = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(VALID_USER)){
			ps.setString(1, username.toLowerCase());
			ps.setString(2, password);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				valid = 1;
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valid;
	}

}
