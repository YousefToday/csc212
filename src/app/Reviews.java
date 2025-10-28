package app;

public class Reviews {
    private int rating;
    private String comment;

    public Reviews(int rating , String comment){
        this.rating = rating;
        this.comment = comment;
    }
    public int getRating(){
        return rating;
    }
    public String getComment() {
        return comment;
    }

    public void editReview(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        String stars = "☆☆☆☆☆";
        for(int i = 0 ; i < rating ;i++)
            stars = stars.replaceFirst("☆", "★");


        return "--------------------------------\n"
                +stars + "\n"
                +comment;
    }
}
