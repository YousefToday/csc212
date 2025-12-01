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
        double sum = 0.0;
        int count = 0;
        while (true){
            sum += reviews.retrieve().getRating();
            count++;
            if(reviews.last())
                break;
            reviews.findNext();
        }
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
    public void displayReviews(String sortBy) {
        if (reviews.empty()) { System.out.println("No reviews."); return; }
        int n = reviews.getSize();
        Review[] a = toArray(reviews, n);

        String by = (sortBy == null) ? "" : sortBy.trim().toLowerCase();
        if (by.equals("cid")) {
            // sort by cid (insertion)
            for (int i = 1; i < n; i++) {
                Review key = a[i]; int k = key.getCustomer().getId(); int j = i - 1;
                while (j >= 0 && a[j].getCustomer().getId() > k) { a[j+1] = a[j]; j--; }
                a[j+1] = key;
            }
        } else if (by.equals("rating")) {
            // sort by rating  / /
            for (int i = 1; i < n; i++) {
                Review key = a[i]; int k = key.getRating(); int j = i - 1;
                while (j >= 0 && a[j].getRating() > k) { a[j+1] = a[j]; j--; }
                a[j+1] = key;
            }
        }

        for (int i = 0; i < n; i++) System.out.println(a[i]);
    }

    private static Review[] toArray(MyLinkedList<Review> list, int n) {
        Review[] a = new Review[n];
        list.findFirst();
        for (int i = 0; i < n; i++) {
            a[i] = list.retrieve();
            if (!list.last()) list.findNext();
        }
        return a;
    }

    @Override
    public String toString() {
        return String.format("[%-3s] : %-25s , Price : %-8s SAR , Stock : %-4s  %-4.1f stars (%d)" , id , name , price , stock , this.averageRating() ,reviews.getSize());

    }
}
