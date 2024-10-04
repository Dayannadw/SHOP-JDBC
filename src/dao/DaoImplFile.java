package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplFile implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Product> getInventory() {
		ArrayList<Product> products = new ArrayList<>();
	    
	    File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
	    
	    try {
	
	        FileReader fr = new FileReader(f);
	        BufferedReader br = new BufferedReader(fr);
	        
	        String line = br.readLine();
	        while (line != null) {
	            
	            String[] sections = line.split(";");
	            
	            String name = "";
	            double wholesalerPrice = 0.0;
	            int stock = 0;
	            
	            for (int i = 0; i < sections.length; i++) {
	                
	                String[] data = sections[i].split(":");
	                
	                if (data.length < 2) {
	                    continue; 
	                }
	                
	                switch (i) {
	                    case 0:
	                        // Formatear el nombre del producto
	                        name = data[1].trim();
	                        break;
	                        
	                    case 1:
	                        // Formatear el precio
	                        wholesalerPrice = Double.parseDouble(data[1].trim());
	                        break;
	                        
	                    case 2:
	                        // Formatear el stock
	                        stock = Integer.parseInt(data[1].trim());
	                        break;
	                        
	                    default:
	                        break;
	                }
	            }
	           
	            products.add(new Product(name, new Amount(wholesalerPrice), true, stock));
	            
	            line = br.readLine();
	        }
	        br.close();
	        
	    } catch (FileNotFoundException e) {
	        System.err.println("El archivo no se encontró: " + e.getMessage());
	    } catch (IOException e) {
	        System.err.println("Error de entrada/salida: " + e.getMessage());
	    }
	 // TODO Auto-generated method stub
	    return products; 
		
		
	}

	
		public boolean writeInventory(ArrayList<Product> inventory) {
		    LocalDate myObj = LocalDate.now();
		    String fileName = "inventory_" + myObj.toString() + ".txt";

		    File f = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);

		    try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {
		        
		        for (Product product : inventory) {
		            // Formato: 0;Name:Manzana;Price:20.0;Stock:50;
		            StringBuilder productLine = new StringBuilder();
		            productLine.append("0;"); 
		            productLine.append("Name:").append(product.getName()).append(";");
		            productLine.append("Price:").append(product.getPublicPrice()).append(";");
		            productLine.append("Stock:").append(product.getStock()).append(";");
		        
		            pw.println(productLine.toString()); 
		        }

		        return true; 
		    } catch (IOException e) {
		        System.err.println("Error al escribir en el archivo: " + e.getMessage());
		        return false; 
		    }
		}

	
}
