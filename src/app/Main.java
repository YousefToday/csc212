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
                    System.out.print("Enter your Username: ");
                    String email = scanner.nextLine();
                    Customer user = store.login(email);

                    if (user == null) {
                        System.out.println("You are not registered. Please sign up first.");
                        continue;
                    }

                    while (true) {
                        printBoarder();

                        System.out.println("Welcome" + user);
                        store.displayTopRatedProducts();
                        System.out.println("\n1) View Products");
                        System.out.println("2) Place an Order");
                        System.out.println("3) View Order History");
                        System.out.println("4) Logout");
                        choice = choice(4);


                        switch (choice) {
                            case 1:
                                while(true) {
                                    store.display("productsbyid");

                                    System.out.println("\n1) View reviews for a product");
                                    System.out.println("2) filters");
                                    System.out.println("3) Back");
                                    choice = choice(3);


                                    switch (choice) {
                                        case 1:
                                            System.out.print("Enter product ID: ");
                                            int pid = choice(Integer.MAX_VALUE);
                                            Product p = store.searchProduct(pid);
                                            if (p != null) {
                                                while (true) {
                                                    p.getReviews().display();

                                                    System.out.println("1) Add Review");
                                                    System.out.println("2) Edit Review");
                                                    System.out.println("3) Back");
                                                    choice = choice(3);

                                                    switch (choice) {
                                                        case 1:
                                                            System.out.print("Rating (1-5): ");
                                                            int r = choice(5);
                                                            System.out.print("Comment: ");
                                                            String cmt = scanner.nextLine();
                                                            store.addReview(pid, user.getId(), r, cmt);
                                                            System.out.println("Review added.");
                                                            continue;
                                                        case 2:
                                                            MyLinkedList<Review> revs = p.getReviews();
                                                            if (revs.empty()) {
                                                                System.out.println("No reviews yet.");
                                                                continue;
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
                                                                continue;
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
                                                            continue;
                                                        case 3:
                                                            break;
                                                    }
                                                    break;
                                                }
                                            }
                                            continue;
                                        case 2:
                                            while (true) {
                                                System.out.println("1) sort by id");
                                                System.out.println("2) sort by name");
                                                System.out.println("3) sort by price");
                                                System.out.println("4) select a price interval");
                                                System.out.println("5) back");
                                                choice = choice(5);
                                                switch (choice) {
                                                    case 1:
                                                        store.display("productsbyid");
                                                        continue;
                                                    case 2:
                                                        store.display("productsbyname");
                                                        continue;
                                                    case 3:
                                                        store.display("productsbyprice");
                                                        continue;
                                                    case 4:
                                                        System.out.println("minimum price range :");
                                                        double min = scanner.nextDouble();
                                                        System.out.println("maximum price range :");
                                                        double max = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        store.selectPriceRange(min , max);
                                                        continue;
                                                    case 5:
                                                        break;
                                                }
                                                break;
                                            }
                                            continue;
                                        case 3:
                                            break;

                                    }
                                    break;
                                }
                                continue;
                            case 2:
                                MyArrayList<Product> cart = new MyArrayList<>(100);
                                cart.display();
                                while (true) {
                                    System.out.println("Add product by:\n1) Name\n2) ID\n3) place order\n4)back");
                                    choice = choice(3);
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
                                                break;
                                            }
                                            String date = java.time.LocalDate.now().toString();
                                            Order o = new Order(user.getId(), cart, date , "pending");
                                            store.order(user.getId(), o);
                                            System.out.println("Order placed.");
                                            cart.wipe();
                                            continue;
                                        case 4:
                                            break;
                                    }
                                    break;
                                }
                                continue;
                            case 3:
                                store.orderHistory(user.getId());
                                continue;
                            case 4:
                                break;
                        }
                        break;
                    }
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    String n = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String e = scanner.nextLine();
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
            System.out.println("\n1) View");
            System.out.println("2) Products Operations");
            System.out.println("3) Orders Operations");
            System.out.println("4) Logout");
            int choice = choice(4);

            switch (choice) {
                case 1:
                    while (true) {
                        printBoarder();
                        System.out.println("\n1) View all products");
                        System.out.println("2) View all customers");
                        System.out.println("3) View All Orders");
                        System.out.println("4) Back");
                        choice = choice(4);
                        switch (choice) {
                            case 1:
                                while (true) {
                                    store.display("productsbyid");
                                    System.out.println("\n1) search for a product");
                                    System.out.println("1) View reviews for a product");
                                    System.out.println("2) filters");
                                    System.out.println("3) Back");
                                    choice = choice(3);
                                    switch (choice) {
                                        case 1:
                                            while(true){
                                                System.out.println("1)search by id\n2)search by name\n3)back");
                                                choice = choice(3);
                                                switch (choice){
                                                    case 1: {
                                                        System.out.println("Enter the product ID");
                                                        int pId = choice(Integer.MAX_VALUE);
                                                        Product p = store.searchProduct(pId);
                                                        if(p == null){
                                                            System.out.println("there is no product with ID: " + pId);
                                                        }
                                                        System.out.println(p);
                                                        continue;
                                                    }
                                                    case 2:
                                                        System.out.println("Enter the product name");
                                                        String pName = scanner.nextLine();
                                                        Product p = store.searchProduct(pName);
                                                        if(p == null){
                                                            System.out.println("there is no product with name: " + pName);
                                                        }
                                                        System.out.println(p);
                                                        continue;
                                                    case 3:
                                                        break;
                                                }
                                                break;
                                            }
                                            continue;
                                        case 2:
                                            System.out.print("Enter product ID: ");
                                            int pid = choice(Integer.MAX_VALUE);
                                            Product p = store.searchProduct(pid);
                                            if (p != null)
                                                p.getReviews().display();
                                            else System.out.println("invalid product id");
                                            continue;
                                        case 3:
                                            while (true) {
                                                System.out.println("\n1) sort by id");
                                                System.out.println("2) sort by name");
                                                System.out.println("3) sort by price");
                                                System.out.println("4) sort by stock");
                                                System.out.println("5) select a price interval");
                                                System.out.println("6) back");
                                                choice = choice(6);
                                                switch (choice) {
                                                    case 1:
                                                        store.display("productsbyid");
                                                        continue;
                                                    case 2:
                                                        store.display("productsbyname");
                                                        continue;
                                                    case 3:
                                                        store.display("productsbyprice");
                                                        continue;
                                                    case 4:
                                                        store.display("productsbystock");
                                                        continue;
                                                    case 5:
                                                        System.out.println("minimum price range :");
                                                        double min = scanner.nextDouble();
                                                        System.out.println("maximum price range :");
                                                        double max = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        store.selectPriceRange(min, max);
                                                        continue;
                                                    case 6:
                                                        break;
                                                }
                                                break;
                                            }
                                            continue;
                                        case 4:
                                            break;
                                    }
                                    break;
                                }
                                continue;
                            case 2:
                                while (true) {
                                    store.display("customersbyid");

                                    System.out.println("\n1) search for a customer");
                                    System.out.println("2) filters");
                                    System.out.println("3) Back");
                                    choice = choice(3);
                                    switch (choice) {
                                        case 1:
                                            while(true){
                                                System.out.println("1)search by id\n2)search by name\n3)back");
                                                choice = choice(3);
                                                switch (choice){
                                                    case 1: {
                                                        System.out.println("Enter the customer ID");
                                                        int cId = choice(Integer.MAX_VALUE);
                                                        Customer c = store.searchCustomer(cId);
                                                        if(c == null){
                                                            System.out.println("there is no customer with ID: " + cId);
                                                        }
                                                        System.out.println(c);
                                                        continue;
                                                    }
                                                    case 2:
                                                        System.out.println("Enter the customer username");
                                                        String cName = scanner.nextLine();
                                                        Customer c = store.searchCustomer(cName);
                                                        if(c == null){
                                                            System.out.println("there is no customer with name: " + cName);
                                                        }
                                                        System.out.println(c);
                                                        continue;
                                                    case 3:
                                                        break;
                                                }
                                               break;
                                            }
                                            continue;
                                        case 2:
                                            while (true) {
                                                System.out.println("1) sort by id");
                                                System.out.println("2) sort by name");
                                                System.out.println("3) extract a customer reviews");
                                                System.out.println("4) extract a common reviewed products with an average rating more that 4");
                                                System.out.println("6) back");
                                                choice = choice(6);
                                                switch (choice) {
                                                    case 1:
                                                        store.display("customersbyid");
                                                        continue;
                                                    case 2:
                                                        store.display("customersbyname");
                                                        continue;
                                                    case 3: {
                                                        System.out.println("Enter the customer ID:");
                                                        int cId = choice(Integer.MAX_VALUE);
                                                        Customer c = store.searchCustomer(cId);
                                                        if (c == null) {
                                                            System.out.println("there is no customer with ID: " + cId);
                                                            continue;
                                                        }
                                                        c.getReviews().display();
                                                        continue;
                                                    }
                                                    case 4:
                                                        System.out.println("Enter the first customer ID: ");
                                                        int cId1 = choice(Integer.MAX_VALUE);
                                                        System.out.println("Enter the first customer ID: ");
                                                        int cId2 = choice(Integer.MAX_VALUE);
                                                        store.showCommonReviewedProducts(cId1 , cId2);
                                                        continue;
                                                    case 5:
                                                        System.out.println("minimum price range :");
                                                        double min = scanner.nextDouble();
                                                        System.out.println("maximum price range :");
                                                        double max = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        store.selectPriceRange(min, max);
                                                        continue;
                                                    case 6:
                                                        break;
                                                }
                                                break;
                                            }
                                            continue;
                                        case 3:
                                            break;
                                    }
                                    break;
                                }
                                continue;
                            case 3:
                                continue;
                            case 4:
                                break;
                        }
                        break;
                    }
                    continue;
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
                    continue;
                case 3:
                    continue;
                case 4:
                    break;

            }
            break;
        }
    }
        private static void printBoarder () {
                System.out.println("===========================================");
            }
}