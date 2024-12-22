package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
	
	
	public DaoImplJDBC() {
		connect();
		
	}
	
	private Connection connection;
	//QUERY TO CONNET 
	public static final String GET_INVENTORY = "select * from inventory";
	

	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String pass = "";
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		String query = "select * from employee where employeeId= ? and password = ? ";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
    		ps.setInt(1,employeeId);
    	  	ps.setString(2,password);
    	  	//System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
            	}
            }
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
    	return employee;
	}

	@Override
	public ArrayList<Product> getInventory() {
	    ArrayList<Product> product = new ArrayList<>();

	    // Ensure connection is established
	    if (connection == null) {
	        return product;
	    }

	    try (Statement ps = connection.createStatement()) {
	        try (ResultSet rs = ps.executeQuery(GET_INVENTORY)) {
	            while (rs.next()) {
	                product.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getBoolean(4), rs.getInt(5)));
	            }
	        }
	        Product totalProduct = new Product(); 
	        totalProduct.setTotalProducts(product.size()); 
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return product;
	}

	
	

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		String insertQuery = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, available, stock, created_at) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
			int totalUpdated = 0;
			for (Product product : inventory) {
				ps.setInt(1, product.getId());
				ps.setString(2, product.getName());
				ps.setDouble(3, product.getWholesalerPrice().getValue());
				ps.setBoolean(4, product.isAvailable());
				ps.setInt(5, product.getStock());
				ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

				int rowsUpdated = ps.executeUpdate();
				totalUpdated += rowsUpdated;
			}

			return totalUpdated == inventory.size();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		String updateQuery = "UPDATE inventory SET stock = ? WHERE id = ?";
	    try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
	        ps.setInt(1, product.getStock()); 
	        ps.setInt(2, product.getId()); 
	        int rowsAffected = ps.executeUpdate(); 
	       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		String deleteQuery = "DELETE FROM inventory WHERE name = ?";
	    try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
	        ps.setInt(1, product.getId()); 
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	       
	    }
	}
	

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		 String addQuery = "INSERT INTO inventory (id, name, wholeSalerPrice, available, stock) VALUES (?, ?, ?, ?, ?)";
		    try (PreparedStatement ps = connection.prepareStatement(addQuery)) {
		        ps.setInt(1, product.getId());
		        ps.setString(2, product.getName());
		        ps.setDouble(3, product.getWholesalerPrice().getValue());
		        ps.setBoolean(4, product.isAvailable());
		        ps.setInt(5, product.getStock());
		        ps.executeUpdate(); // Execute the query
		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		        
		    }
		
	}
	
	
	
	

}