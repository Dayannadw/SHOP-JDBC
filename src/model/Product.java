package model;

import java.util.Objects;

public class Product {
    private static final double EXPIRATION_RATE = 0.60;
    private static int totalProducts = 0;
    
    private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;

    public Product() {
       
    }


	public Product(String name, double wholesalerPrice, double publicPrice, boolean available, int stock) {
		super();
		this.id = totalProducts + 1;
		this.name = name;
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.publicPrice = new Amount(publicPrice);
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}
	
    public Product(int id, String name, double wholesalerPrice, boolean available, int stock) {
        if (name == null || wholesalerPrice < 0) {
            throw new IllegalArgumentException("Invalid product parameters");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.publicPrice = new Amount(wholesalerPrice * 2);
        this.available = available;
        this.stock = stock;
        totalProducts++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public Amount getPublicPrice() {
        return new Amount(publicPrice.getValue()); // Return a copy to prevent modification
    }

    public void setPublicPrice(Amount publicPrice) {
        if (publicPrice == null) {
            throw new IllegalArgumentException("Public price cannot be null");
        }
        this.publicPrice = new Amount(publicPrice.getValue());
    }

    public Amount getWholesalerPrice() {
        return new Amount(wholesalerPrice.getValue()); // Return a copy to prevent modification
    }

    public void setWholesalerPrice(Amount wholesalerPrice) {
        if (wholesalerPrice == null) {
            throw new IllegalArgumentException("Wholesaler price cannot be null");
        }
        this.wholesalerPrice = new Amount(wholesalerPrice.getValue());
        this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = stock;
        this.available = stock > 0;
    }

    public static int getTotalProducts() {
        return totalProducts;
    }

    public static void setTotalProducts(int total) {
        if (total < 0) {
            throw new IllegalArgumentException("Total products cannot be negative");
        }
        totalProducts = total;
    }

    public void expire() {
        double discountedPrice = this.publicPrice.getValue() * EXPIRATION_RATE;
        this.publicPrice.setValue(discountedPrice);
    }

    @Override
    public String toString() {
        return String.format("Product [id=%d, name=%s, publicPrice=%s, wholesalerPrice=%s, available=%b, stock=%d]",
                id, name, publicPrice, wholesalerPrice, available, stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
               available == product.available &&
               stock == product.stock &&
               Objects.equals(name, product.name) &&
               Objects.equals(publicPrice, product.publicPrice) &&
               Objects.equals(wholesalerPrice, product.wholesalerPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, publicPrice, wholesalerPrice, available, stock);
    }
}