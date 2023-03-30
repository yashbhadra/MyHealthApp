package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.BloodPressure;
import model.HealthRecord;
import model.User;

public class HealthRecordDaoImpl implements HealthRecordDao {
	private final String TABLE_NAME = "health_records";

	public HealthRecordDaoImpl() {
	}
	// Creating the initial connection using JDBC driver
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getInstance().getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "record_date date NOT NULL,"+ "weight INTEGER NOT NULL,"+ "temperature INTEGER NOT NULL,"+ "low INTEGER NOT NULL,"+ "high INTEGER NOT NULL,"
					+ "notes VARCHAR(50) NOT NULL," + "username varchar(10) NOT NULL,"+ "FOREIGN KEY (username) references users(username))";
			stmt.executeUpdate(sql);
			

		} 
	}
	// Fetch health records 
	@Override
	public ArrayList<HealthRecord> getHealthRecords(String username) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getInstance().getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			
			var records = new ArrayList<HealthRecord>();
			
				
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					BloodPressure bloodPressure = new BloodPressure(Integer.parseInt(rs.getString("low")),Integer.parseInt(rs.getString("high")));
					HealthRecord healthRecord = new HealthRecord();
					healthRecord.setId(Integer.parseInt(rs.getString("id")));
					healthRecord.setDate(rs.getDate("record_date"));
					healthRecord.setWeight(Integer.parseInt(rs.getString("weight")));
					healthRecord.setTemperature(Integer.parseInt(rs.getString("temperature")));
					healthRecord.setBloodPressure(bloodPressure);
					healthRecord.setNotes(rs.getString("notes"));
					records.add(healthRecord);
				}
				return records;
			} 
		}
	}
	// Fetch health records by ID

	@Override
	public HealthRecord getHealthRecordById(int recordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HealthRecord createHealthRecord(String username,Date date, int weight,int temperature, BloodPressure bloodPressure,
			String notes) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?,?, ?, ?, ?, ? , ? , ?)";
		try (Connection connection = Database.getInstance().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, null);
			stmt.setDate(2, date);
			stmt.setLong(3,weight);
			stmt.setLong(4, temperature);
			stmt.setLong(5, bloodPressure.getLow());
			stmt.setLong(6, bloodPressure.getHigh());
			stmt.setString(7, notes);
			stmt.setString(8, username);
			
			stmt.executeUpdate();
			return new HealthRecord(0,date,weight,temperature, bloodPressure,notes);
		} 
	}
	// delete health records


	@Override
	public void deleteHealthRecord(int recordId) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " where id=?";
		try (Connection connection = Database.getInstance().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, recordId);
			
			
			
			stmt.executeUpdate();
			
			
		} 
	}
	// update health records 

	@Override
	public HealthRecord updateHealthRecord(String username, Date date, int weight, int temperature,
			BloodPressure bloodPressure, String notes, int id) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " set record_date=?, weight=?, temperature=?, low=?, high=? , notes=?  WHERE id=?";
		try (Connection connection = Database.getInstance().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setDate(1, date);
			stmt.setLong(2,weight);
			stmt.setLong(3, temperature);
			stmt.setLong(4, bloodPressure.getLow());
			stmt.setLong(5, bloodPressure.getHigh());
			stmt.setString(6, notes);
			stmt.setInt(7, id);
			
			stmt.executeUpdate();
			return new HealthRecord(id,date,weight,temperature, bloodPressure,notes);
		} 
	}

	

	
	
	
}
