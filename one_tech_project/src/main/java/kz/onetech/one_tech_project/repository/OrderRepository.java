package kz.onetech.one_tech_project.repository;

import kz.onetech.one_tech_project.model.Order;

import java.util.List;

public interface OrderRepository {
    void addOrder(Order order);
    List<Order> getAllOrders();
}

