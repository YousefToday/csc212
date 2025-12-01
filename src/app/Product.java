package app;

import dataStructures.list.MyLinkedList;

public class Product {
    private static int idGenerator = 150;
    private final int id;
    private String name;
    private int stock;
    private double price;

    private MyLinkedList<Review> reviews;


    public Product(String name , double price , int stock){
        id = ++idGenerator;
        this.name = name;
        this.price = price;
        this.stock = stock;

        this.reviews = new MyLinkedList<Review>();
    }
    public Product(int id, String name , double price , int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;

        this.reviews = new MyLinkedList<Review>();
    }


    //Reviews operations:
        // Add/edit review.
        // Get an average rating for product.

    public void addReview(Review r){
        reviews.insert(r);
    }
    public double averageRating() {
        if(reviews.empty())
            return 0;
        reviews.findFirst();
        double sum = 0;
        int count = 1;
        while (!reviews.last()){
            sum += reviews.retrieve().getRating();
            count++;
            reviews.findNext();
        }
        sum += reviews.retrieve().getRating(); //last element
        return sum/count;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void sell(){
        if(stock > 0)
            stock--;
        else
            System.out.println("sorry, "+ name + " is out of stock!");
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public void setProduct(String name, double price , int stock){
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
    }

    public MyLinkedList<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return String.format("[%-3s] : %-25s , Price : %-8s SAR , Stock : %-4s  %-4.1f stars (%d)" , id , name , price , stock , this.averageRating() ,reviews.getSize());

    }
}
