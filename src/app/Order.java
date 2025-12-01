package app;

import dataStructures.list.MyArrayList;

public class Order {
    private static int idGenerator = 300;
    private int id, cId;
    private MyArrayList<Product> products;
    private double totalPrice;
    private String status, date;

    public Order(int cId , MyArrayList<Product> products , String date ,String status) {
        this.id = ++idGenerator;
        this.cId = cId;
        this.products = copyList(products);
        this.date = date;
        this.status = status;
        this.totalPrice = getTotalPrice();
    }

    public String getStatus() {
        return status;
    }

    public MyArrayList<Product> getProducts() {
        return products;
    }
    private static MyArrayList<Product> copyList(MyArrayList<Product> src) {
        MyArrayList<Product> dst = new MyArrayList<>(src == null ? 0 : src.size());
        if (src == null || src.empty()) return dst;
        src.findFirst();
        while (true) {
            Product p = src.retrieve();
            if (p != null) dst.addLast(p);
            if (src.last()) break;
            src.findNext();
        }
        return dst;
    }

    private double getTotalPrice(){
        double total = 0;
        products.findFirst();
        while(true){
            Product p = products.retrieve();
            if(p != null)
                total += p.getPrice();
            if(products.last())
                break;
            products.findNext();
        }
        return total;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID : ").append(id)
                .append(" , Date : ").append(date)
                .append(", Status : ").append(status)
                .append("\t{");

        if (!products.empty()) {
            products.findFirst();
            boolean first = true;
            while (true) {
                if (!first) sb.append(", ");
                Product p = products.retrieve();
                sb.append(p != null ? p.getName() : "null");
                if (products.last()) break;
                products.findNext();
                first = false;
            }
        }
        sb.append('}');
        return sb.toString();
    }
}