package kz.onetech.one_tech_project.service;

import kz.onetech.one_tech_project.model.Coffee;
import kz.onetech.one_tech_project.model.OrderItem;

import java.util.List;

public interface CoffeeShopService {
    void placeOrder(List<OrderItem> items);
    void addNewCoffee(Coffee coffee);
}

