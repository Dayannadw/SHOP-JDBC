package dao;

import model.Employee;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public interface Dao {
	
	public void connect();

	public void disconnect();

	public Employee getEmployee(int employeeId, String password);
	
	
	public ArrayList<Product> getInventory();
	
	public boolean writeInventory(ArrayList<Product> products); 
	
}
	

