package app;

import dataStructures.list.MyArrayList;
import dataStructures.list.MyLinkedList;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final String ROLE_USER = "user";
    private static final String ROLE_ADMIN = "admin";

    static Store store = new Store("TechMart");
    static Scanner scanner = new Scanner(System.in);

    public static boolean isValidInput(int choice, int optionsNo) {
        return choice >= 0 && choice <= optionsNo;
    }

    public static int choice(int optionsNo) {
        String input = null;
        int choice = -1;
        while (true) {
            try {
                input = scanner.next();
                choice = Integer.parseInt(input);
            } catch (NoSuchElementException e) {
                System.out.println("no input , please try again");
                continue;
            } catch (NumberFormatException f) {
                System.out.println("invalid input , please try again");
                continue;
            }
            if (!isValidInput(choice, optionsNo)) {
                System.out.println("your choice out of range , please try again");
                continue;
            }
            scanner.nextLine();
            return choice;
        }
    }

    public static void main(String[] args) {
        store.loadProducts();
        store.loadCustomers();
        store.loadReviews();
        store.loadOrders();

        while (true) {
            System.out.println("\t\t\t\tWELCOME TO " + store.getName());
            System.out.println("\t\t\tplaese inter your choice:\n1-I am a user\n2-I am an administrator\n3)Exit");
            int choice = choice(3);
            if (choice == 1) userMenu();
            else if (choice == 2) adminMenu();
            else break;
        }
    }

    private static void userMenu() {
        printBoarder();

        while (true) {
            System.out.println("1) Login\n2) Sign Up\n3) Exit");
            int choice = choice(3);
            if (choice == 1) userLogin();
            else if (choice == 2) userSignup();
            else break;
        }
    }

    public static void userLogin() {
        System.out.print("Enter your Username: ");
        String username = scanner.nextLine();
        Customer user = store.login(username);
        if (user == null) {
            System.out.println("You are not registered. Please sign up first.");
            return;
        }
        userHome(user);
    }

    public static void userSignup() {
        System.out.print("Enter name: ");
        String n = scanner.nextLine();
        System.out.print("Enter email: ");
        String e = scanner.nextLine();
        if (store.signUp(n, e))
            System.out.println("Account created.");
        else
            System.out.println("username already exists.");
    }

    public static void userHome(Customer user) {
        while (true) {
            printBoarder();
            System.out.println("Welcome" + user);
            store.displayTopRatedProducts();
            System.out.println("\n1) View Products");
            System.out.println("2) Place an Order");
            System.out.println("3) View Order History");
            System.out.println("4) Logout");
            int choice = choice(4);
            if (choice == 1) viewProducts(ROLE_USER, user);
            else if (choice == 2) placeOrder(user);
            else if (choice == 3) store.orderHistory(user.getId());
            else break;
        }
    }
    private static double totalPrice(MyArrayList<Product> cart){
        double total = 0;
        cart.findFirst();
        while(true){
            total += cart.retrieve().getPrice();
            if(cart.last())
                break;
            cart.findNext();
        }
        return total;
    }
    public static void placeOrder(Customer user) {
        MyArrayList<Product> cart = new MyArrayList<>(100);
        while (true) {
            if(!cart.empty()) {
                cart.display();
                System.out.println("total price : " + totalPrice(cart));
            }
            System.out.println("Add product by:\n1) Name\n2) ID\n3) place order\n4)back");
            int choice = choice(4);
            Product p;
            switch (choice) {
                case 1:
                    System.out.print("Enter the product name: ");
                    String name = scanner.nextLine();
                    p = store.searchProduct(name);
                    if (p != null) {
                        cart.insert(p);
                        System.out.println(p.getName() + " added.");
                    } else
                        System.out.println("Product not found.");
                    continue;
                case 2:
                    System.out.print("Enter the product id: ");
                    int id = choice(Integer.MAX_VALUE);
                    p = store.searchProduct(id);
                    if (p != null) {
                        cart.insert(p);
                        System.out.println(p.getName() + " added.");
                    } else
                        System.out.println("Product not found.");
                    continue;
                case 3:
                    if (cart.empty()) {
                        System.out.println("your cart is empty , no orders to place");
                    } else {
                        String date = java.time.LocalDate.now().toString();
                        Order o = new Order(user.getId(), cart, date, "pending");
                        store.order(user.getId(), o);
                        System.out.println("Order placed.");
                        cart.wipe();
                    }
                    continue;
                case 4:
                    break;
            }
            break;
        }
    }

    private static void adminMenu() {
        while (true) {
            printBoarder();
            System.out.println("1) View");
            System.out.println("2) Products Operations");
            System.out.println("3) Orders Operations");
            System.out.println("4) Logout");
            int choice = choice(4);
            if (choice == 1) adminView();
            else if (choice == 2) adminProductsOps();
            else if (choice == 3) adminOrdersOps();
            else break;
        }
    }

    private static void adminView() {
        while (true) {
            printBoarder();
            System.out.println("\n1) View all products");
            System.out.println("2) View all customers");
            System.out.println("3) View All Orders");
            System.out.println("4) Back");
            int choice = choice(4);
            if (choice == 1) viewProducts(ROLE_ADMIN, null);
            else if (choice == 2) viewCustomers();
            else if (choice == 3) store.display("ordersbyid");
            else break;
        }
    }

    private static void adminProductsOps() {
        while (true) {
            printBoarder();
            System.out.println("1) Add Product");
            System.out.println("2) Remove Product");
            System.out.println("3) Update Product");
            System.out.println("4) back");
            int choice = choice(4);
            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Stock: ");
                    int stock = choice(Integer.MAX_VALUE);
                    store.addProduct(name, price, stock);
                    System.out.println("Product Added.");
                    continue;
                case 2:
                    System.out.print("Enter Product ID to remove: ");
                    int id = choice(Integer.MAX_VALUE);
                    store.removeProduct(id);
                    continue;
                case 3:
                    System.out.print("Product ID: ");
                    int pid = choice(Integer.MAX_VALUE);
                    System.out.print("New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("New Price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("New Stock: ");
                    int newStock = choice(Integer.MAX_VALUE);
                    store.updateProduct(pid, newName, newPrice, newStock);
                    continue;
                case 4:
                    break;
            }
            break;
        }
    }

    private static void adminOrdersOps() {
        while (true) {
            printBoarder();
            System.out.println("1) Search order by ID");
            System.out.println("2) Update order status");
            System.out.println("3) Cancel order");
            System.out.println("4) View orders between two dates (YYYY-MM-DD)");
            System.out.println("5) Serve pending order");
            System.out.println("6) Back");
            int c = choice(6);
            if (c == 1) {
                System.out.print("Order ID: ");
                int oid = choice(Integer.MAX_VALUE);
                Order o = store.searchOrder(oid);
                if (o == null) System.out.println("No order with ID: " + oid);
                else System.out.println(o);
            } else if (c == 2) {
                System.out.print("Order ID: ");
                int oid = choice(Integer.MAX_VALUE);
                Order o = store.searchOrder(oid);
                if (o == null) {
                    System.out.println("No order with ID: " + oid);
                    continue;
                }
                System.out.println("Current status: " + o.getStatus());
                System.out.println("Choose new status:");
                System.out.println("1) Pending");
                System.out.println("2) Shipped");
                System.out.println("3) Delivered");
                System.out.println("4) Cancelled");
                int sc = choice(4);

                String newStatus;
                if (sc == 1) newStatus = "Pending";
                else if (sc == 2) newStatus = "Shipped";
                else if (sc == 3) newStatus = "Delivered";
                else newStatus = "Cancelled";

                store.updateOrderStatus(oid, newStatus);
                System.out.println("Status updated to: " + newStatus);
            } else if (c == 3) {
                System.out.print("Order ID: ");
                int oid = choice(Integer.MAX_VALUE);
                store.cancelOrder(oid);
            } else if (c == 4) {
                System.out.print("Start date (YYYY-MM-DD): ");
                String lo = scanner.nextLine().trim();
                System.out.print("End date   (YYYY-MM-DD): ");
                String hi = scanner.nextLine().trim();
                store.selectDateRange(lo, hi);
            } else if (c == 5) {
                store.listPendingOrders();
                System.out.print("Enter Order ID to serve: ");
                int oid = choice(Integer.MAX_VALUE);
                store.serveOrder(oid);
            } else break;
        }
    }


    private static void viewProducts(String role, Customer user) {
        while (true) {
            printBoarder();
            store.display("productsbyid");
            System.out.println("\n1) Search for a product");
            System.out.println("2) View reviews for a product");
            System.out.println("3) Filters");
            System.out.println("4) Back");
            int c = choice(4);
            if (c == 1) searchProduct();
            else if (c == 2) viewProductReviews(user);
            else if (c == 3) productFiltersMenu(role);
            else break;
        }
    }

    private static void searchProduct() {
        while (true) {
            System.out.println("1)search by id\n2)search by name\n3)back");
            int choice = choice(3);
            switch (choice) {
                case 1: {
                    System.out.println("Enter the product ID");
                    int pId = choice(Integer.MAX_VALUE);
                    Product p = store.searchProduct(pId);
                    if (p == null) {
                        System.out.println("there is no product with ID: " + pId);
                    } else System.out.println(p);
                    continue;
                }
                case 2:
                    System.out.println("Enter the product name");
                    String pName = scanner.nextLine();
                    Product p = store.searchProduct(pName);
                    if (p == null) {
                        System.out.println("there is no product with name: " + pName);
                    } else System.out.println(p);
                    continue;
                case 3:
                    break;
            }
            break;
        }
    }

    private static void viewProductReviews(Customer user) {
        System.out.print("Enter product ID: ");
        int pid = choice(Integer.MAX_VALUE);
        Product p = store.searchProduct(pid);
        String reviewsSort = "rating";
        if (p != null) {
            while (true) {
                p.displayReviews(reviewsSort);

                System.out.println("1) Add Review");
                System.out.println("2) Edit Review");
                System.out.println("3) sort by rating");
                System.out.println("4) sort by customer ID");
                System.out.println("5) Back");
                int choice = choice(5);

                switch (choice) {
                    case 1:
                        if (user == null) {
                            System.out.println("only users can add a review");
                            continue;
                        }
                        System.out.print("Rating (1-5): ");
                        int r = choice(5);
                        System.out.print("Comment: ");
                        String cmt = scanner.nextLine();
                        store.addReview(pid, user.getId(), r, cmt);
                        System.out.println("Review added.");
                        continue;
                    case 2:
                        if (user == null) {
                            System.out.println("only users can edit a review");
                            continue;
                        }
                        editOwnReview(user, p);
                        continue;
                    case 3:
                        reviewsSort = "rating";
                        continue;
                    case 4:
                        reviewsSort = "cid";
                        continue;
                    case 5:
                        break;
                }
                break;
            }
        }
    }

    private static void editOwnReview(Customer user, Product p){
        MyLinkedList<Review> revs = p.getReviews();
        if (revs.empty()) {
            System.out.println("No reviews yet.");
            return;
        }
        boolean any = false;
        revs.findFirst();
        while (true) {
            Review rv = revs.retrieve();
            if (rv.getCustomer().getId() == user.getId()) {
                any = true;
                System.out.println(rv);
            }
            if(revs.last()) break;
            revs.findNext();
        }
        if (!any) {
            System.out.println("You have no reviews on this product.");
            return;
        }

        System.out.print("Enter your review ID to edit: ");
        int rid = choice(Integer.MAX_VALUE);

        boolean found = false;
        revs.findFirst();
        while (true) {
            Review rv = revs.retrieve();
            if (rv.getId() == rid && rv.getCustomer().getId() == user.getId()) {
                found = true;

                System.out.print("New rating: ");
                int nr = choice(5);
                rv.setRating(nr);
                System.out.print("New comment: ");
                String nc = scanner.nextLine().trim();
                rv.setComment(nc);
                System.out.println("Review [" + rid + "] has been successfully updated.");
                break;
            }
            if (revs.last()) break;
            revs.findNext();
        }
        if (!found)
            System.out.println("That review ID is not yours on this product.");
    }
    private static void productFiltersMenu(String role){
        boolean admin = ROLE_ADMIN.equalsIgnoreCase(role);
        while (true) {
            if (admin) {
                System.out.println("1) sort by id");
                System.out.println("2) sort by name");
                System.out.println("3) sort by price");
                System.out.println("4) sort by stock");
                System.out.println("5) select a price interval");
                System.out.println("6) back");
                int c = choice(6);
                if (c == 1) store.display("productsbyid");
                else if (c == 2) store.display("productsbyname");
                else if (c == 3) store.display("productsbyprice");
                else if (c == 4) store.display("productsbystock");
                else if (c == 5) askPriceRange();
                else break;
            } else {
                System.out.println("1) sort by id");
                System.out.println("2) sort by name");
                System.out.println("3) sort by price");
                System.out.println("4) select a price interval");
                System.out.println("5) back");
                int c = choice(5);
                if (c == 1) store.display("productsbyid");
                else if (c == 2) store.display("productsbyname");
                else if (c == 3) store.display("productsbyprice");
                else if (c == 4) askPriceRange();
                else break;
            }
        }
    }
    private static void askPriceRange(){
        System.out.println("minimum price range :");
        double min = scanner.nextDouble();
        System.out.println("maximum price range :");
        double max = scanner.nextDouble();
        scanner.nextLine();
        store.selectPriceRange(min , max);
    }
    private static void viewCustomers(){
        while (true) {
            printBoarder();
            store.display("customersbyid");

            System.out.println("\n1) search for a customer");
            System.out.println("2) filters");
            System.out.println("3) Back");
            int c = choice(3);

            if (c == 1) searchCustomerMenu();
            else if (c == 2) customerFiltersMenu();
            else break;
        }
    }
    private static void searchCustomerMenu(){
        while(true){
            System.out.println("1)search by id\n2)search by name\n3)back");
            int c = choice(3);
            if (c == 1) {
                System.out.println("Enter the customer ID");
                int cId = choice(Integer.MAX_VALUE);
                Customer cust = store.searchCustomer(cId);
                if(cust == null) System.out.println("there is no customer with ID: " + cId);
                else System.out.println(cust);
            } else if (c == 2) {
                System.out.println("Enter the customer username");
                String cName = scanner.nextLine();
                Customer cust = store.searchCustomer(cName);
                if(cust == null) System.out.println("there is no customer with name: " + cName);
                else System.out.println(cust);
            } else break;
        }
    }
    private static void customerFiltersMenu(){
        while (true) {
            System.out.println("1) sort by id");
            System.out.println("2) sort by name");
            System.out.println("3) extract a customer reviews");
            System.out.println("4) extract a common reviewed products with an average rating more that 4");
            System.out.println("5) back");
            int c = choice(5);

            if (c == 1) store.display("customersbyid");
            else if (c == 2) store.display("customersbyname");
            else if (c == 3) {
                System.out.println("Enter the customer ID:");
                int id = choice(Integer.MAX_VALUE);
                Customer cust = store.searchCustomer(id);
                if (cust == null) System.out.println("there is no customer with ID: " + id);
                else cust.getReviews().display();
            } else if (c == 4) {
                System.out.println("Enter the first customer ID: ");
                int cId1 = choice(Integer.MAX_VALUE);
                System.out.println("Enter the second customer ID: ");
                int cId2 = choice(Integer.MAX_VALUE);
                store.showCommonReviewedProducts(cId1 , cId2);
            } else break;
        }
    }
    private static void printBoarder () {
        System.out.println("===========================================");
    }
}