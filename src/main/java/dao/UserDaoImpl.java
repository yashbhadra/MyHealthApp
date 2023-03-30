package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}
	// Setting up initial connection for JDBC driver  

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getInstance().getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}
	// Fetch user by username password 

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getInstance().getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					return user;
				}
				return null;
			} 
		}
	}
	// Insert record 

	@Override
	public User createUser(String username, String password,String firstName,String lastName) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.getInstance().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);

			stmt.executeUpdate();
			return new User(username, password,firstName,lastName);
		} 
	}
	// update user record 

	@Override
	public User updateUser(String username, String password,String firstName, String lastName) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET firstname=?, lastname=? where username=?";
		try (Connection connection = Database.getInstance().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, username);
			
			stmt.executeUpdate();
			return getUser(username,password);
		} 
	}
	// Fetch user by username 
	@Override
	public User getUserByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getInstance().getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					return user;
				}
				return null;
			} 
		}
	}
	
}
