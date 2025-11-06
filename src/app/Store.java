package app;

import dataStructures.list.*;

import java.io.InputStream;
import java.util.Scanner;


public class Store {

    public void loadProducts() {
        InputStream in = Store.class.getResourceAsStream("/resources/products.csv");

        if (in == null) {
            System.out.println("products load failed");
            return;
        }

        Scanner file = new Scanner(in);
        file.nextLine();

        while (file.hasNextLine()) {
            String[] x = file.nextLine().split(",");
            int id = Integer.parseInt(x[0].trim());
            String name = x[1].trim();
            double price = Double.parseDouble(x[2].trim());
            int stock = Integer.parseInt(x[3].trim());

            products.insert(new Product(id, name, price, stock));
        }
        file.close();
        System.out.println("Products loaded successfully.");
    }

    public void loadOrders() {
        InputStream in = Store.class.getResourceAsStream("/resources/orders.csv");
        if (in == null) {
            System.out.println("orders load failed");
            return;
        }

        Scanner file = new Scanner(in);
        file.nextLine();

        while (file.hasNextLine()) {
            String line = file.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] x = line.split(",");

            int orderId = Integer.parseInt(x[0].trim());
            int customerId = Integer.parseInt(x[1].trim());
            String[] pids = x[2].replace("\"", "").trim().split(";");
            String date = x[4].trim();
            String status = x[5].trim();
            MyArrayList<Product> cart = new MyArrayList<>(100);

            for (String pid : pids) {
                pid = pid.trim();
                if (pid.isEmpty()) continue;
                if (moveToProduct(Integer.parseInt(pid))) {
                    cart.insert(products.retrieve());
                }
            }
            Order o = new Order(orderId, cart, date, status);
            if (moveToCustomer(customerId)) {
                customers.retrieve().addOrder(o);
            }
            orders.insert(o);
        }
        file.close();
        System.out.println("Orders loaded successfully.");
    }



    public void loadReviews() {
        InputStream in = Store.class.getResourceAsStream("/resources/reviews.csv");
        if (in == null) {
            System.out.println("reviews load failed");
            return;
        }

        Scanner file = new Scanner(in);
        file.nextLine();

        while (file.hasNextLine()) {
            String[] x = file.nextLine().split(",");
            int rId = Integer.parseInt(x[0].trim());
            int pId = Integer.parseInt(x[1].trim());
            int cId = Integer.parseInt(x[2].trim());
            int rating = Integer.parseInt(x[3].trim());
            String comment = x[4].trim();

            if (moveToProduct(pId) && moveToCustomer(cId)) {
                addReview(rId , pId , cId , rating , comment);
            }
        }
        file.close();
        System.out.println("Reviews loaded successfully.");
    }

    public void loadCustomers() {
        InputStream in = Store.class.getResourceAsStream("/resources/customers.csv");
        if (in == null) {
            System.out.println("customers load failed");
            return;
        }

        Scanner file = new Scanner(in);
        file.nextLine();

        while (file.hasNextLine()) {
            String[] x = file.nextLine().split(",");

            int id = Integer.parseInt(x[0].trim());
            String name = x[1].trim();
            String email = x[2].trim();

            customers.insert(new Customer(id, name, email));
        }

        file.close();
        System.out.println("Customers loaded successfully.");
    }

    String name;
    private MyLinkedList<Order> orders;
    private MyLinkedList<Customer> customers;
    private MyArrayList<Product> products;
    public static final int MAX_PRODUCTS = 100;

    public Store(String name) {
        this.name = name;
        this.products = new MyArrayList<>(MAX_PRODUCTS);
        this.customers = new MyLinkedList<>();
        this.orders = new MyLinkedList<>();
    }

    private boolean moveToProduct(String name) { //moves the current by Name , Time Complexity : O(n)
        if (products.empty()) return false;

        products.findFirst();
        while (!products.last()) {
            if (products.retrieve().getName().equals(name))
                return true;
            products.findNext();
        }
        return products.retrieve().getName().equals(name);
    }

    private boolean moveToProduct(int id) { //moves the current by ID , Time Complexity : O(n)
        if (products.empty())
            return false;
        products.findFirst();
        while (!products.last()) {
            if (products.retrieve().getId() == id)
                return true;
            products.findNext();
        }
        return products.retrieve().getId() == id;
    }

    private boolean moveToCustomer(String email) { //moves the current by email, Time Complexity : O(n)
        if (customers.empty()) return false;

        customers.findFirst();
        while (!customers.last()) {
            if (customers.retrieve().getEmail().equals(email))
                return true;
            customers.findNext();
        }
        return customers.retrieve().getEmail().equals(email);
    }

    private boolean moveToCustomer(int id) { //moves the current by ID , Time Complexity : O(n)
        if (customers.empty())
            return false;
        customers.findFirst();
        while (!customers.last()) {
            if (customers.retrieve().getId() == id)
                return true;
            customers.findNext();
        }
        return customers.retrieve().getId() == id;
    }

    private boolean moveToOrder(int id) { //moves the current by ID , Time Complexity : O(n)
        if (orders.empty())
            return false;
        orders.findFirst();
        while (!orders.last()) {
            if (orders.retrieve().getId() == id)
                return true;
            orders.findNext();
        }
        return orders.retrieve().getId() == id;
    }
    public MyLinkedList<Order> getOrders() {
        return orders;
    }

    public void setOrders(MyLinkedList<Order> orders) {
        this.orders = orders;
    }

    //customer operations
//    o Register new customer.
//    o Place a new order for a specific customer.
//    o View order history.

    public boolean signUp(String name, String email) {
        if (moveToCustomer(email))
            return false;
        customers.insert(new Customer(name, email));
        return true;
    }

    public Customer login(String email) {
        if (moveToCustomer(email))
            return customers.retrieve();
        return null;
    }

    public void order(int cId, Order order) {
        moveToCustomer(cId);
        customers.retrieve().addOrder(order);
        orders.insert(order);
    }

    public void orderHistory(int cId) {
        moveToCustomer(cId);
        customers.retrieve().viewOrderHistory();
    }
    public Customer searchCustomer(int id){
        if (customers.empty()) {
            return null;
        }
        boolean found = moveToCustomer(id); //O(n) search method
        if (found)
            return customers.retrieve();
        return null;
    }

//    Order Operations:
//    o Create/cancel order.
//    o Update order status.
//    o Search order by ID.

    public void updateOrderStatus(int oId, String status) {
        if(moveToOrder(oId))
            orders.retrieve().setStatus(status);
        else System.out.println("there is no order with id : " + oId);
    }

    public void cancelOrder(int id) { //Time Complexity : O(n)
        if (orders.empty()) {
            System.out.println("there is no orders yet to cancel");
            return;
        }
        boolean found = moveToOrder(id); //O(n) search method
        if (found) {
            products.remove(); //O(n) remove method in MyArrayList
            System.out.println("Order: [" + id + "]" + " has been removed successfully");
        } else
            System.out.println("there is no Order with ID: " + id);
    }
    public Order searchOrder(int oId){
        if(moveToOrder(oId))
            return orders.retrieve();
        System.out.println("there is no order with id : " + oId);
        return null;
    }

    //• Review Operations:
    //o Add/edit review.
    //o Get an average rating for product.(inside product)
    public void addReview(int rId, int pId ,int cId ,int rating , String comment){
       moveToCustomer(cId);
       moveToProduct(pId);
       Review r = new Review(rId , customers.retrieve(), rating , comment);
       searchProduct(pId).addReview(r);
       customers.retrieve().addReview(r);
    }
    public void addReview(int pId ,int cId ,int rating , String comment){
        moveToCustomer(cId);
        moveToProduct(pId);
        Review r = new Review(customers.retrieve(), rating , comment);
        searchProduct(pId).addReview(r);
        customers.retrieve().addReview(r);
    }
    public void editReview(int cId, int pId, int rating, String comment, int reviewNumber) {

        moveToProduct(pId);
        Product p = products.retrieve();
        MyLinkedList<Review> reviews = p.getReviews();
        Review r = null;
        int count = 0;

        reviews.findFirst();
        while (true) {
            if (reviews.retrieve().customer.getId() == cId) {
                count++;
                if (reviewNumber == -1) {
                    // Store last one found, just in case it's the only one
                    r = reviews.retrieve();
                }
            }
            if (reviews.last()) break;
            reviews.findNext();
        }

        if (count == 0) {
            System.out.println("You have no reviews on this product.");
            return;
        }

        // Only one review -> update directly
        if (count == 1 && reviewNumber == -1) {
            r.setRating(rating);
            r.setComment(comment);
            System.out.println("Review updated.");
            return;
        }

        // Multiple reviews, but user did not pick yet -> list them only
        if (reviewNumber == -1) {
            int i = 1;
            reviews.findFirst();
            while (true) {
                if (reviews.retrieve().customer.getId() == cId) {
                    System.out.println(i + "-" + reviews.retrieve());
                    i++;
                }
                if (reviews.last()) break;
                reviews.findNext();
            }
            System.out.println("Choose review number in MAIN and call editReview again.");
            return;
        }

        // Multiple reviews, user has selected the review
        int index = 1;
        reviews.findFirst();
        while (true) {
            if (reviews.retrieve().customer.getId() == cId) {
                if (index == reviewNumber) {
                    r = reviews.retrieve();
                    r.setRating(rating);
                    r.setComment(comment);
                    System.out.println("Review updated.");
                    return;
                }
                index++;
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
    }

    //    product Operations:
//      o Add/remove/update products.
//      o Search by ID or name (linear).
//      o Track out-of-stock products.
    public void addProduct(String name, double price, int stock) { //Time Complexity : O(1)
        products.insert(new Product(name, price, stock));
    }
    public void removeProduct(int id) { //Time Complexity : O(n)
        if (products.empty()) {
            System.out.println("Your store is empty!, nothing to remove yet");
            return;
        }
        boolean found = moveToProduct(id); //O(n) search method
        if (found) {
            String name = products.retrieve().getName();
            products.remove(); //O(n) remove method in MyArrayList
            System.out.println("Product: " + name + "[" + id + "]" + " has been removed successfully");
        } else
            System.out.println("there is no product with ID: " + id);
    }

    public void updateProduct(int id, String fName, double fPrice, int fStock) { //Time Complexity : O(n)
        if (products.empty()) {
            System.out.println("Your store is empty!, nothing to update yet");
            return;
        }
        boolean found = moveToProduct(id); //O(n) search method
        if (found) {
            Product p = products.retrieve(); //O(1)
            System.out.println(p + ".\n has been successfully updated to:");
            p.setProduct(fName, fPrice, fStock);  //O(1)
            System.out.println(p);
        } else
            System.out.println("there is no product with ID: " + id);
    }

    public Product searchProduct(int id) {//Time Complexity : O(n)
        if (products.empty()) {
            return null;
        }
        boolean found = moveToProduct(id); //O(n) search method
        if (found)
            return products.retrieve();
        return null;
    }

    public Product searchProduct(String name) {//Time Complexity : O(n)
        if (products.empty()) {
            return null;
        }
        boolean found = moveToProduct(name); //O(n) search method
        if (found)
            return products.retrieve();
        return null;
    }

    public MyArrayList<Product> outOfStockProducts() {
        MyArrayList<Product> outOfStock = new MyArrayList<>(MAX_PRODUCTS);
        products.findFirst();
        while (true) {
            if (products.retrieve().getStock() == 0)
                outOfStock.insert(products.retrieve());
            if (products.last())
                break;
            products.findNext();
        }
        return outOfStock;
    }

    public void displayProducts() {
        if (products.empty()) {
            System.out.println("the store has no products yet");
            return;
        }
        products.findFirst();
        while (true) {
            System.out.println(products.retrieve());
            if (products.last())
                break;
            products.findNext();
        }
    }


//Requirements
//• Read data from CSV file that contains products, customers, orders, and reviews.
//• You can add a product, customer, and place an order.
//• Customers can add reviews to products.
//• Extract reviews from a specific customer for all products with the most efficient
//linear data structure possible.
//• Suggest "top 3 products" by average rating.
//• All Orders between two dates
//• Given two customers IDs, show a list of common products that have been reviewed
//with an average rating of more than 4 out of 5.


    public void displayTopRatedProducts(){
        if (products.empty()) {
            return;
        }
        Product best1 = null , best2 = null , best3 = null;
        products.findFirst();
        while(true){
            if(best1 == null || products.retrieve().averageRating() > best1.averageRating()){
                best3 = best2;
                best2 = best1;
                best1 = products.retrieve();
            }else if(best2 == null || products.retrieve().averageRating() > best2.averageRating()) {
                best3 = best2;
                best2 = products.retrieve();
            }else if(best3 == null || products.retrieve().averageRating() > best3.averageRating()){
                best3 = products.retrieve();
            }
            if(products.last())
                break;
            products.findNext();
        }
        System.out.println("TOP 3 Products:");
        if(best1 != null)
            System.out.printf("1st place : [%3d] %-25s Rating :%4.1f\n" ,best1.getId() ,best1.getName() , best1.averageRating());
        if(best2 != null)
            System.out.printf("2nd place : [%3d] %-25s Rating :%4.1f\n" ,best2.getId(), best2.getName() , best2.averageRating());
        if(best3 != null)
            System.out.printf("3rd place : [%3d] %-25s Rating :%4.1f\n" ,best3.getId(),best3.getName() , best3.averageRating());

    }

    public void displayOrdersBetweenDates(String startDate, String endDate) {

        if (orders.empty()) {
            System.out.println("No orders available yet");
            return;
        }
        int start = Integer.parseInt(startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10));
        int end   = Integer.parseInt(endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10));
        boolean found = false;
        orders.findFirst();
        while (true) {
            Order o = orders.retrieve();
            String d = o.getDate();
            int od = Integer.parseInt(d.substring(0, 4) + d.substring(5, 7) + d.substring(8, 10));
            if (od >= start && od <= end) {
                System.out.println(o);
                found = true;
            }
            if (orders.last())
                break;
            orders.findNext();
        }
        if (!found) {
            System.out.println("No orders in this date range.");
        }
    }
    private boolean hasReviewed(Product p, int cId) {
        MyLinkedList<Review> list = p.getReviews();
        if (list.empty()) return false;

        list.findFirst();
        while (true) {
            if (list.retrieve().customer.getId() == cId)
                return true;

            if (list.last()) break;
            list.findNext();
        }
        return false;
    }
    public void showCommonReviewedProducts(int cId1, int cId2) {

        if (!moveToCustomer(cId1)) {
            System.out.println("Customer [" + cId1 + "] not found.");
            return;
        }
        if (!moveToCustomer(cId2)) {
            System.out.println("Customer [" + cId2 + "] not found.");
            return;
        }
        boolean found = false;
        products.findFirst();
        while (true) {
            Product p = products.retrieve();
            if (hasReviewed(p, cId1) && hasReviewed(p, cId2) && p.averageRating() > 4.0) {
                System.out.println(p);
                found = true;
            }
            if (products.last()) break;
            products.findNext();
        }
        if (!found) {
            System.out.println("No common >4 rated products were found.");
        }
    }
}