package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

import java.util.ArrayList;
import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {
    
    private ArrayList<Product> products = new ArrayList<>(); 
    private Product product; 
    private StringBuilder buffer = new StringBuilder(); 
    
    
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
    	this.products = products; 
    }

    @Override
    public void startDocument() throws SAXException {
      
        System.out.println("Starting...");
    }

    @Override
    public void endDocument() throws SAXException {
       
        System.out.println("Finished.");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        buffer.setLength(0); 
        switch (qName) {
            case "product": 
            	String productName = attributes.getValue("name");
                product = new Product(productName, new Amount(0), true, 0);
                
            case "wholesalerPrice":
                buffer.setLength(0);
                break;

            case "stock":
            	 buffer.setLength(0);
                break;
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        
        switch (qName) {
        case "WholesalerPrise":
        	double wholesalerPriceValue = Double.parseDouble(buffer.toString());
            product.setWholesalerPrice(new Amount(wholesalerPriceValue));
            break;
        case "Stock":
            int stock = Integer.parseInt(buffer.toString());
            product.setStock(stock);
            product.setAvailable(stock > 0);
            break;
        case "product":
            products.add(product);
            break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }
}
