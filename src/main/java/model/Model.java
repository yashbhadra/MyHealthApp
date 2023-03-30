package model;

import java.sql.SQLException;

import dao.HealthRecordDao;
import dao.HealthRecordDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private HealthRecordDao healthRecordDao;
	private User currentUser; 
	
	public Model() {
		userDao = new UserDaoImpl();
		healthRecordDao  = new HealthRecordDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
		healthRecordDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public HealthRecordDao getHealthRecordDao() {
		return healthRecordDao;
	}
}
