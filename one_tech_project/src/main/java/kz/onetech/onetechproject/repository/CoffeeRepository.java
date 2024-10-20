package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.Coffee;

import java.util.Optional;

public interface CoffeeRepository {
    void addCoffee(Coffee coffee);

    Optional<Coffee> getCoffeeByName(String name);

    void removeCoffee(String name);
}
