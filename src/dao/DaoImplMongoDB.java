package dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import model.Amount;
import model.Employee;
import model.Product;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DaoImplMongoDB implements Dao {

    MongoCollection<Document> collection;
    MongoDatabase mongoDB;
    ObjectId id;

    @Override
    public void connect() {
        String uri = "mongodb://localhost:27017";
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        mongoDB = mongoClient.getDatabase("shop");
    }

    @Override
    public void disconnect() {
        // Implementación pendiente
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        connect();
        collection = mongoDB.getCollection("users");
        Document document = collection.find(Filters.and(Filters.eq("employeeId", employeeId), Filters.eq("password", password))).first();
        if (document != null) {
            return new Employee(document.getInteger("employeeId"), document.getString("password"));
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Product> getInventory() {
        connect();
        ArrayList<Product> products = new ArrayList<Product>();

        collection = mongoDB.getCollection("inventory");
        Document document = collection.find().first();
        if(document == null) {
            addDefaultProducts();
        }

        for (Document doc : collection.find()) {
            String name = doc.getString("name");
            boolean available = doc.getBoolean("available", true);
            int stock = doc.getInteger("stock", 0);

            // Obtener el subdocumento wholesalerPrice
            Document wholesalerPriceDoc = (Document) doc.get("wholesalerPrice");
            double wholesalerPriceValue = wholesalerPriceDoc.getDouble("value");

            System.out.println("document read on list " + ": " + doc.toJson());
            Product product = new Product(name, new Amount(wholesalerPriceValue), stock, available);
            products.add(product);
        }

        return products;
    }

    @Override
    public boolean writeInventory(ArrayList<Product> inventory) {
        connect();
        collection = mongoDB.getCollection("historical_inventory");

        for (Product productData : inventory) {
            LocalDateTime dateTimeNow = LocalDateTime.now();

            productData.getWholesalerPrice();
            Document document = new Document("_id", new ObjectId())
                    .append("id", productData.getId())
                    .append("name", productData.getName())
                    .append("wholesalerPrice", new Document("value", productData.getWholesalerPrice().getValue()).append("currency", Amount.getCurrency()))
                    .append("available", productData.isAvailable())
                    .append("stock", productData.getStock())
                    .append("created_at", dateTimeNow);

            collection.insertOne(document);
        }

        return true;
    }

    @Override
    public boolean updateProduct(Product product) {
        connect();
        collection = mongoDB.getCollection("inventory");

        Document document = collection.find(Filters.regex("name", "^" + product.getName() + "$", "i")).first();
        if (document == null) {
            return false;
        } else {
            collection.updateOne(document, Updates.set("stock", product.getStock()));
            return true;
        }
    }


//    public boolean deleteProductInventory(String name) {
//        connect();
//        collection = mongoDatabase.getCollection("inventory");
//        Document document = collection.find(Filters.regex("name", "^" + name + "$", "i")).first();
//
//        if (document == null) {
//            System.out.println("fail");
//            return false;
//        } else {
//            System.out.println("succes");
//            collection.deleteOne(document);
//            return true;
//        }
//    }


    @Override
    public void deleteProduct(Product product) {
        try {
            MongoCollection<Document> collection = mongoDB.getCollection("inventory");

            Document query = new Document("id", product.getId());

            collection.deleteOne(query);
            System.out.println("Producto eliminado (ID: " + product.getId() + ").");

        } catch (Exception e) {
            System.err.println("No se ha podido eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product) {
        try {
            MongoCollection<Document> collection = mongoDB.getCollection("inventory");

            Document productDoc = new Document()
                    .append("id", product.getId())
                    .append("name", product.getName())
                    .append("wholesalerPrice", new Document("value", product.getWholesalerPrice().getValue())
                            .append("currency", "€"))
                    .append("stock", product.getStock())
                    .append("available", product.isAvailable())
                    .append("created_at", new Date());

            collection.insertOne(productDoc);
        } catch (Exception e) {
            System.err.println("Unable to add product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteProductInventory(String name) {
        return false;
    }

    private void addDefaultProducts() {
        Document product1 = new Document("name", "Manzana")
                .append("wholesalerPrice", new Document("value", 10.0).append("currency", "€"))
                .append("available", true)
                .append("stock", 10)
                .append("id", 1);
        Document product2 = new Document("name", "Fresa")
                .append("wholesalerPrice", new Document("value", 5.0).append("currency", "€"))
                .append("available", true)
                .append("stock", 20)
                .append("id", 4);

        collection.insertMany(Arrays.asList(product1, product2));
    }

}

