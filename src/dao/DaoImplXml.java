package dao;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

import org.xml.sax.SAXException;

import model.Employee;
import model.Product;
import xml.DomWriter;
import xml.SaxReader;

public class DaoImplXml implements Dao {

    private static final String XML_FILE_PATH = System.getProperty("user.dir") + File.separator + "files" + File.separator + "inventory.xml";

    @Override
    public ArrayList<Product> getInventory() {
		ArrayList<Product> products = null;	
		try {
			SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
			SAXParser saxParse = saxParseFactory.newSAXParser();
			File file = new File("files/inputInventory.xml");
			SaxReader handler = new SaxReader();
			saxParse.parse(file, handler);
			
			products = handler.getProducts();
			
			System.out.println("Inventory loaded");
			for(Product product : products) {
				System.out.println(product);
			}
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		return products;
	}

    @Override
    public boolean writeInventory(ArrayList<Product> inventory) {
    	DomWriter domWriter = new DomWriter(inventory);
		return domWriter.generateDocument();
    }

    @Override
    public void connect() {
        // No se implementa en esta versión
    }

    @Override
    public void disconnect() {
        // No se implementa en esta versión
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        // No se implementa en esta versión
        return null;
    }
}
