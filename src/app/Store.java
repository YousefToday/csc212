package app;

import dataStructures.list.*;


public class Store {
    String name;
    private MyLinkedList<Customers> customers;
    private MyArrayList<Products> products;
    public static final int MAX_PRODUCTS = 99;

    public Store(String name) {
        this.name = name;
        this.products = new MyArrayList<>(MAX_PRODUCTS);
        this.customers = new MyLinkedList<>();
    }

    //customer operations



//    product Operations:
//      o Add/remove/update products.
//      o Search by ID or name (linear).
//      o Track out-of-stock products.
    public void addProduct(String name , double price , int stock){ //Time Complexity : O(1)
        products.insert(new Products(name , price , stock));
    }
    private boolean moveToProduct(String name) { //moves the current by Name , Time Complexity : O(n)
        if (products.empty())
            return false;
        products.findFirst();
        while (true) {
            if (products.retrieve().getName().equals(name)) return true;
            if (products.last()) break;
            products.findNext();
        }
        return false;
    }
    private boolean moveToProduct(int id) { //moves the current by ID , Time Complexity : O(n)
        if (products.empty())
            return false;
        products.findFirst();
        while (!products.last()) {
            if (products.retrieve().getId() == id)
                return true;
            products.findNext();
        }
        return products.retrieve().getId() == id;
    }

    public void removeProduct(int id){ //Time Complexity : O(n)
        if(products.empty()){
            System.out.println("Your store is empty!, nothing to remove yet");
            return;
        }
        boolean found = moveToProduct(id); //O(n) search method
        if(found) {
            String name = products.retrieve().getName();
            products.remove(); //O(n) remove method in MyArrayList
            System.out.println("Product: " + name + "[" + id + "]" + " has been removed successfully");
        }else
            System.out.println("there is no product with ID: " + id);
    }

    public void removeProduct(String name){ //Time Complexity : O(n)
        if(products.empty()){
            System.out.println("Your store is empty!, nothing to remove yet");
            return;
        }
        boolean found = moveToProduct(name); //O(n) search method
        if(found) {
            int id = products.retrieve().getId();
            products.remove(); //O(n) remove method in MyArrayList
            System.out.println("Product: " + name + "[" + id + "]" + " has been removed successfully");
        }else
            System.out.println("there is no product with Name: " + name);
    }


    public void updateProduct(int id , String fName , double fPrice , int fStock) { //Time Complexity : O(n)
        if(products.empty()){
            System.out.println("Your store is empty!, nothing to update yet");
            return;
        }
        boolean found = moveToProduct(id); //O(n) search method
        if(found) {
            Products p = products.retrieve(); //O(1)
            System.out.println(p +".\n has been successfully updated to:");
            p.setProduct(fName , fPrice , fStock);  //O(1)
            System.out.println(p);
        }else
            System.out.println("there is no product with ID: " + id);
    }
    public void updateProduct(String name , String fName , double fPrice , int fStock) { //Time Complexity : O(n)
        if(products.empty()){
            System.out.println("Your store is empty!, nothing to update yet");
            return;
        }
        boolean found = moveToProduct(name); //O(n) search method
        if(found) {
            Products p = products.retrieve(); //O(1)
            System.out.println(p +".\n has been successfully updated to:");
            p.setProduct(fName , fPrice , fStock);  //O(1)
            System.out.println(p);
        }else
            System.out.println("there is no product with Name: " + name);
    }


    public Products searchProduct(int id){//Time Complexity : O(n)
        if(products.empty()){
            return null;
        }
        boolean found = moveToProduct(id); //O(n) search method
        if(found)
            return products.retrieve();
        return null;
    }
    public Products searchProduct(String name){//Time Complexity : O(n)
        if(products.empty()){
            return null;
        }
        boolean found = moveToProduct(name); //O(n) search method
        if(found)
            return products.retrieve();
        return null;
    }

    public MyArrayList<Products> outOfStockProducts(){
        MyArrayList<Products> outOfStock = new MyArrayList<>(MAX_PRODUCTS);
        products.findFirst();
        while(true){
            if(products.retrieve().getStock() == 0)
                outOfStock.insert(products.retrieve());
            if(products.last())
                break;
            products.findNext();
        }
        return outOfStock;
    }

    public void displayProducts(){
        if(products.empty()) {
            System.out.println("the store has no products yet");
            return;
        }
        products.findFirst();
        while(true){
            System.out.println(products.retrieve());
            if(products.last())
                break;
            products.findNext();
        }
    }

}
