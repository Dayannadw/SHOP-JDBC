package xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Amount;
import model.Product;

public class DomWriter extends DefaultHandler {
    private Document document;
    private ArrayList<Product> copyInventory;

    public DomWriter(ArrayList<Product> inventory) {
        try {
        	copyInventory = inventory;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println("ERROR GENERANDO COCUMENTO");
        }
    }

    public boolean generateDocument() {
        // PARENT NODE
        Element products = document.createElement("products");
        products.setAttribute("total", String.valueOf(copyInventory.size()));
        document.appendChild(products);

        for (Product productItem : copyInventory) {
	      
	        Element product = document.createElement("product");
	        product.setAttribute("id", String.valueOf(productItem.getId()));
	        products.appendChild(product);
	        
	        Element name = document.createElement("name");
	        name.setTextContent(productItem.getName());
	        product.appendChild(name);
	        
	        Element price = document.createElement("price");
	        price.setAttribute("Currency", Amount.getCurrency());
	        price.setTextContent(String.valueOf(productItem.getPublicPrice()));
	        product.appendChild(price);
	        
	        Element stock = document.createElement("stock");
	        stock.setTextContent(String.valueOf(productItem.getStock()));
	        product.appendChild(stock);
	        
	    }
	    
	    if(generateXml()) {
	    	return true;
	    }else {
	    	return false;
	    }
    }

    

    private boolean generateXml() {
    	boolean generated = false;
		try {
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Source source = new DOMSource(document);
			File file = new File("files/inventory_"+date+".xml");
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			Result result = new StreamResult(pw);
			
			transformer.transform(source, result);
			generated = true;
			
			
		} catch (IOException e) {
			System.out.println("ERROR CREATING FILE");
		} catch (TransformerException e) {
			System.out.println("ERROR TRASNFORMING THE DOCUMENT");
		}
		return generated;
    }
}