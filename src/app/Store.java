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
    private AVLTree<Product> productsByPrice;
    private AVLTree<Product> productsByStock;
    private AVLTree<Customer> customersById;
    private AVLTree<Customer> customersByName;
    private AVLTree<Order> ordersById;
    private AVLTree<Order> ordersByDate;

    public Store(String name) {
        this.name = name;

        //this.products = new MyArrayList<>(MAX_PRODUCTS);
        //this.orders = new MyLinkedList<>();
        //this.customers = new MyLinkedList<>();

        this.productsById = new AVLTree<>();
        this.productsByName = new AVLTree<>();
        this.productsByPrice = new AVLTree<>();
        this.productsByStock = new AVLTree<>();
        this.customersById = new AVLTree<>();
        this.customersByName = new AVLTree<>();
        this.ordersById = new AVLTree<>();
        this.ordersByDate = new AVLTree<>();
    }

    public String getName() {
        return name;
    }


    //loaders
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

            Product p = new Product(id, name, price, stock);
            productsById.insert(new Key(id), p);
            productsByName.insert(new Key(name), p);
            productsByPrice.insert(new Key(priceKey(p.getPrice(), p.getId())), p);
            productsByStock.insert(new Key(stockKey(p.getStock(), p.getId())), p);
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
                Product p = searchProduct(Integer.parseInt(pid));
                if (p != null)
                    cart.insert(p);
            }
            Order o = new Order(customerId, cart, date, status);
            Customer c = searchCustomer(customerId);
            if (c != null)
                c.addOrder(o);
            ordersById.insert(new Key(orderId), o);
            ordersByDate.insert(new Key(dateKey(o.getDate(), o.getId())), o);
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

            Customer c = searchCustomer(cId);
            if (c == null) continue;

            Product p = searchProduct(pId);
            if (p == null) continue;

            Review r = new Review(rId, c, rating, comment);
            p.addReview(r);
            c.addReview(r);
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
    public boolean signUp(String name, String email) {
        if (searchCustomer(name) != null)
            return false;
        Customer c = new Customer(name, email);
        customersById.insert(new Key(c.getId()), c);
        customersByName.insert(new Key(name), c);
        return true;
    }

    public Customer login(String name) {
        return searchCustomer(name);
    }

    public void order(int cId, Order order) {
        Customer c = searchCustomer(cId);
        if (c != null) {
            c.addOrder(order);
            ordersById.insert(new Key(order.getId()), order);
            ordersByDate.insert(new Key(dateKey(order.getDate(), order.getId())), order);
        } else System.out.println("there is no customer with Id:" + cId);
    }

    public void orderHistory(int cId) {
        Customer c = searchCustomer(cId);
        if (c != null) c.viewOrderHistory();
        else System.out.println("there is no customer with Id: " + cId);
    }

    public Customer searchCustomer(int id) {
        return customersById.find(new Key(id));
    }

    public Customer searchCustomer(String name) {
        return customersByName.find(new Key(name));
    }

    //    Order Operations:
//    o Create/cancel order.(create is in customer)
//    o Update order status.
//    o Search order by ID.
    public void updateOrderStatus(int oId, String status) {
        Order o = searchOrder(oId);
        if (o != null)
            o.setStatus(status);
        else System.out.println("there is no order with id : " + oId);
    }

    public void cancelOrder(int id) {
        Order o = searchOrder(id);
        if (o == null) {
            System.out.println("there is no Order with ID: " + id);
            return;
        }
        ordersById.remove(new Key(id));
        ordersByDate.remove(new Key(dateKey(o.getDate(), o.getId())));
        System.out.println("Order: [" + id + "]" + " has been removed successfully");
    }

    public Order searchOrder(int oId) {
        return ordersById.find(new Key(oId));
    }
    public void listPendingOrders() {
        MyLinkedList<Order> list = new MyLinkedList<>();
        ordersById.Select(new Key(Integer.MIN_VALUE), new Key(Integer.MAX_VALUE), list);
        if (list.empty()) {
            System.out.println("No orders yet.");
            return;
        }
        boolean any = false;
        list.findFirst();
        while (true) {
            Order o = list.retrieve();
            if (o != null && "Pending".equalsIgnoreCase(o.getStatus())) {
                System.out.println(o);
                any = true;
            }
            if (list.last()) break;
            list.findNext();
        }
        if (!any) System.out.println("No pending orders.");
    }

    public void serveOrder(int orderId) {
        Order o = searchOrder(orderId);
        if (o == null) {
            System.out.println("No order with ID: " + orderId);
            return;
        }
        if (!"Pending".equalsIgnoreCase(o.getStatus())) {
            System.out.println("Only Pending orders can be served.");
            return;
        }

        MyArrayList<Product> prods = o.getProducts();
        if (prods == null || prods.empty()) {
            o.setStatus("Shipped");
            System.out.println("Order [" + orderId + "] served (no items).");
            return;
        }
        boolean ok = true;
        StringBuilder missing = new StringBuilder();
        prods.findFirst();
        while (true) {
            Product p = prods.retrieve();
            if (p != null && p.getStock() <= 0) {
                ok = false;
                if (missing.length() > 0) missing.append(", ");
                missing.append(p.getName()).append(" [").append(p.getId()).append("]");
            }
            if (prods.last()) break;
            prods.findNext();
        }
        if (!ok) {
            System.out.println("Cannot serve order [" + orderId + "]. Out of stock: " + missing);
            return;
        }
        prods.findFirst();
        while (true) {
            Product p = prods.retrieve();
            if (p != null) sellProduct(p.getId());
            if (prods.last()) break;
            prods.findNext();
        }

        o.setStatus("Shipped");
        System.out.println("Order [" + orderId + "] served successfully (status: Shipped).");
    }


    //    product Operations:
//      o Add/remove/update products.
//      o Search by ID or name (linear).
//      o Track out-of-stock products.
    public void addProduct(String name, double price, int stock) {
        Product p = new Product(name, price, stock);
        productsById.insert(new Key(p.getId()), p);
        productsByName.insert(new Key(name), p);
        productsByPrice.insert(new Key(priceKey(p.getPrice(), p.getId())), p);
        productsByStock.insert(new Key(stockKey(p.getStock(), p.getId())), p);


    }

    public void removeProduct(int id) {
        Product p = searchProduct(id);
        if (p == null) {
            System.out.println("there is no product with ID: " + id);
            return;
        }
        productsById.remove(new Key(id));
        productsByName.remove(new Key(p.getName()));
        productsByPrice.remove(new Key(priceKey(p.getPrice(), p.getId())));
        productsByStock.remove(new Key(stockKey(p.getStock(), p.getId())));

        System.out.println("Product: " + p.getName() + "[" + id + "]" + " has been removed successfully");
    }

    public void updateProduct(int id, String fName, double fPrice, int fStock) {
        Product p = searchProduct(id);
        if (p == null) {
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
        productsByPrice.remove(new Key(priceKey(p.getPrice(), p.getId())));
        productsByStock.remove(new Key(stockKey(p.getStock(), p.getId())));

        p.setProduct(newName, fPrice, fStock);
        productsByName.insert(new Key(newName), p);
        productsByPrice.insert(new Key(priceKey(p.getPrice(), p.getId())), p);
        productsByStock.insert(new Key(stockKey(p.getStock(), p.getId())), p);

        System.out.println(p + ".\n has been successfully updated to:");
        System.out.println(p);
    }
    public void sellProduct(int productId) {
        Product p = searchProduct(productId);
        if (p == null || p.getStock() <= 0) return;
        productsByStock.remove(new Key(stockKey(p.getStock(), p.getId())));
        p.setStock(p.getStock() - 1);
        productsByStock.insert(new Key(stockKey(p.getStock(), p.getId())), p);
    }

    public Product searchProduct(int id) {
        return productsById.find(new Key(id));
    }

    public Product searchProduct(String name) {
        return productsByName.find(new Key(name));
    }


    //• Review Operations:
    //o Add/edit review.
    //o Get an average rating for product.(inside product)
    public void addReview(int rId, int pId, int cId, int rating, String comment) {
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

    public void addReview(int pId, int cId, int rating, String comment) {
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

    public void editReview(int cId, int pId, int rating, String comment) {
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

    public void displayTopRatedProducts() {
        MyLinkedList<Product> list = new MyLinkedList<>();
        // collect all products via ID index (min..max)
        productsById.Select(new Key(Integer.MIN_VALUE), new Key(Integer.MAX_VALUE), list);

        if (list.empty()) { System.out.println("No products yet."); return; }

        Product best1 = null, best2 = null, best3 = null;

        list.findFirst();
        while (true) {
            Product p = list.retrieve();
            double r = p.averageRating();

            if (best1 == null || r > best1.averageRating() ||
               (best1 != null && r == best1.averageRating() && p.getId() < best1.getId())) {
                best3 = best2; best2 = best1; best1 = p;
            } else if (best2 == null || r > best2.averageRating() ||
                      (best2 != null && r == best2.averageRating() && p.getId() < best2.getId())) {
                best3 = best2; best2 = p;
            } else if (best3 == null || r > best3.averageRating() ||
                      (best3 != null && r == best3.averageRating() && p.getId() < best3.getId())) {
                best3 = p;
            }
            if (list.last()) break;
            list.findNext();
        }

        System.out.println("TOP 3 Products:");
        if (best1 != null) System.out.printf("1st place : [%3d] %-25s Rating : %4.1f%n", best1.getId(), best1.getName(), best1.averageRating());
        if (best2 != null) System.out.printf("2nd place : [%3d] %-25s Rating : %4.1f%n", best2.getId(), best2.getName(), best2.averageRating());
        if (best3 != null) System.out.printf("3rd place : [%3d] %-25s Rating : %4.1f%n", best3.getId(), best3.getName(), best3.averageRating());
    }

//    public void displayOrdersBetweenDates(String startDate, String endDate) { //phase 1
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
//    }

    public void display(String tree) {

        switch (tree.toLowerCase()) {
            case "productsbyid" -> productsById.traverse("inorder");
            case "productsbyname" -> productsByName.traverse("inorder");
            case "productsbyprice" -> productsByPrice.traverse("inorder");
            case "productsbystock" -> productsByStock.traverse("inorder");
            case "customersbyid" -> customersById.traverse("inorder");
            case "customersbyname" -> customersByName.traverse("inorder");
            case "ordersbyid" -> ordersById.traverse("inorder");
            case "ordersbydate" -> ordersByDate.traverse("inorder");
        }
    }

    public void selectPriceRange(double low, double high) {
        MyLinkedList<Product> list = new MyLinkedList<>();
        productsByPrice.Select(new Key(priceLoStr(low)), new Key(priceHiStr(high)), list);
        list.display();
    }

    public void selectDateRange(String low, String high) {
        if (dateLoStr(low).compareTo(dateHiStr(high)) > 0) {
            String t = low;
            low = high;
            high = t;
        }
        MyLinkedList<Order> list = new MyLinkedList<>();
        ordersByDate.Select(new Key(dateLoStr(low)), new Key(dateHiStr(high)), list);
        list.display();
    }

    public void selectOutOfStockProducts() {
        MyLinkedList<Product> list = new MyLinkedList<>();
        productsByStock.Select(new Key(stockLoStr(0)), new Key(stockHiStr(0)), list);
        list.display();
    }

    public void showCommonReviewedProducts(int cId1, int cId2) {
        Customer c1 = searchCustomer(cId1);
        if (c1 == null) { System.out.println("Customer [" + cId1 + "] not found."); return; }
        Customer c2 = searchCustomer(cId2);
        if (c2 == null) { System.out.println("Customer [" + cId2 + "] not found."); return; }

        MyLinkedList<Product> all = new MyLinkedList<>();
        productsById.Select(new Key(Integer.MIN_VALUE), new Key(Integer.MAX_VALUE), all);

        if (all.empty()) { System.out.println("No products."); return; }

        boolean found = false;

        all.findFirst();
        while (true) {
            Product p = all.retrieve();
            if (p != null && p.averageRating() > 4.0) {
                boolean has1 = false, has2 = false;
                MyLinkedList<Review> revs = p.getReviews();
                if (!revs.empty()) {
                    revs.findFirst();
                    while (true) {
                        Review r = revs.retrieve();
                        int cid = r.getCustomer().getId();
                        if (cid == cId1) has1 = true;
                        else if (cid == cId2) has2 = true;
                        if (has1 && has2) break;
                        if (revs.last()) break;
                        revs.findNext();
                    }
                }
                if (has1 && has2) {
                    System.out.println(p);
                    found = true;
                }
            }
            if (all.last()) break;
            all.findNext();
        }
        if (!found) System.out.println("No common >4 rated products were found.");
    }


    //composite key issue
    private static String priceKey(double price, int pid) {
        int pc = (int) Math.round(price * 100);
        String a = String.format("%09d", pc);   // price cents, 9 digits
        String b = String.format("%010d", pid); // id, 10 digits
        return a + ":" + b;
    }

    private static String priceLoStr(double p) {
        int pc = (int) Math.round(p * 100);
        return String.format("%09d:0000000000", pc);
    }

    private static String priceHiStr(double p) {
        int pc = (int) Math.round(p * 100);
        return String.format("%09d:9999999999", pc);
    }

    private static String normalizeDate(String iso) {
        //"YYYY-MM-DD"
        return iso.substring(0, 4) + iso.substring(5, 7) + iso.substring(8, 10);
    }

    // Exact composite key for an order (unique)
    private static String dateKey(String dateIso, int orderId) {
        String ymd = normalizeDate(dateIso);            // 8 digits
        String id = String.format("%010d", orderId);   // 10 digits
        return ymd + ":" + id;                          // "YYYYMMDD:oooooooooo"
    }

    // Inclusive lower/upper bounds for a date (span all order IDs that day)
    private static String dateLoStr(String dateIso) {
        String ymd = normalizeDate(dateIso);
        return ymd + ":0000000000";
    }

    private static String dateHiStr(String dateIso) {
        String ymd = normalizeDate(dateIso);
        return ymd + ":9999999999";
    }

    private static String stockKey(int stock, int pid) {
        // non-negative stock assumed
        return String.format("%010d:%010d", stock, pid);
    }

    private static String stockLoStr(int stock) {
        return String.format("%010d:0000000000", stock);
    }

    private static String stockHiStr(int stock) {
        return String.format("%010d:9999999999", stock);
    }
}