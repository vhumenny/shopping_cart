package cart;

import discount.Discount;
import storage.Storage;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private Storage storage; // Storage containing map of products
    private Map<String, Product> cartMap;         // map of products, which are added in the cart
    private Map<String, BigDecimal> discountMap;  // map of products, which are added in the cart and discounts applied
    private BigDecimal price = new BigDecimal(00.00).setScale(2); // total price of products in cart (including discount)
    private BigDecimal discount = new BigDecimal(00.00).setScale(2); // total amount of discount on products in cart


    /**
     * When creating the constructor of cart we fill the storageMap with products from the storage to check the name
     * of products and their quantity before adding them in the cart.
     */
    public Cart(Storage storage) {
        this.cartMap = new LinkedHashMap<>();
        this.storage = storage;
        this.discountMap = new HashMap<>();
    }

    /*
     * Method description - it should add products in the cart
     * method parameters - name of product, and it's quantity
     * we check if product with such name and in needed quantity exists in the storage
     * tempPrice - temporary variable for storing price of product
     * Further we are checking if cart is not empty and stores product with such name - then we update its quantity
     * otherwise we add this product in cart
     * method printToConsole() - we print data to console
     * updateQuantityProductsInStorageMap() - we update quantity of product in storage
     * method updatePrice() - we update price (total price of all products in cart)
     */
    public void add(String productName, int quantity) {
        if (storage.isProductAvailable(productName, quantity)) {
            BigDecimal tempPrice = storage.getProductPrice(productName);
            if (!cartMap.isEmpty() && cartMap.containsKey(productName)) {
                int newQuantity = cartMap.get(productName).getQuantity() + quantity;
                cartMap.put(productName, new Product(productName, tempPrice, newQuantity));
            } else {
                cartMap.put(productName, new Product(productName, tempPrice, quantity));
            }
            addPrintToConsole(quantity, productName);
            storage.removeProduct(productName, quantity);
            price = updatePrice();
        }
    }

    public void remove(String productName, int quantity) {
        if (cartMap.containsKey(productName)) {
            if (cartMap.get(productName).getQuantity() == quantity) {
                Product tempProduct = cartMap.get(productName);
                removePrintToConsole(quantity, productName);
                cartMap.remove(productName);
                storage.addProduct(tempProduct, quantity);
            } else if (cartMap.get(productName).getQuantity() > quantity) {
                cartMap.get(productName).setQuantity(cartMap.get(productName).getQuantity() - quantity);
                removePrintToConsole(quantity, productName);
                storage.addProduct(cartMap.get(productName), quantity);
            } else {
                System.out.printf("Cart doesn't contain %s in quantity %d right now there is only next quantity: %d%n",
                        productName, quantity, cartMap.get(productName).getQuantity());
            }

        } else System.out.println("You don't have " + productName + " in cart. Please enter another Product.");
    }

    // output data to the console according to the technical task
    private void addPrintToConsole(int quantity, String productName) {
        System.out.println(quantity + " " + productName + "(s) vas added");
    }
    private void removePrintToConsole(int quantity, String productName) {
        System.out.println(quantity + " " + productName + "(s) vas removed");
    }

    /**
     * data output to console in next format: discount:00.00,price:XX.50
     */
    public void price() {
        System.out.println(String.format("discount:%s, price:%s", discount, price));
        System.out.println(this); // for logging, call of method toString on cart
    }

    /**
     * total price of products without discounts
     */
    public BigDecimal totalPriceWithoutDiscount() {
        List<Product> list = new ArrayList<>(cartMap.values());
        var sum = BigDecimal.ZERO;
        for (Product product : list) {
            sum = sum.add(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return sum.setScale(2);
    }

    /*
     * Method description
     * Method parameters - type of discount and name of product
     * checking if product with such name exists in cart
     * if true - we get discount value (discountProductValue)
     * if discount value not 0, then discount worked and then:
     * we update total discount value in cart (discount)
     * then update total price of products in cart (price)
     * next we add in map with discounts product name and discount value on it (BigDecimal).
     * output to console - discount added
     */
    public void applyDiscount(Discount discountType, String productName) {
        if (isProductExistsInCart(productName)) {
            BigDecimal discountProductValue = discountType.getDiscount(productName, cartMap).setScale(2);
            if (discountProductValue.intValue() != 0) {
                discount = updateDiscount(productName, discountProductValue);
                price = updatePrice();
                discountMap.put(productName, discountProductValue);
                System.out.printf("discount added. Details: apply %s by  %s. Discount value - %s $ %n",
                        discountType.getClass().getSimpleName(), productName, discountProductValue);
            }
        }
    }

    /**
     * Method description
     * Method parameters - name of product and amount of discount applied to product
     * checking if name of product exists as key in map with discounts.
     * If true then discount was already applied in this product, then we must subtract old discount
     * (oldDiscountValueProduct) from total discount on all products in cart(discount)
     * Because according to the totalPriceWithoutDiscount - if two discounts are applied to one product -
     * then we must apply only the last one.
     * return - we add discount on this product to total discount on all products.
     */
    private BigDecimal updateDiscount(String productName, BigDecimal discountProductValue) {
        if (discountMap.containsKey(productName)) {
            BigDecimal oldDiscountValueProduct = discountMap.get(productName);
            discount = discount.subtract(oldDiscountValueProduct);
        }
        return discount.add(discountProductValue).setScale(2);
    }

    /**
     * updating total price of products in cart
     */
    private BigDecimal updatePrice() {
        return totalPriceWithoutDiscount().subtract(discount);
    }

    private boolean isProductExistsInCart(String productName) {
        if (!cartMap.containsKey(productName)) {
            System.out.println("Product " + productName + " doesn't exist in cart. " +
                    "Therefore discount cannot be applied.");
            return false;
        } else {
            return true;
        }
    }


    public Storage getStorage() {
        return storage;
    }

    public Map<String, Product> getCartMap() {
        return cartMap;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Map<String, BigDecimal> getDiscountMap() {
        return discountMap;
    }

    @Override
    public String toString() {
        return "~~~~~~~~~~~~~~~~~  CART (LOG) ~~~~~~~~~~~~~~~~~\n" +
                "cartMap=" + cartMap +
                ",\n storage=" + storage +
                ",\n discountMap=" + discountMap +
                ",\n price=" + price +
                ",\n discount=" + discount +
                ",\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                '\n';
    }

}
