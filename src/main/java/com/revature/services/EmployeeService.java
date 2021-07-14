package com.revature.services;

import com.revature.models.Employee;
import com.revature.repos.EmployeeDAO;
import com.revature.repos.EmployeeDAOImpl;

public class EmployeeService {
	EmployeeDAO employeeDao = new EmployeeDAOImpl();
	public Employee login(String username, String password) {
		return employeeDao.login(username, password);
	}
	public boolean createEmployee(String username, String password) {
		return employeeDao.createEmployee(username, password);
	}
}
