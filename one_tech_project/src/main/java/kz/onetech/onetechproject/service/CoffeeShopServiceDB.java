package kz.onetech.onetechproject.service;

import jakarta.transaction.Transactional;
import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB;
import kz.onetech.onetechproject.repository.jpa.OrderItemRepositoryDB;
import kz.onetech.onetechproject.repository.jpa.OrderRepositoryDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class CoffeeShopServiceDB implements CoffeeShopService {

    private final CoffeeRepositoryDB coffeeRepository;
    private final OrderRepositoryDB orderRepository;
    private final OrderItemRepositoryDB orderItemRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CoffeeShopServiceDB(CoffeeRepositoryDB coffeeRepositoryDB,
                               OrderRepositoryDB orderRepositoryDB,
                               OrderItemRepositoryDB orderItemRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.coffeeRepository = coffeeRepositoryDB;
        this.orderRepository = orderRepositoryDB;
        this.orderItemRepository = orderItemRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Order placeOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        double total = orderItems.stream()
                .mapToDouble(OrderItem::getOrderItemPrice)
                .sum();
        order.setOrderTotalAmount(total);
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
            addOrderItem(orderItem);
        });
        Order savedOrder = orderRepository.save(order);
        savedOrder.setOrderItems(orderItems);
        log.info("Order placed with total amount: " + total);
        savedOrder.getOrderItems().forEach(orderItem -> kafkaTemplate.send("orders-for-bar", OrderItemMapper.fromOrderItemToDTO(orderItem)));
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
        return orderRepository.findById(id);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public void showAllOrders() {
        log.info("Список всех заказов из БД:");
        getAllOrders().forEach(order -> log.info(order.toString()));
    }
}

