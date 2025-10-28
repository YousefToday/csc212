package app;

import dataStructures.list.MyLinkedList;

public class Products {
    private static int idGenerator;
    private final int id;
    private String name;
    private int stock;
    private double price;

    private MyLinkedList<Reviews> reviews;


    public Products(String name , double price , int stock){
        id = idGenerator++;
        this.name = name;
        this.price = price;
        this.stock = stock;

        this.reviews = new MyLinkedList<Reviews>();
    }


    //Reviews operations:
        // Add/edit review.
        // Get an average rating for product.

    public void addReview(int rating ,String comment){//Time Complexity : O(1)
        reviews.insert(new Reviews(rating , comment));
    }
    public void editReview(int rating , String comment){//Time Complexity : O(n)
        //to be implemented later
    }
    public double averageRating() { //Time Complexity : O(n)
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
    public void addStock(int amount){
        stock += amount;
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

    public MyLinkedList<Reviews> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return String.format("[%-2s] : %-12s , Price : %-8s SAR , Stock : %-4s " , id , name , price , stock);

    }
}
