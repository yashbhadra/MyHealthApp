package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	// URL pattern for database
	private static final String DB_URL = "jdbc:sqlite:application.db";
	private static Database database;
	private Connection connection=null;
	
	private Database() throws SQLException {
		connection = DriverManager.getConnection(DB_URL);
	
		
	}
	public static Database  getInstance() throws SQLException {
		if(database == null) {
			database = new Database();
		}else if(database.connection.isClosed()) {
			database = new Database();
		}
		return database;
	}
	
	public Connection getConnection() throws SQLException {
		// DriverManager is the basic service for managing a set of JDBC drivers
		// Can also pass username and password
		return connection;
	}
}
