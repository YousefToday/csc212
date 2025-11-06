package app;

import dataStructures.list.MyLinkedList;

public class Customer {
    private static int idGenerator = 230;
    private int id;
    private String name, email;
    private MyLinkedList<Order> orders;
    private MyLinkedList<Review> reviews;

    public Customer(String name, String email) {
        id = ++idGenerator;
        this.email = email;
        this.name = name;
        orders = new MyLinkedList<>();
        reviews = new MyLinkedList<>();
    }
    public Customer(int id , String name, String email) {
        this.id = id;
        this.email = email;
        this.name = name;
        orders = new MyLinkedList<>();
        reviews = new MyLinkedList<>();
    }
    public void addOrder(Order order){
        orders.insert(order);
    }

    public void viewOrderHistory(){
        if(orders.empty())
            return;
        orders.findFirst();
        while(!orders.last()){
            System.out.println(orders.retrieve());
            orders.findNext();
        }
        System.out.println(orders.retrieve());
    }

    public void addReview(Review r){
        reviews.insert(r);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyLinkedList<Review> getReviews() {
        return reviews;
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

    public MyLinkedList<Order> getOrders() {
        return orders;
    }

    public void setOrders(MyLinkedList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "\t" + id + "\t" + name + "\t" + email;
    }
}