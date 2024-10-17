package kz.onetech.one_tech_project.repository;

import kz.onetech.one_tech_project.model.Coffee;
import lombok.Builder;
import java.util.Map;

@Builder
public class CoffeeRepositoryImpl implements CoffeeRepository {

    private Map<String, Coffee> coffeeInventory;

    @Override
    public void addCoffee(Coffee coffee) {
        coffeeInventory.put(coffee.getName(), coffee);
        System.out.printf("Added coffee: %s%n", coffee.getName());
    }

    @Override
    public Coffee getCoffeeByName(String name) {
        return coffeeInventory.get(name);
    }

    @Override
    public void removeCoffee(String name) {
        coffeeInventory.remove(name);
        System.out.printf("Removed coffee: %s%n", name);
    }
}
