package kz.onetech.one_tech_project.repository;

import kz.onetech.one_tech_project.model.Order;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class OrderRepositoryImpl implements OrderRepository {

    private List<Order> orderList;

    @Override
    public void addOrder(Order order) {
        orderList.add(order);
        System.out.printf("Added order with total: %s%n", order.getTotalAmount());
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderList);
    }
}
