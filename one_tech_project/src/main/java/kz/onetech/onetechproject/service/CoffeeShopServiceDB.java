package kz.onetech.onetechproject.service;

import jakarta.transaction.Transactional;
import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB;
import kz.onetech.onetechproject.repository.jpa.OrderItemRepositoryDB;
import kz.onetech.onetechproject.repository.jpa.OrderRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CoffeeShopServiceDB implements CoffeeShopService {

    private final CoffeeRepositoryDB coffeeRepository;
    private final OrderRepositoryDB orderRepository;
    private final OrderItemRepositoryDB orderItemRepository;

    public CoffeeShopServiceDB(CoffeeRepositoryDB coffeeRepositoryDB,
                               OrderRepositoryDB orderRepositoryDB,
                               OrderItemRepositoryDB orderItemRepository) {
        this.coffeeRepository = coffeeRepositoryDB;
        this.orderRepository = orderRepositoryDB;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order placeOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        double total = orderItems.stream()
                .mapToDouble(OrderItem::getOrderItemPrice)
                .sum();
        order.setOrderTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
            addOrderItem(orderItem);
        });
        savedOrder.setOrderItems(orderItems);
        System.out.println("Order placed with total amount: " + total);
        return savedOrder;
    }

    @Override
    public Coffee addNewCoffee(Coffee coffee) {
        coffeeRepository.save(coffee);
        return coffee;
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
        return orderItem;
    }

    @Override
    public Optional<Coffee> findCoffeeByName(String name) {
        return coffeeRepository.findCoffeeByName(name);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(int id) {
        Optional<Order> orderById = orderRepository.findById(id);
        orderItemRepository.findAll().stream().filter(x -> x.getOrder().equals(orderById.get())).forEach(System.out::println);
        return orderById;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public void showAllOrders() {
        System.out.println("Список всех заказов из БД:");
        getAllOrders().forEach(System.out::println);
        System.out.println();
    }
}

