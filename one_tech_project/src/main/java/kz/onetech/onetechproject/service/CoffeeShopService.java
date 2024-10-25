package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface CoffeeShopService {
    Order placeOrder(List<OrderItem> items);

    Coffee addNewCoffee(Coffee coffee);

    OrderItem addOrderItem(OrderItem orderItem);

    Optional<Coffee> findCoffeeByName(String name);
}

