package com.traodoido.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.traodoido.Model.Item;

public class ItemDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/ttcs";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";

	private static final String SELECT_ALL_ITEMS = "SELECT * FROM item ORDER BY createddate DESC;";
	private static final String SELECT_ALL_ITEMS_BY_CATEGORY = "SELECT * FROM item WHERE category = ? ORDER BY createddate DESC;";
	private static final String SELECT_ALL_ITEMS_BY_ACCEPT_TYPE = "SELECT * FROM item WHERE accepttype = ? ORDER BY createddate DESC;";
	private static final String SELECT_ITEMS_BY_UID = "SELECT * FROM item WHERE userid = ? ORDER BY createddate DESC;";
	private static final String SELECT_ITEM_BY_KEYWORD = "SELECT * FROM item WHERE title LIKE ? ORDER BY createddate DESC;";
	private static final String SELECT_ITEM_BY_ID = "SELECT * FROM item WHERE iid = ? ORDER BY createddate DESC;";
	private static final String INSERT_ITEM_SQL = "INSERT INTO item (`title`, `userid`, `accepttype`, `tradeprice`, `sellprice`, `traderange`, `avaiable`, `descr`, `category`, `img1`, `img2`, `img3`, `createddate`)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_ITEM_SQL = "UPDATE item SET `title` = ?, `accepttype` = ?, `tradeprice` = ?, `sellprice` = ?, "
			+ "`traderange` = ?, `avaiable` = ?, `descr` = ?, `category` = ?, "
			+ "`img1` = ?, `img2` = ?, `img3` = ?, `createddate` = ? WHERE (`iid` = ?);";
	private static final String SELECT_ITEM_BY_ID_AND_UID = "SELECT * FROM item WHERE iid = ? and userid = ? ORDER BY createddate DESC;";
	private static final String DELETE_ITEM_BY_ID = "DELETE FROM item WHERE (`iid` = ?);";
	
	public ItemDAO() {

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
	
	public int deleteItem(int iid) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_ITEM_BY_ID)) {
			ps.setInt(1, iid);
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int addItem(Item item) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ITEM_SQL)) {
			ps.setString(1, item.getTitle());
			ps.setInt(2, item.getUserID());
			ps.setInt(3, item.getAcceptType());
			ps.setInt(4, item.getTradePrice());
			ps.setInt(5, item.getSellPrice());
			ps.setInt(6, item.getTradeRange());
			ps.setInt(7, item.getAvaiable());
			ps.setString(8, item.getDescr());
			ps.setInt(9, item.getCategory());
			ps.setString(10, item.getImg1());
			ps.setString(11, item.getImg2());
			ps.setString(12, item.getImg3());
			ps.setDate(13, item.getCreateddate());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateItem(Item item) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_ITEM_SQL)) {
			ps.setString(1, item.getTitle());
			ps.setInt(2, item.getAcceptType());
			ps.setInt(3, item.getTradePrice());
			ps.setInt(4, item.getSellPrice());
			ps.setInt(5, item.getTradeRange());
			ps.setInt(6, item.getAvaiable());
			ps.setString(7, item.getDescr());
			ps.setInt(8, item.getCategory());
			ps.setString(9, item.getImg1());
			ps.setString(10, item.getImg2());
			ps.setString(11, item.getImg3());
			ps.setDate(12, item.getCreateddate());
			ps.setInt(13, item.getIid());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Item> selectAllItemsByAcceptType(int accType) {
		List<Item> items = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ITEMS_BY_ACCEPT_TYPE)) {
			ps.setInt(1, accType);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int iid = rs.getInt("iid");
				String title = rs.getString("title");
				int userID = rs.getInt("userid");
				int acceptType = rs.getInt("accepttype");
				int tradePrice = rs.getInt("tradeprice");
				int sellPrice = rs.getInt("sellprice");
				int traderange = rs.getInt("traderange");
				int avaiable = rs.getInt("avaiable");
				String descr = rs.getString("descr");
				int category = rs.getInt("category");
				String img1 = rs.getString("img1");
				String img2 = rs.getString("img2");
				String img3 = rs.getString("img3");
				Date createddate = rs.getDate("createddate");
				Item item = new Item(iid, userID, acceptType, tradePrice, sellPrice, traderange, category, avaiable,
						title, descr, img1, img2, img3, createddate);
//				System.out.println(item);
				items.add(item);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	public List<Item> selectAllItemsByCategory(int cateid) {
		List<Item> items = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ITEMS_BY_CATEGORY)) {
			ps.setInt(1, cateid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int iid = rs.getInt("iid");
				String title = rs.getString("title");
				int userID = rs.getInt("userid");
				int acceptType = rs.getInt("accepttype");
				int tradePrice = rs.getInt("tradeprice");
				int sellPrice = rs.getInt("sellprice");
				int traderange = rs.getInt("traderange");
				int avaiable = rs.getInt("avaiable");
				String descr = rs.getString("descr");
				int category = rs.getInt("category");
				String img1 = rs.getString("img1");
				String img2 = rs.getString("img2");
				String img3 = rs.getString("img3");
				Date createddate = rs.getDate("createddate");
				Item item = new Item(iid, userID, acceptType, tradePrice, sellPrice, traderange, category, avaiable,
						title, descr, img1, img2, img3, createddate);
//				System.out.println(item);
				items.add(item);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	public List<Item> selectAllItems() {
		List<Item> items = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_ITEMS)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int iid = rs.getInt("iid");
				String title = rs.getString("title");
				int userID = rs.getInt("userid");
				int acceptType = rs.getInt("accepttype");
				int tradePrice = rs.getInt("tradeprice");
				int sellPrice = rs.getInt("sellprice");
				int traderange = rs.getInt("traderange");
				int avaiable = rs.getInt("avaiable");
				String descr = rs.getString("descr");
				int category = rs.getInt("category");
				String img1 = rs.getString("img1");
				String img2 = rs.getString("img2");
				String img3 = rs.getString("img3");
				Date createddate = rs.getDate("createddate");
				Item item = new Item(iid, userID, acceptType, tradePrice, sellPrice, traderange, category, avaiable,
						title, descr, img1, img2, img3, createddate);
//				System.out.println(item);
				items.add(item);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public List<Item> selectItemsByKeyword(String keyword) {
		List<Item> items = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ITEM_BY_KEYWORD)) {
			ps.setString(1, "%"+keyword+"%");
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int iid = rs.getInt("iid");
				String title = rs.getString("title");
				int userID = rs.getInt("userid");
				int acceptType = rs.getInt("accepttype");
				int tradePrice = rs.getInt("tradeprice");
				int sellPrice = rs.getInt("sellprice");
				int traderange = rs.getInt("traderange");
				int avaiable = rs.getInt("avaiable");
				String descr = rs.getString("descr");
				int category = rs.getInt("category");
				String img1 = rs.getString("img1");
				String img2 = rs.getString("img2");
				String img3 = rs.getString("img3");
				Date createddate = rs.getDate("createddate");
				Item item = new Item(iid, userID, acceptType, tradePrice, sellPrice, traderange, category, avaiable,
						title, descr, img1, img2, img3, createddate);
//				System.out.println(item);
				items.add(item);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public List<Item> selectItemsOfUserByUID(int userid) {
		List<Item> items = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ITEMS_BY_UID)) {
			ps.setInt(1, userid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int iid = rs.getInt("iid");
				String title = rs.getString("title");
				int userID = rs.getInt("userid");
				int acceptType = rs.getInt("accepttype");
				int tradePrice = rs.getInt("tradeprice");
				int sellPrice = rs.getInt("sellprice");
				int traderange = rs.getInt("traderange");
				int avaiable = rs.getInt("avaiable");
				String descr = rs.getString("descr");
				int category = rs.getInt("category");
				String img1 = rs.getString("img1");
				String img2 = rs.getString("img2");
				String img3 = rs.getString("img3");
				Date createddate = rs.getDate("createddate");
				Item item = new Item(iid, userID, acceptType, tradePrice, sellPrice, traderange, category, avaiable,
						title, descr, img1, img2, img3, createddate);
//				System.out.println(item);
				items.add(item);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public Item selectItemById(int iid) {
		Item item = new Item();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ITEM_BY_ID)){
			ps.setInt(1, Integer.valueOf(iid));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				item.setIid(rs.getInt("iid"));
				item.setTitle(rs.getString("title"));
				item.setUserID(rs.getInt("userid"));
				item.setAcceptType(rs.getInt("accepttype"));
				item.setTradePrice(rs.getInt("tradeprice"));
				item.setSellPrice(rs.getInt("sellprice"));
				item.setTradeRange(rs.getInt("traderange"));
				item.setAvaiable(rs.getInt("avaiable"));
				item.setDescr(rs.getString("descr"));
				item.setCategory(rs.getInt("category"));
				item.setImg1(rs.getString("img1"));
				item.setImg2(rs.getString("img2"));
				item.setImg3(rs.getString("img3"));
				item.setCreateddate(rs.getDate("createddate"));
			}
			ps.close();
			conn.close();
			System.out.println(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	
	public Item selectItemByIdAndUser(int iid, int uid) {
		Item item = new Item();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ITEM_BY_ID_AND_UID)){
			ps.setInt(1, Integer.valueOf(iid));
			ps.setInt(2, Integer.valueOf(uid));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				item.setIid(rs.getInt("iid"));
				item.setTitle(rs.getString("title"));
				item.setUserID(rs.getInt("userid"));
				item.setAcceptType(rs.getInt("accepttype"));
				item.setTradePrice(rs.getInt("tradeprice"));
				item.setSellPrice(rs.getInt("sellprice"));
				item.setTradeRange(rs.getInt("traderange"));
				item.setAvaiable(rs.getInt("avaiable"));
				item.setDescr(rs.getString("descr"));
				item.setCategory(rs.getInt("category"));
				item.setImg1(rs.getString("img1"));
				item.setImg2(rs.getString("img2"));
				item.setImg3(rs.getString("img3"));
				item.setCreateddate(rs.getDate("createddate"));
			}
			ps.close();
			conn.close();
//			System.out.println(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
}
