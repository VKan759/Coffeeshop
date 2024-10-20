package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.repository.CoffeeRepository;
import kz.onetech.onetechproject.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CoffeeShopServiceDB implements CoffeeShopService {
    private static final AtomicInteger ORDER_ID = new AtomicInteger(1);

    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;

    public CoffeeShopServiceDB(CoffeeRepository coffeeRepositoryDB, OrderRepository orderRepositoryDB) {
        this.coffeeRepository = coffeeRepositoryDB;
        this.orderRepository = orderRepositoryDB;
    }

    @Override
    public void placeOrder(List<OrderItem> items) {
        double total = items.stream()
                .mapToDouble(item -> item.getCoffee().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order(ORDER_ID.getAndIncrement(), items, total);
        orderRepository.addOrder(order);
        System.out.println("Order placed with total amount: " + total);
    }

    @Override
    public void addNewCoffee(Coffee coffee) {
        coffeeRepository.addCoffee(coffee);
    }

    @Override
    public Optional<Coffee> findCoffeeByName(String name) {
        return coffeeRepository.getCoffeeByName(name);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public void showAllOrders(List<Order> orders) {
        System.out.println("Список всех заказов из БД:");
        orders.forEach(System.out::println);
        System.out.println();
    }
}

