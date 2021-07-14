package com.revature.repos;

import com.revature.models.Employee;

public interface EmployeeDAO {
	public Employee login(String username, String password);
	public boolean createEmployee(String username, String password);
}
