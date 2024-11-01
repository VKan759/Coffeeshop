package kz.onetech.onetechproject.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.service.CoffeeShopServiceDB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderItemController {
    private final CoffeeShopServiceDB coffeeShopServiceDB;

    public OrderItemController(CoffeeShopServiceDB coffeeShopServiceDB) {
        this.coffeeShopServiceDB = coffeeShopServiceDB;
    }

    @GetMapping("/allOrders")
    ResponseEntity<List<OrderItem>> getAllOrders() {
        List<OrderItem> allOrderItems = coffeeShopServiceDB.getAllOrderItems();
        return ResponseEntity.ok(allOrderItems);
    }

    @PostMapping
    ResponseEntity<Order> addOrder(@RequestBody List<OrderItem> orderItems) {
        Order order = coffeeShopServiceDB.placeOrder(orderItems);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(order);
    }

    @GetMapping("/allOrderItems")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> allOrderItems = coffeeShopServiceDB.getAllOrderItems();
        return ResponseEntity.ok(allOrderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderItemBuId(@PathVariable("id") @Min(1) @NotNull int id) {
        Order order = coffeeShopServiceDB.findOrderById(id).orElseGet(() -> null);
        return ResponseEntity.ok(order);
    }
}
