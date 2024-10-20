package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.Order;
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
