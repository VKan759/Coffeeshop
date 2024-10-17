package kz.onetech.one_tech_project.repository;

import kz.onetech.one_tech_project.model.Coffee;

public interface CoffeeRepository {
    void addCoffee(Coffee coffee);
    Coffee getCoffeeByName(String name);
    void removeCoffee(String name);
}
