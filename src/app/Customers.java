package app;

import dataStructures.list.MyLinkedList;

public class Customers {
    private static int idGenerator;
    private int customerId;
    private String name, email;
    private MyLinkedList<Order> CustomerOrders;
    // list of order indices, referencing the global list in class Store?

    public Customers(int Id, String name, String email) {
        this.customerId = Id;
        this.name = name;
        this.email = email;
        this.CustomerOrders = new MyLinkedList<>();
    }

    public static int getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator(int idGenerator) {
        Customers.idGenerator = idGenerator;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void registerCustomer() {
        System.out.println("Customer " + name + " registered successfully!");
    }
    
    public void placeOrder(Order order) {
        CustomerOrders.insert(order); 
        System.out.println("Order placed successfully for " + name);
    }

    public void viewOrderHistory() {
        System.out.println("Order History for " + name + ":");
        if (CustomerOrders.empty()) {
            System.out.println("No orders found in" + name + ".");
        } else {
            CustomerOrders.findFirst();
            while (!CustomerOrders.last()) {
                System.out.println(CustomerOrders.retrieve());
                CustomerOrders.findNext();
            }
            System.out.println(CustomerOrders.retrieve());
            CustomerOrders.findFirst();
        }
    }
}
