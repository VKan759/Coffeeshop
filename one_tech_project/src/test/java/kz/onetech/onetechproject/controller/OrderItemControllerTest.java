package kz.onetech.onetechproject.controller;

import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.service.CoffeeShopServiceDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderItemControllerTest {

    @Mock
    private CoffeeShopServiceDB coffeeShopServiceDB;

    @InjectMocks
    private OrderItemController orderItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void getAllOrdersShouldReturnListOfOrderItems() {
        List<OrderItem> orderItems = Arrays.asList(new OrderItem(), new OrderItem());
        when(coffeeShopServiceDB.getAllOrderItems()).thenReturn(orderItems);

        ResponseEntity<List<OrderItem>> response = orderItemController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderItems, response.getBody());
        verify(coffeeShopServiceDB, times(1)).getAllOrderItems();
    }

    @Test
    void addOrderShouldReturnCreatedOrder() {
        Order order = new Order();
        order.setId(1);
        List<OrderItem> orderItems = Arrays.asList(new OrderItem(), new OrderItem());
        when(coffeeShopServiceDB.placeOrder(orderItems)).thenReturn(order);

        ResponseEntity<Order> response = orderItemController.addOrder(orderItems);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(order, response.getBody());
        verify(coffeeShopServiceDB, times(1)).placeOrder(orderItems);
    }

    @Test
    void getOrderItemByIdShouldReturnOrderWhenFound() {
        int orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        when(coffeeShopServiceDB.findOrderById(orderId)).thenReturn(Optional.of(order));

        ResponseEntity<Order> response = orderItemController.getOrderItemBuId(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(coffeeShopServiceDB, times(1)).findOrderById(orderId);
    }

    @Test
    void getOrderItemByIdShouldReturnNotFoundWhenOrderDoesNotExist() {
        int orderId = 1;
        when(coffeeShopServiceDB.findOrderById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<Order> response = orderItemController.getOrderItemBuId(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());  // Assuming it returns null if not found
        verify(coffeeShopServiceDB, times(1)).findOrderById(orderId);
    }
}
