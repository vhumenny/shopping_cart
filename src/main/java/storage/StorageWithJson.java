package storage;

import cart.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Realisation of storage with products based on json file
 */
public class StorageWithJson implements Storage {
    private String path;
    private Map<String, Product> storageProducts;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageWithJson(String path) {
        this.path = path;
        this.storageProducts = load();
    }

    /*
     * Task (completed): rewrite method load() with checks and write tests for it
     * in the end we must get a Map with data? which are stored in resources by address
     * source root --> shopping_products_storage.json
     */
    @Override
    public Map<String, Product> load() {
        File jsonFile = new File(path);
        Map<String, Product> productMap = new LinkedHashMap<>();
        try {
            List<Product> productList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            productList.forEach(product -> productMap.put(product.getName(), product));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return productMap;
    }

    /*
     * This task must be clarified
     * Task: implement method write() with checks and write tests for it
     * data from storage Map must be written in file, which is stored in resources by address
     * source root --> shopping_products_storage.json
     */
    @Override
    public void write(Map<String, Product> storage) {
        File jsonFile = new File(path);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, storage.values());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product) {
        storageProducts.put(product.getName(), product);
    }

    @Override
    public void removeProduct(Product product) {
        storageProducts.remove(product.getName());
    }

    @Override
    public void reserveProduct(Product product, int quantity) {

    }

    /**
     * updating products quantity in storage
     */
    @Override
    public void updateQuantityProductsInStorage(String productName, int quantity) {
        storageProducts.get(productName).setQuantity(storageProducts.get(productName).getQuantity() - quantity);
    }

    @Override
    public List<String> getProductNames() {
        return storageProducts.keySet().stream().toList();
    }

    @Override
    public BigDecimal getProductPrice(String productName) {
        return storageProducts.get(productName).getPrice();
    }

    @Override
    public Map<String, Product> getStorageMap() {
        return storageProducts;
    }

    @Override
    public String toString() {
        return "StorageWithJson{" +
                "storageProducts=" + storageProducts +
                '}';
    }
}