package app;

import dataStructures.list.MyArrayList;
import java.time.LocalDate;

public class Orders {
  private MyArrayList<Order> orders = new MyArrayList<Order>(100);

  public Order createOrder(int customerId, String customerReference, MyArrayList<Products> products, double totalPrice,
      LocalDate orderDate, String status) {
    // increment order id
    int orderId = orders.size() + 1;

    // the new order
    Order newOrder = new Order(orderId, customerId, customerReference, products, totalPrice, orderDate, status);

    /*
     * Adds an element to the end of the list. Time complexity: O(1) in average, but
     * O(n) in the worst case (during resizing). Space complexity: O(1) normally,
     * but O(n) in the worst case (temporary resize).
     */
    orders.addLast(newOrder);
    return newOrder;
  }

  // Time Complexity: Average/Worst case: O(n), Space Complexity: O(1)
  public boolean cancelOrder(int orderId) {
    if (orders.empty())
      return false;

    orders.findFirst();
    while (true) {
      Order currentOrder = orders.retrieve();
      if (currentOrder != null && currentOrder.getOrderId() == orderId) {
        currentOrder.setStatus("Canceled");
        return true;
      }
      if (orders.last())
        break;
      orders.findNext();
    }
    return false;
  }
}
