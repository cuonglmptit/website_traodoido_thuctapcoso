package com.traodoido.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.traodoido.Model.Transaction;


public class TransactionDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/ttcs";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";
	
	private static final String SELECT_ALL_TRANS_BY_FROM_USER_AND_STATUS = "SELECT * FROM transaction WHERE fromuser = ? AND status = ? ORDER BY created DESC;";
	private static final String SELECT_ALL_TRANS_BY_TO_USER_AND_STATUS = "SELECT * FROM transaction WHERE touser = ? AND status = ? ORDER BY created DESC;";
	private static final String SELECT_TRANS_BY_ID = "SELECT * FROM transaction WHERE transid = ? ORDER BY created DESC;";
	private static final String INSERT_TRANS_SQL = "INSERT INTO transaction (`fromuser`, `touser`, `item1id`, `item2id`, `amount`, "
			+ "`tradetype`, `status`, `created`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_TRANS_STATUS = "UPDATE transaction SET `status` = ? WHERE (`transid` = ?);";
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
	
	public Transaction selectTransByTransID(int transactionid) {
		Transaction trans = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_TRANS_BY_ID)){
			ps.setInt(1, transactionid);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				String fromuser = rs.getString("fromuser");
				String touser = rs.getString("touser");
				int itemid1 = rs.getInt("item1id");
				int itemid2 = rs.getInt("item2id");
				int amount = rs.getInt("amount");
				int tradetype = rs.getInt("tradetype");
				int stt = rs.getInt("status");
				Timestamp created = rs.getTimestamp("created");
				trans = new Transaction(transid, fromuser, touser, itemid1, itemid2, amount, tradetype, stt, created);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trans;
	}
	
	public List<Transaction> selectTransByFromUserAndStatus(String username, int status) {
		List<Transaction> transs = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_TRANS_BY_FROM_USER_AND_STATUS)){
			ps.setString(1, username);
			ps.setInt(2, status);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				String fromuser = rs.getString("fromuser");
				String touser = rs.getString("touser");
				int itemid1 = rs.getInt("item1id");
				int itemid2 = rs.getInt("item2id");
				int amount = rs.getInt("amount");
				int tradetype = rs.getInt("tradetype");
				int stt = rs.getInt("status");
				Timestamp created = rs.getTimestamp("created");
				Transaction trans = new Transaction(transid, fromuser, touser, itemid1, itemid2, amount, tradetype, stt, created);
				transs.add(trans);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transs;
	}
	public List<Transaction> selectTransByToUserAndStatus(String username, int status) {
		List<Transaction> transs = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_TRANS_BY_TO_USER_AND_STATUS)){
			ps.setString(1, username);
			ps.setInt(2, status);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				String fromuser = rs.getString("fromuser");
				String touser = rs.getString("touser");
				int itemid1 = rs.getInt("item1id");
				int itemid2 = rs.getInt("item2id");
				int amount = rs.getInt("amount");
				int tradetype = rs.getInt("tradetype");
				int stt = rs.getInt("status");
				Timestamp created = rs.getTimestamp("created");
				Transaction trans = new Transaction(transid, fromuser, touser, itemid1, itemid2, amount, tradetype, stt, created);
				transs.add(trans);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transs;
	}

	public int addTrans(Transaction trans) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_TRANS_SQL)) {
			ps.setString(1, trans.getFromuser());
			ps.setString(2, trans.getTouser());
			ps.setInt(3, trans.getItem1id());
			ps.setInt(4, trans.getItem2id());
			ps.setInt(5, trans.getAmount());
			ps.setInt(6, trans.getTradetype());
			ps.setInt(7, trans.getStatus());
			trans.setCreated(new Timestamp(System.currentTimeMillis()));
			System.out.println(trans.getCreated());
			ps.setTimestamp(8, trans.getCreated());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int updateTransStatus(Transaction trans) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_TRANS_STATUS)) {
			ps.setInt(1, trans.getStatus());
			ps.setInt(2, trans.getTransid());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
