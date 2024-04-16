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

import com.traodoido.Model.Message;

public class MessageDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/ttcs";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";
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
	private static final String SELECT_ALL_MESSAGE_BY_TRANSID = "SELECT * FROM message WHERE trans = ? ORDER BY time ASC;";
	private static final String INSERT_MESSAGE_SQL = "INSERT INTO message (`from`, `time`, `trans`, `content`) VALUES (?, ?, ?, ?);";
	
	public int addMessage(Message message) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MESSAGE_SQL)) {
			ps.setString(1, message.getFrom());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, message.getTrans());
			ps.setString(4, message.getContent());
//			System.out.println(ps);
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Message> selectMessageByTransID(int transid){
		List<Message> messages = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_MESSAGE_BY_TRANSID)) {
			ps.setInt(1, transid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int messid = rs.getInt("messid");
				String from = rs.getString("from");
				Timestamp time = rs.getTimestamp("time");
				int trans = rs.getInt("trans");
				String content = rs.getString("content");
//				System.out.println(item);
				messages.add(new Message(messid, from, time, trans, content));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
}
