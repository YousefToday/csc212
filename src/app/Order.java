package app;

import java.time.LocalDate;
import dataStructures.list.MyArrayList;

public class Order {
  private int orderId;
  private int customerId;
  private MyArrayList<Products> products;
  private double totalPrice;
  private LocalDate orderDate;
  private String status;
  private String customerReference;

  public Order(int orderId, int customerId, String customerReference, MyArrayList<Products> products,
      double totalPrice, LocalDate orderDate, String status) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.customerReference = customerReference;
    this.products = products;
    this.totalPrice = totalPrice;
    this.orderDate = orderDate;
    this.status = status;
  }

  // Getters
  public int getOrderId() {
    return orderId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public String getCustomerReference() {
    return customerReference;
  }

  public MyArrayList<Products> getProducts() {
    return products;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public LocalDate getOrderDate() {
    return orderDate;
  }

  public String getStatus() {
    return status;
  }

  // Setters
  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public void setCustomerReference(String customerReference) {
    this.customerReference = customerReference;
  }

  public void setProducts(MyArrayList<Products> products) {
    this.products = products;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}