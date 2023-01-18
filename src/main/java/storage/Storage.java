package storage;

import cart.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/*
 * Method load() loads data in our Map
 * if we parse file json - then we get container Map with filled with data from this file,
 * if we parse file with connecting to database - it will have its own realisation,
 * but in the end we will also get Map filled from database (not sure for now)
 *
 * Method write() - writes updated data to the file
 * Method getStorage() - getter of Map with data
 */
public interface Storage {
    Map<String, Product> load();

    void write();

    void addProduct(Product product, int quantity);

    void removeProduct(String productName, int quantity);

    List<String> getProductNames();

    BigDecimal getProductPrice(String productName);

    boolean isProductAvailable(String productName, int quantity);
}
