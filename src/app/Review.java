package app;

public class Review {
    private static int idGenetator = 500;
    private int id;
    private int rating;
    private String comment;
    private Customer customer;

    public Review(Customer customer , int rating , String comment){
        id = ++idGenetator;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }
    public Review(int id , Customer customer, int rating , String comment){
        this.id = id;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getRating(){
        return rating;
    }
    public String getComment() {
        return comment;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String stars = "☆☆☆☆☆";
        for(int i = 0 ; i < rating ;i++)
            stars = stars.replaceFirst("☆", "★");


        return "--------------------------------\n"
                + '[' + id + ']' + " " + customer.getName()+ " " +stars + "\n\n"
                +comment;
    }
}
