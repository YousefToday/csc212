package app;

import dataStructures.list.MyArrayList;
import dataStructures.list.MyLinkedList;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
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
            return choice;
        }
    }

    public static void main(String[] args) {
        store.loadProducts();
        store.loadCustomers();
        store.loadReviews();
        store.loadOrders();
        while (true) {
            System.out.println("\t\t\t\tWELCOME TO " + store.name);
            System.out.println("\t\t\tplaese inter your choice:\n1-I am a user\n2-I am an administrator");
            int choice = choice(2);
            switch (choice) {
                case 1:
                    userMenu();
                    continue;
                case 2:
                    adminMenu();
                    continue;
            }
            break;
        }
    }


    private static void userMenu() {
        printBoarder();
        while (true) {

            System.out.println("1) Login\n2) Sign Up\n3) Exit");
            int choice = choice(3);

            switch (choice) {
                case 1:
                    System.out.print("Enter your email: ");
                    String email = scanner.next();
                    Customer user = store.login(email);

                    if (user == null) {
                        System.out.println("You are not registered. Please sign up first.");
                        continue;
                    }

                    // Logged-in Menu
                    while (true) {
                        printBoarder();

                        System.out.println("Welcome " + user);
                        store.displayTopRatedProducts();
                        System.out.println("\n\n1) View Products");
                        System.out.println("2) Place Order");
                        System.out.println("3) View Order History");
                        System.out.println("4) Add Review");
                        System.out.println("5) Edit Review");
                        System.out.println("6) Logout");
                        int op = choice(6);

                        switch (op) {
                            case 1:
                                store.displayProducts();

                                System.out.println("\n1) View reviews for a product");
                                System.out.println("2) Back");
                                int ch = choice(2);

                                if(ch == 1) {
                                    System.out.print("Enter product ID: ");
                                    int pid = choice(Integer.MAX_VALUE);
                                    if(store.searchProduct(pid) != null)
                                        store.searchProduct(pid).getReviews().display();
                                    else System.out.println("there is no customer with id: " + pid);
                                }
                                continue;

                            case 2:
                                MyArrayList<Product> cart = new MyArrayList<>(100);
                                while (true) {
                                    System.out.println("Add product by:\n1) Name\n2) ID\n3) Finish");
                                    int oChoice = choice(3);
                                    Product p;
                                    switch (oChoice) {
                                        case 1:
                                            System.out.print("Enter the product name: ");
                                            String name = scanner.next();
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
                                                break;
                                            }
                                            String date = java.time.LocalDate.now().toString();
                                            Order o = new Order(user.getId(), cart, date , "pending");
                                            store.order(user.getId(), o);
                                            System.out.println("Order placed.");
                                            break;
                                    }
                                    break;
                                }
                                continue;
                            case 3:
                                store.orderHistory(user.getId());
                                continue;

                            case 4:
                                System.out.print("Enter product ID: ");
                                int pid = choice(Integer.MAX_VALUE);

                                Product p = store.searchProduct(pid);
                                if(p == null) {
                                    System.out.println("Product not found.");
                                    continue;
                                }

                                System.out.print("Rating (1-5): ");
                                int r = choice(5);
                                scanner.nextLine();
                                System.out.print("Comment: ");
                                String cmt = scanner.nextLine();

                                store.addReview(pid , user.getId(), r , cmt);
                                System.out.println("Review added.");
                                continue;

                            case 5:

                                continue;
                            case 6:
                                break; // logout
                        }
                        break;
                    }
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    String n = scanner.next();
                    System.out.print("Enter email: ");
                    String e = scanner.next();
                    if(store.signUp(n, e))
                        System.out.println("Account created.");
                    else
                        System.out.println("Email already exists.");
                    continue;

                case 3:
                    return;
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            printBoarder();
            System.out.println("1) View");
            System.out.println("2) Products Operations");
            System.out.println("3) Customers Operations");
            System.out.println("4) Orders Operations");
            System.out.println("5) Logout");
            int choice = choice(5);

            switch (choice) {
                case 1:
                    while (true) {
                        printBoarder();
                        System.out.println("1) View all products");
                        System.out.println("2) View Out-of-Stock Products");
                        System.out.println("3) View All Orders");
                        System.out.println("4) Back");
                        int viewChoice = choice(4);
                        switch (viewChoice) {
                            case 1:
                                store.displayProducts();
                                System.out.println("\n1) View reviews for a product");
                                System.out.println("2) Back");
                                int ch = choice(2);

                                if(ch == 1) {
                                    System.out.print("Enter product ID: ");
                                    int pid = choice(Integer.MAX_VALUE);
                                    if(store.searchProduct(pid) != null)
                                        store.searchProduct(pid).getReviews().display();
                                    else System.out.println("there is no product with id: " + pid);
                                }
                                continue;
                            case 2:
                                MyArrayList<Product> out = store.outOfStockProducts();
                                if (out.empty()) System.out.println("No products are out of stock.");
                                else {
                                    out.findFirst();
                                    while (true) {
                                        System.out.println(out.retrieve());
                                        if (out.last()) break;
                                        out.findNext();
                                    }
                                }
                                continue;
                            case 3:
                                store.getOrders().display();
                                continue;
                            case 4:
                                break;
                        }
                        break;
                    }
                    break;

                case 2:
                    while (true) {
                        printBoarder();
                        System.out.println("1) Add Product");
                        System.out.println("2) Remove Product");
                        System.out.println("3) Update Product");
                        System.out.println("4) back");
                        int productChoice = choice(4);
                        switch (productChoice) {
                            case 1:
                                System.out.print("Name: ");
                                String name = scanner.next();
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
                                String newName = scanner.next();
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
                    break;
                case 3:
                    while (true) {
                        printBoarder();
                        System.out.println("1) extract all reviews from one customer by id");
                        System.out.println("2) list of common products that have been reviewed by 2 customers with an average rating of more than 4 out of 5");
                        System.out.println("3) Back");
                        int viewChoice = choice(4);
                        switch (viewChoice) {
                            case 1:
                                System.out.print("Enter Customer ID to extract their reviews: ");
                                int id = choice(Integer.MAX_VALUE);
                                if(store.searchCustomer(id) == null){
                                    System.out.println("there is no customer with id: " + id);

                                }else
                                    store.searchCustomer(id).getReviews().display();
                                continue;
                            case 2:
                                System.out.print("Enter the first Customer ID");
                                int cid1 = choice(Integer.MAX_VALUE);
                                if(store.searchCustomer(cid1) == null) {
                                System.out.println("there is no customer with id: " + cid1);
                                continue;
                            }
                                System.out.print("Enter the second Customer ID");
                                int cid2 = choice(Integer.MAX_VALUE);
                                if(store.searchCustomer(cid2) == null) {
                                    System.out.println("there is no customer with id: " + cid2);
                                    continue;
                                }
                                store.showCommonReviewedProducts(cid1 , cid2);
                                continue;
                            case 3:
                                break;
                        }
                        break;
                    }
                    break;
                case 4:
                    while (true) {
                        printBoarder();
                        System.out.println("1) All Orders between two dates");
                        System.out.println("2) Cancel an order by id");
                        System.out.println("3) Search an order by id");
                        System.out.println("4) Back");
                        int viewChoice = choice(4);
                        switch (viewChoice) {
                            case 1:
                                System.out.println("use YYYY/MM/DD format for your input");
                                System.out.println("Starting date: ");
                                String start = scanner.next();
                                System.out.println("ending date: ");
                                String end = scanner.next();
                                store.displayOrdersBetweenDates(start , end);
                                continue;
                            case 2:
                                System.out.print("Enter an Order ID to Cancel: ");
                                int id = choice(Integer.MAX_VALUE);
                                if(store.searchOrder(id) == null){
                                    System.out.println("there is no order with id: " + id);
                                }
                                store.cancelOrder(id);
                                continue;
                            case 3:
                                System.out.print("Enter an Order ID to extract: ");
                                int oId = choice(Integer.MAX_VALUE);
                                if(store.searchOrder(oId) != null)
                                    System.out.print(store.searchOrder(oId));
                                else
                                    System.out.println("there is Order with id: " + oId);
                                continue;
                            case 4:
                                break;
                        }
                        break;
                    }
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void printBoarder() {
        System.out.println("===========================================");
    }
}

