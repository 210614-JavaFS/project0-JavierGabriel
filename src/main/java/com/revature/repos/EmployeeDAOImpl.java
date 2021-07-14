package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Client;
import com.revature.models.Employee;
import com.revature.models.UserType;
import com.revature.utils.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public Employee login(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE username=? AND user_pass=? AND (user_type = 'Employee' OR user_type = 'Administrator');";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, username);
			statement.setString(++index, password);
			ResultSet result = statement.executeQuery();
			Employee employee = new Employee();
			while(result.next() && result.getString("username") != "null") {
				employee.setName(result.getString("username"));
				employee.setPassword(result.getString("user_pass"));
				try {
					employee.setUserType(UserType.valueOf(result.getString("user_type")));
				}catch(IllegalArgumentException e) {
					e.printStackTrace();
				}	
			}
			
			if(employee.getName() != null) {
				return employee;
			}
			else {
				employee = null;
				return employee;
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
