package com.traodoido.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.traodoido.Model.Category;


public class CategoryDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/ttcs";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";

	private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM category;";
	private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE catid = ?;";

	public CategoryDAO() {

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
	
	public List<Category> selectAllCategories() {
		List<Category> categories = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_CATEGORIES)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int catid = rs.getInt("catid");
				String catname = rs.getString("catname");
				String catdescribe = rs.getString("catdescribe");
				Category category = new Category(catid, catname, catdescribe);
//				System.out.println(categorie);
				categories.add(category);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}
	public Category selectCategoryById(int catid) {
		Category category = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_CATEGORY_BY_ID)) {
			ps.setInt(1, catid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("catid");
				String catname = rs.getString("catname");
				String catdescribe = rs.getString("catdescribe");
				category = new Category(cid, catname, catdescribe);
//				System.out.println(categorey);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}
}
