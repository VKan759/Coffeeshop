package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface CoffeeShopService {
    void placeOrder(List<OrderItem> items);

    void addNewCoffee(Coffee coffee);

    Optional<Coffee> findCoffeeByName(String name);
}

