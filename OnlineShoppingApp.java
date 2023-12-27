import java.util.*;

class Product {
    public String name;
    public double price;
    public int quantity;
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    // Getters and setters for product details
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int qauntity){
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}

class User {
    private String username;
    private String password;
    double balance;
    ShoppingCart myCart = new ShoppingCart();
    // Other user details as needed
    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
    // Getters and setters for user details
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public double getBalance() {
        return balance;
    }
    public void addBalance(double a) {
         this.balance += a ;
    }

}

class ShoppingCart {
    private Map<Product, Integer> items;
    double totalCost = 0.0;
    public ShoppingCart() {
        this.items = new HashMap<>();
    }
    public void addProduct(Product product, int quantity) {
        if(product.quantity >= 1){
            items.put(product, items.getOrDefault(product, 0) + quantity);
        }
        else
            System.out.println("product is not available.");

    }

    public void removeProduct(Product product, int quantity) {
        int currentQuantity = items.getOrDefault(product, 0);
        if (currentQuantity <= quantity) {
            items.remove(product);
        } else {
            items.put(product, currentQuantity - quantity);
        }
    }
    public void viewCartProducts() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Products in Cart:");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(product.getName() + " - Quantity: " + quantity);
            }
        }
    }
    public double calculateTotalCost() {

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalCost += product.getPrice() * quantity;
        }
        return totalCost;
    }
    public void confirmOrder(User user) {
        double orderTotal = calculateTotalCost();
        if (user.getBalance() >= orderTotal) {
            user.balance -= orderTotal;
            System.out.println("Payment successful. Order confirmed! Total cost: Rs." + orderTotal);
            items.clear();
            totalCost = 0.0;
        } else {
            double balanceDifference = orderTotal - user.getBalance();
            System.out.println("Insufficient balance. Additional amount needed: Rs." + balanceDifference);
        }
    }

}

class Shop {
    List<Product> availableProducts;

    public Shop() {
        this.availableProducts = new ArrayList<>();
    }

    public void addProductToShop(String name, double price, int quantity) {
        availableProducts.add(new Product(name, price, quantity));
    }

    public void viewAvailableProducts() {
        if (availableProducts.isEmpty()) {
            System.out.println("No products available in the shop.");
        } else {
            System.out.println("Available Products:");
            for (Product product : availableProducts) {
                System.out.println("Name: " + product.getName() + ", Price: Rs." + product.getPrice() +
                        ", Quantity: " + product.getQuantity());
            }
        }
    }

}




public class OnlineShoppingApp {

    private List<User> users;
    private User currentUser;
    private boolean isLoggedIn;

    public OnlineShoppingApp() {
        this.users = new ArrayList<>();
        this.isLoggedIn = false;
    }

    public void register(String username, String password, double balance) {
        users.add(new User(username, password, balance));
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isLoggedIn = true;
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        isLoggedIn = false;
        currentUser = null;
    }

    public static void main(String[] args) {
        OnlineShoppingApp shoppingApp = new OnlineShoppingApp();
        Shop myShop = new Shop();
        myShop.addProductToShop("Product 1", 10.0, 4);
        myShop.addProductToShop("Product 2", 20.0, 5);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!shoppingApp.isLoggedIn) {
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.println("Enter password: ");
                        String password = scanner.nextLine();
                        if (shoppingApp.login(username, password)) {
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Invalid credentials.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        System.out.println("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        System.out.println("Add some balance to your account: ");
                        double newBalance = scanner.nextDouble();
                        shoppingApp.register(newUsername, newPassword, newBalance);
                        System.out.println("Registration successful!");
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("1. View Available Products");
                System.out.println("2. Add Product to Cart");
                System.out.println("3. View Cart");
                System.out.println("4. Checkout");
                System.out.println("5. View Balance");
                System.out.println("6. Add Balance");
                System.out.println("7. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        myShop.viewAvailableProducts();
                        break;
                    case 2:
                        System.out.println("Enter the name of the product to add: ");
                        String productName = scanner.nextLine();
                        scanner.nextLine();
                        Product selectedProduct = null;

                        for (Product product : myShop.availableProducts) {
                            if (product.getName().equalsIgnoreCase(productName)) {
                                selectedProduct = product;
                                break;
                            }
                        }

                        if (selectedProduct != null) {
                            System.out.println("Enter the quantity to add: ");
                            int quantityToAdd = scanner.nextInt();
                            shoppingApp.currentUser.myCart.addProduct(selectedProduct, quantityToAdd);
                            System.out.println(quantityToAdd + " " + selectedProduct.getName() + "(s) added to cart.");
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;

                    case 3:
                        shoppingApp.currentUser.myCart.viewCartProducts();
                        break;

                    case 4:
                        shoppingApp.currentUser.myCart.confirmOrder(shoppingApp.currentUser);
                        break;
                    case 5:
                        System.out.println("Your balance is Rs. " + shoppingApp.currentUser.balance);
                        break;
                    case 6:
                        System.out.println("Enter amount to be added:");
                        double a = scanner.nextDouble();
                        scanner.nextLine();
                        shoppingApp.currentUser.balance += a ;
                        System.out.println("Amount added successfully...");
                        break;
                    case 7:
                        shoppingApp.logout();
                        System.out.println("Logged out.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

}


