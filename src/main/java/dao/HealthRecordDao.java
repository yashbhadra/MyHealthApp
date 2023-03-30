package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import model.BloodPressure;
import model.HealthRecord;
import model.User;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface HealthRecordDao {
	void setup() throws SQLException;
	ArrayList<HealthRecord> getHealthRecords(String username) throws SQLException;
	HealthRecord getHealthRecordById(int recordId) throws SQLException;
	HealthRecord createHealthRecord(String username, Date date,int weight, int temperature, BloodPressure bloodPressure, String notes) throws SQLException;
	HealthRecord updateHealthRecord(String username, Date date, int weight,int temperature, BloodPressure bloodPressure, String notes, int id) throws SQLException;
	void deleteHealthRecord(int recordId) throws SQLException;
}
