package kz.onetech.one_tech_project.service;

import kz.onetech.one_tech_project.model.Coffee;
import kz.onetech.one_tech_project.model.Order;
import kz.onetech.one_tech_project.model.OrderItem;
import kz.onetech.one_tech_project.repository.CoffeeRepository;
import kz.onetech.one_tech_project.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeShopServiceImpl implements CoffeeShopService {

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;

    public CoffeeShopServiceImpl(CoffeeRepository coffeeRepository, OrderRepository orderRepository) {
        this.coffeeRepository = coffeeRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void placeOrder(List<OrderItem> items) {
        double total = items.stream()
                .mapToDouble(item -> item.getCoffee().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order(items, total);
        orderRepository.addOrder(order);
        System.out.println("Order placed with total amount: " + total);
    }

    @Override
    public void addNewCoffee(Coffee coffee) {
        coffeeRepository.addCoffee(coffee);
    }
}
