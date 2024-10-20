package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.Order;

import java.util.List;

public interface OrderRepository {
    void addOrder(Order order);

    List<Order> getAllOrders();
}

