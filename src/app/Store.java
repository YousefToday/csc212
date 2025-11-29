package app;

import dataStructures.Trees.AVL.*;
import dataStructures.list.*;
import java.io.InputStream;
import java.util.Scanner;


public class Store {
    //private MyLinkedList<Order> orders;
    //private MyLinkedList<Customer> customers;
    //private MyArrayList<Product> products;
    //public static final int MAX_PRODUCTS = 100;

    private String name;
    private AVLTree<Product> productsById;
    private AVLTree<Product> productsByName;
    private AVLTree<Customer> customersById;
    private AVLTree<Customer> customersByName;
    private AVLTree<Order> ordersById;

    public Store(String name) {
        this.name = name;

        //this.products = new MyArrayList<>(MAX_PRODUCTS);
        //this.orders = new MyLinkedList<>();
        //this.customers = new MyLinkedList<>();

        this.productsById = new AVLTree<>();
        this.productsByName = new AVLTree<>();
        this.customersById = new AVLTree<>();
        this.customersByName = new AVLTree<>();
        this.ordersById = new AVLTree<>();
    }
    public String getName(){
        return name;
    }



    //loaders
    public void loadProducts(){
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

            Product p = new Product(id, name, price, stock);
            productsById.insert(new Key(id), p);
            productsByName.insert(new Key(name), p);
        }
        file.close();
        System.out.println("Products loaded successfully.");
    }
    public void loadOrders(){
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
                Product p = searchProduct(Integer.parseInt(pid));
                if (p != null)
                    cart.insert(p);
            }
            Order o = new Order(orderId, cart, date, status);
            Customer c = searchCustomer(customerId);
            if (c != null)
                c.addOrder(o);
            ordersById.insert(new Key(orderId),o);
        }
        file.close();
        System.out.println("Orders loaded successfully.");
    }
    public void loadReviews () {
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

            Customer c = searchCustomer(cId);
            if (c == null) continue;

            Product p = searchProduct(cId);
            if (p == null) continue;

            Review r = new Review(rId, c, rating, comment);
            p.addReview(r);
            c.addReview(r);
        }
        file.close();
        System.out.println("Reviews loaded successfully.");
    }
    public void loadCustomers () {
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

            Customer c = new Customer(id, name, email);
            customersById.insert(new Key(id), c);
            customersByName.insert(new Key(name), c);
        }

        file.close();
        System.out.println("Customers loaded successfully.");
    }

    //moveTo methods (for phase one)
    {
//    private boolean moveToProduct(String name) { //moves the current by Name , Time Complexity : O(n)
//        if (products.empty()) return false;
//
//        products.findFirst();
//        while (!products.last()) {
//            if (products.retrieve().getName().equals(name))
//                return true;
//            products.findNext();
//        }
//        return products.retrieve().getName().equals(name);
//    }
//
//    private boolean moveToProduct(int id) { //moves the current by ID , Time Complexity : O(n)
//        if (products.empty())
//            return false;
//        products.findFirst();
//        while (!products.last()) {
//            if (products.retrieve().getId() == id)
//                return true;
//            products.findNext();
//        }
//        return products.retrieve().getId() == id;
//    }

//    private boolean moveToCustomer(String email) { //moves the current by email, Time Complexity : O(n)
//        if (customers.empty()) return false;
//
//        customers.findFirst();
//        while (!customers.last()) {
//            if (customers.retrieve().getEmail().equals(email))
//                return true;
//            customers.findNext();
//        }
//        return customers.retrieve().getEmail().equals(email);
//    }
//
//    private boolean moveToCustomer(int id) { //moves the current by ID , Time Complexity : O(n)
//        if (customers.empty())
//            return false;
//        customers.findFirst();
//        while (!customers.last()) {
//            if (customers.retrieve().getId() == id)
//                return true;
//            customers.findNext();
//        }
//        return customers.retrieve().getId() == id;
//    }


//    private boolean moveToOrder(int id) { //moves the current by ID , Time Complexity : O(n)
//        if (orders.empty())
//            return false;
//        orders.findFirst();
//        while (!orders.last()) {
//            if (orders.retrieve().getId() == id)
//                return true;
//            orders.findNext();
//        }
//        return orders.retrieve().getId() == id;
//    }
//    public MyLinkedList<Order> getOrders() {
//        return orders;
//    }
    }

    //customer operations
//    o Register new customer.
//    o Place a new order for a specific customer.
//    o View order history.
    public boolean signUp (String name, String email){
        if (searchCustomer(name) != null)
            return false;
        Customer c = new Customer(name, email);
        customersById.insert(new Key(c.getId()), c);
        customersByName.insert(new Key(name), c);
        return true;
    }
    public Customer login (String name){
        return searchCustomer(name);
    }
    public void order ( int cId, Order order){
        Customer c = searchCustomer(cId);
        if (c != null) {
            c.addOrder(order);
            ordersById.insert(new Key(order.getId()), order);
        } else System.out.println("there is no customer with Id:" + cId);
    }
    public void orderHistory ( int cId){
        Customer c = searchCustomer(cId);
        if (c != null) c.viewOrderHistory();
        else System.out.println("there is no customer with Id: " + cId);
    }
    public Customer searchCustomer ( int id){
        return customersById.find(new Key(id));
    }
    public Customer searchCustomer (String name){
        return customersByName.find(new Key(name));
    }

//    Order Operations:
//    o Create/cancel order.(create is in customer)
//    o Update order status.
//    o Search order by ID.
    public void updateOrderStatus ( int oId, String status){
    Order o = searchOrder(oId);
    if(o != null)
        o.setStatus(status);
    else System.out.println("there is no order with id : " + oId);
    }
    public void cancelOrder (int id){
        Order o = searchOrder(id);
        if(o == null) {
            System.out.println("there is no Order with ID: " + id);
            return;
        }
        ordersById.remove(new Key(id));
        System.out.println("Order: [" + id + "]" + " has been removed successfully");
    }
    public Order searchOrder ( int oId) {
        return ordersById.find(new Key(oId));
    }



    //    product Operations:
//      o Add/remove/update products.
//      o Search by ID or name (linear).
//      o Track out-of-stock products.
    public void addProduct (String name,double price, int stock){
        Product p = new Product(name , price , stock);
        productsById.insert(new Key(p.getId()), p);
        productsByName.insert(new Key(name), p);
    }
    public void removeProduct ( int id){
        Product p = searchProduct(id);
        if(p == null) {
            System.out.println("there is no product with ID: " + id);
            return;
        }
        productsById.remove(new Key(id));
        productsByName.remove(new Key(p.getName()));
        System.out.println("Product: " + name + "[" + id + "]" + " has been removed successfully");
    }
    public void updateProduct ( int id, String fName,double fPrice, int fStock){
        Product p = searchProduct(id);
        if(p == null) {
            System.out.println("there is no product with ID: " + id);
            return;
        }
        String oldName = p.getName();
        String newName = (fName == null) ? oldName : fName.trim();
        if (newName.isEmpty()) {
            System.out.println("product name cannot be empty");
            return;
        }
        Product conflict = searchProduct(newName);
        if (conflict != null && conflict.getId() != p.getId()) {
            System.out.println(newName + " already exists as name for another product");
            return;
        }
        productsByName.remove(new Key(oldName));
        p.setProduct(fName , fPrice ,fStock);
        productsByName.insert(new Key(newName) , p);
        System.out.println(p + ".\n has been successfully updated to:");
        System.out.println(p);
    }
    public Product searchProduct ( int id){
        return productsById.find(new Key(id));
    }
    public Product searchProduct (String name){
        return productsByName.find(new Key(name));
    }
    public MyArrayList<Product> outOfStockProducts () {
        final int MAX = 100;
        MyArrayList<Product> outOfStock = new MyArrayList<>(MAX);
        //to be implemented
        return outOfStock;
    }
    public void displayProducts () {
        productsByName.traverse("preorder");
    }

    //• Review Operations:
    //o Add/edit review.
    //o Get an average rating for product.(inside product)
    public void addReview ( int rId, int pId, int cId, int rating, String comment){
        Customer c = searchCustomer(cId);
        if (c == null) {
            System.out.println("there is no customer with Id : " + cId);
            return;
        }
        Product p = searchProduct(pId);
        if (p == null) {
            System.out.println("there is no Product with Id : " + pId);
            return;
        }
        Review r = new Review(rId, c, rating, comment);
        p.addReview(r);
        c.addReview(r);
    }
    public void addReview ( int pId, int cId, int rating, String comment){
        Customer c = searchCustomer(cId);
        if (c == null) {
            System.out.println("there is no customer with Id : " + cId);
            return;
        }
        Product p = searchProduct(pId);
        if (p == null) {
            System.out.println("there is no Product with Id : " + pId);
            return;
        }
        Review r = new Review(c, rating, comment);
        p.addReview(r);
        c.addReview(r);
    }
    public void editReview ( int cId, int pId, int rating, String comment){
        Product p = searchProduct(pId);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        MyLinkedList<Review> reviews = p.getReviews();
        if (reviews.empty()) {
            System.out.println("This product has no reviews.");
            return;
        }
        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            if (r.getCustomer().getId() == cId) {
                r.setRating(rating);
                r.setComment(comment);
                System.out.println("Review updated.");
                return;
            }
            if (reviews.last())
                break;
            reviews.findNext();
        }
        System.out.println("You have not reviewed this product.");
    }

//other Requirements
//• Extract reviews from a specific customer for all products with the most efficient DS possible
//• Suggest "top 3 products" by average rating.
//• All Orders between two dates
//• Given two customers IDs, show a list of common products that have been reviewed with an average rating of more than 4 out of 5.

        public void displayTopRatedProducts () {
//        Product best1 = null, best2 = null, best3 = null;
//        while (true) {
//            if (best1 == null || products.retrieve().averageRating() > best1.averageRating()) {
//                best3 = best2;
//                best2 = best1;
//                best1 = products.retrieve();
//            } else if (best2 == null || products.retrieve().averageRating() > best2.averageRating()) {
//                best3 = best2;
//                best2 = products.retrieve();
//            } else if (best3 == null || products.retrieve().averageRating() > best3.averageRating()) {
//                best3 = products.retrieve();
//            }
//            if (products.last())
//                break;
//            products.findNext();
//        }
//        System.out.println("TOP 3 Products:");
//        if (best1 != null)
//            System.out.printf("1st place : [%3d] %-25s Rating :%4.1f\n", best1.getId(), best1.getName(), best1.averageRating());
//        if (best2 != null)
//            System.out.printf("2nd place : [%3d] %-25s Rating :%4.1f\n", best2.getId(), best2.getName(), best2.averageRating());
//        if (best3 != null)
//            System.out.printf("3rd place : [%3d] %-25s Rating :%4.1f\n", best3.getId(), best3.getName(), best3.averageRating());

    }

        public void displayOrdersBetweenDates (String startDate, String endDate){
//        if (orders.empty()) {
//            System.out.println("No orders available yet");
//            return;
//        }
//        int start = Integer.parseInt(startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10));
//        int end = Integer.parseInt(endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10));
//        boolean found = false;
//        orders.findFirst();
//        while (true) {
//            Order o = orders.retrieve();
//            String d = o.getDate();
//            int od = Integer.parseInt(d.substring(0, 4) + d.substring(5, 7) + d.substring(8, 10));
//            if (od >= start && od <= end) {
//                System.out.println(o);
//                found = true;
//            }
//            if (orders.last())
//                break;
//            orders.findNext();
//        }
//        if (!found) {
//            System.out.println("No orders in this date range.");
//        }
    }
        private boolean hasReviewed (Product p,int cId){
        MyLinkedList<Review> list = p.getReviews();
        if (list.empty()) return false;

        list.findFirst();
        while (true) {
            if (list.retrieve().getCustomer().getId() == cId)
                return true;

            if (list.last()) break;
            list.findNext();
        }
        return false;
    }



    public void showCommonReviewedProducts (int cId1, int cId2){

        if (searchCustomer(cId1) == null) {
            System.out.println("Customer [" + cId1 + "] not found.");
            return;
        }
        if (searchCustomer(cId2) == null) {
            System.out.println("Customer [" + cId2 + "] not found.");
            return;
        }
        boolean found = false;

        if (!found) {
            System.out.println("No common >4 rated products were found.");
        }
    }
    public AVLTree<Product> getProducts() {
        return productsByName;
    }
}