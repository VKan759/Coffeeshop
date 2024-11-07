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
/**
 * Сервис для управления заказами и товарами (кофе) в кофейне.
 * <p>
 * Реализует методы для обработки заказов, добавления новых товаров,
 * поиска товаров по имени и получения информации обо всех заказах.
 * <p>
 * Поддерживает транзакционность и использует Kafka для отправки
 * информации о заказах в очередь.
 */
@Transactional
@Service
@Slf4j
public class CoffeeShopServiceDB implements CoffeeShopService {

    private final CoffeeRepositoryDB coffeeRepository;
    private final OrderRepositoryDB orderRepository;
    private final OrderItemRepositoryDB orderItemRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Конструктор для создания экземпляра CoffeeShopServiceDB с
     * необходимыми репозиториями и KafkaTemplate.
     *
     * @param coffeeRepositoryDB   репозиторий для управления товарами (кофе).
     * @param orderRepositoryDB    репозиторий для управления заказами.
     * @param orderItemRepository  репозиторий для управления элементами заказа.
     * @param kafkaTemplate        шаблон Kafka для отправки сообщений.
     */
    public CoffeeShopServiceDB(CoffeeRepositoryDB coffeeRepositoryDB,
                               OrderRepositoryDB orderRepositoryDB,
                               OrderItemRepositoryDB orderItemRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.coffeeRepository = coffeeRepositoryDB;
        this.orderRepository = orderRepositoryDB;
        this.orderItemRepository = orderItemRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Оформляет новый заказ на основе списка элементов заказа.
     * Рассчитывает общую стоимость заказа и сохраняет заказ в базе данных.
     * Отправляет каждый элемент заказа в Kafka топик "orders-for-bar".
     *
     * @param orderItems список элементов заказа.
     * @return объект Order, представляющий сохранённый заказ.
     */
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

    /**
     * Добавляет новый продукт (кофе) в базу данных.
     *
     * @param coffee объект Coffee, представляющий новый продукт.
     * @return объект Coffee, представляющий добавленный продукт.
     */
    @Override
    public Coffee addNewCoffee(Coffee coffee) {
        coffeeRepository.save(coffee);
        return coffee;
    }

    /**
     * Добавляет новый элемент заказа в базу данных.
     *
     * @param orderItem объект OrderItem, представляющий новый элемент заказа.
     * @return объект OrderItem, представляющий добавленный элемент заказа.
     */
    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
        return orderItem;
    }

    /**
     * Ищет продукт (кофе) по имени.
     *
     * @param name имя продукта для поиска.
     * @return объект Optional, содержащий Coffee, если продукт найден.
     */
    @Override
    public Optional<Coffee> findCoffeeByName(String name) {
        return coffeeRepository.findCoffeeByName(name);
    }


    /**
     * Возвращает список всех заказов в базе данных.
     *
     * @return список объектов Order.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Ищет заказ по идентификатору.
     *
     * @param id идентификатор заказа для поиска.
     * @return объект Optional, содержащий Order, если заказ найден.
     */
    public Optional<Order> findOrderById(int id) {
        return orderRepository.findById(id);
    }

    /**
     * Возвращает список всех элементов заказа в базе данных.
     *
     * @return список объектов OrderItem.
     */
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    /**
     * Выводит в лог информацию обо всех заказах в базе данных.
     */
    public void showAllOrders() {
        log.info("Список всех заказов из БД:");
        getAllOrders().forEach(order -> log.info(order.toString()));
    }
}

