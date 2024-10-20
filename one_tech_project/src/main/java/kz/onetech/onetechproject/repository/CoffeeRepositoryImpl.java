package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.Coffee;
import lombok.Builder;
import java.util.Map;
import java.util.Optional;

@Builder
public class CoffeeRepositoryImpl implements CoffeeRepository {

    private Map<String, Coffee> coffeeInventory;

    @Override
    public void addCoffee(Coffee coffee) {
        coffeeInventory.put(coffee.getName(), coffee);
        System.out.printf("Added coffee: %s%n", coffee.getName());
    }

    @Override
    public Optional<Coffee> getCoffeeByName(String name) {
        if (coffeeInventory == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(coffeeInventory.get(name));
    }

    @Override
    public void removeCoffee(String name) {
        coffeeInventory.remove(name);
        System.out.printf("Removed coffee: %s%n", name);
    }
}
