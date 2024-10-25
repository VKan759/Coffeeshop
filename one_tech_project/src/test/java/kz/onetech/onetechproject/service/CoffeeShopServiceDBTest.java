package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.repository.jpa.CoffeeRepositoryDB;
import kz.onetech.onetechproject.repository.jpa.OrderItemRepositoryDB;
import kz.onetech.onetechproject.repository.jpa.OrderRepositoryDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CoffeeShopServiceDBTest {
    @Mock
    CoffeeRepositoryDB coffeeRepository;
    @Mock
    OrderRepositoryDB orderRepository;
    @Mock
    OrderItemRepositoryDB orderItemRepository;
    @InjectMocks
    CoffeeShopServiceDB coffeeShopServiceDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        Coffee coffee1 = Coffee.builder()
                .name("First coffee")
                .price(100)
                .id(1)
                .build();
        OrderItem orderItem1 = OrderItem.builder()
                .id(1)
                .coffee(coffee1)
                .quantity(1)
                .build();
        coffee1.setOrderItems(List.of(orderItem1));
        Order order = Order.builder()
                .id(1)
                .orderItems(List.of(orderItem1))
                .orderTotalAmount(orderItem1.getOrderItemPrice())
                .build();
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(coffee1);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem1);
        assertThat(coffeeShopServiceDB.placeOrder(List.of(orderItem1)))
                .usingRecursiveComparison()
                .isEqualTo(order);
        assertThat(orderRepository.findAll()).usingRecursiveComparison().isEqualTo(List.of(order));
    }

    @Test
    void testAddNewCoffee() {
        Coffee coffee = Coffee.builder()
                .name("New Coffee")
                .price(150)
                .build();
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(coffee);
        Coffee result = coffeeShopServiceDB.addNewCoffee(coffee);
        assertThat(result).usingRecursiveComparison().isEqualTo(coffee);
    }

    @Test
    void testAddOrderItem() {
        Coffee coffee = Coffee.builder()
                .name("Coffee")
                .price(100)
                .id(1)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .coffee(coffee)
                .quantity(2)
                .build();
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        OrderItem result = coffeeShopServiceDB.addOrderItem(orderItem);
        assertThat(result).usingRecursiveComparison().isEqualTo(orderItem);
    }

    @Test
    void testFindCoffeeByName() {
        Coffee coffee = Coffee.builder()
                .name("Espresso")
                .price(120)
                .build();
        when(coffeeRepository.findCoffeeByName("Espresso")).thenReturn(Optional.of(coffee));
        Optional<Coffee> result = coffeeShopServiceDB.findCoffeeByName("Espresso");
        assertThat(result).contains(coffee);
    }

    @Test
    void testGetAllOrders() {
        Order order = Order.builder()
                .id(1)
                .orderItems(List.of())
                .orderTotalAmount(200)
                .build();
        when(orderRepository.findAll()).thenReturn(List.of(order));
        List<Order> result = coffeeShopServiceDB.getAllOrders();
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(order));
    }

    @Test
    void testGetAllOrderItems() {
        OrderItem orderItem = OrderItem.builder()
                .id(1)
                .coffee(new Coffee())
                .quantity(2)
                .build();
        when(orderItemRepository.findAll()).thenReturn(List.of(orderItem));
        List<OrderItem> result = coffeeShopServiceDB.getAllOrderItems();
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(orderItem));
    }

    @Test
    void findOrderById() {
        Order order = Order.builder()
                .id(1)
                .orderItems(List.of())
                .orderTotalAmount(500)
                .build();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        Optional<Order> result = coffeeShopServiceDB.findOrderById(1);
        assertThat(result).usingRecursiveComparison().isEqualTo(Optional.of(order));
    }

    @Test
    void testPlaceOrderWithMultipleOrderItems() {
        Coffee coffee1 = Coffee.builder()
                .name("First coffee")
                .price(100)
                .id(1)
                .build();
        Coffee coffee2 = Coffee.builder()
                .name("Second coffee")
                .price(150)
                .id(2)
                .build();
        OrderItem orderItem1 = OrderItem.builder()
                .id(1)
                .coffee(coffee1)
                .quantity(2)
                .build();
        OrderItem orderItem2 = OrderItem.builder()
                .id(2)
                .coffee(coffee2)
                .quantity(3)
                .build();
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);
        double expectedTotal = orderItem1.getOrderItemPrice() * orderItem1.getQuantity() + orderItem2.getOrderItemPrice() * orderItem2.getQuantity();
        Order order = Order.builder()
                .id(1)
                .orderItems(orderItems)
                .orderTotalAmount(expectedTotal)
                .build();

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem1, orderItem2);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        assertThat(coffeeShopServiceDB.placeOrder(orderItems))
                .usingRecursiveComparison()
                .isEqualTo(order);
    }
}

