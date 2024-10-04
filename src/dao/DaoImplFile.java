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
	    
	    // Localizar el archivo, la ruta y el nombre
	    File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
	    
	    try {
	        // Envolver en las clases adecuadas
	        FileReader fr = new FileReader(f);
	        BufferedReader br = new BufferedReader(fr);
	        
	        // Leer la primera línea
	        String line = br.readLine();
	        
	        // Procesar y leer la siguiente línea hasta el final del archivo
	        while (line != null) {
	            // Dividir en secciones
	            String[] sections = line.split(";");
	            
	            String name = "";
	            double wholesalerPrice = 0.0;
	            int stock = 0;
	            
	            // Leer cada sección
	            for (int i = 0; i < sections.length; i++) {
	                // Dividir los datos en clave(0) y valor(1) 
	                String[] data = sections[i].split(":");
	                
	                if (data.length < 2) {
	                    continue; // Evitar errores si el formato no es correcto
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
	            // Añadir producto a la lista de productos
	            products.add(new Product(name, new Amount(wholesalerPrice), true, stock));
	            
	            // Leer la siguiente línea
	            line = br.readLine();
	        }
	        br.close();
	        
	    } catch (FileNotFoundException e) {
	        System.err.println("El archivo no se encontró: " + e.getMessage());
	    } catch (IOException e) {
	        System.err.println("Error de entrada/salida: " + e.getMessage());
	    }
	 // TODO Auto-generated method stub
	    return products; // Devolver la lista de productos
		
		
	}

	
		public boolean writeInventory(ArrayList<Product> inventory) {
		    // Define el nombre del archivo basado en la fecha
		    LocalDate myObj = LocalDate.now();
		    String fileName = "inventory_" + myObj.toString() + ".txt";

		    // Localizar el archivo, la ruta y el nombre
		    File f = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);

		    // Inicializa el PrintWriter y FileWriter
		    try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {
		        // Formatear cada producto en el inventario
		        for (Product product : inventory) {
		            // Formato: 0;Name:Manzana;Price:20.0;Stock:50;
		            StringBuilder productLine = new StringBuilder();
		            productLine.append("0;"); // Se puede usar un número de contador o dejarlo en 0 si no es necesario.
		            productLine.append("Name:").append(product.getName()).append(";");
		            productLine.append("Price:").append(product.getPublicPrice()).append(";");
		            productLine.append("Stock:").append(product.getStock()).append(";");

		            // Escribir la línea en el archivo
		            pw.println(productLine.toString()); // Agrega un salto de línea automáticamente
		        }

		        return true; // La escritura fue exitosa
		    } catch (IOException e) {
		        System.err.println("Error al escribir en el archivo: " + e.getMessage());
		        return false; // La escritura falló
		    }
		}

	
}
