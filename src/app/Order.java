package app;

import dataStructures.list.MyArrayList;

public class Order {
    private static int idGenerator = 400;
    private int id, cId;
    private MyArrayList<Product> products;
    private double totalPrice;
    private String status, date;

    public Order(int cId , MyArrayList products , String date ,String status) {

        this.id = ++idGenerator;
        this.cId = cId;
        this.products = products;
        this.date = date;
        this.status = status;
        totalPrice = getTotalPrice();
    }

    public String getStatus() {
        return status;
    }

    public MyArrayList<Product> getProducts() {
        return products;
    }

    private double getTotalPrice(){
        double total = 0;
        products.findFirst();
        while(!products.last()){
            total += products.retrieve().getPrice();
            products.findNext();
        }
        return total + products.retrieve().getPrice();
    }
    public void setStatus(String status){
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        String str =  "Order ID : " + id + " , Date : " + date + ", Status : " +status + "\t{";

                for(int i = 0 ; i < products.size() ; i++)
                    str += products.retrieve().getName();
                str += "}";
        return str;
    }
}