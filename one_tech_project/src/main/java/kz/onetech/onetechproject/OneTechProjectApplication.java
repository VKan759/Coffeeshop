package kz.onetech.onetechproject;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.service.CoffeeShopServiceDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OneTechProjectApplication {

    public static void main(String[] args) {
        // Настройка контекста
        ApplicationContext context =
                SpringApplication.run(OneTechProjectApplication.class, args);

        // Получение сервисного класса через
        CoffeeShopServiceDB coffeeShopService = context.getBean(CoffeeShopServiceDB.class);

        // Добавление кофе в меню
        Coffee espresso = new Coffee("Espresso", 250);
        Coffee cappuccino = new Coffee("Cappuccino", 300);
        Coffee latte = new Coffee("Latte", 400);
        List.of(espresso, cappuccino, latte).forEach(coffeeShopService::addNewCoffee);

        // Создание заказа
        OrderItem item1 = new OrderItem();
        item1.setCoffee(espresso);
        item1.setQuantity(2);

        OrderItem item2 = new OrderItem();
        item2.setCoffee(cappuccino);
        item2.setQuantity(3);

        OrderItem item3 = new OrderItem();
        item3.setCoffee(latte);
        item3.setQuantity(5);

        // Размещение заказа и показ заказов
        coffeeShopService.placeOrder(Arrays.asList(item1, item2));
        coffeeShopService.placeOrder(List.of(item3));
        coffeeShopService.showAllOrders(coffeeShopService.getAllOrders());

        // Проверка AOP
        Optional<Coffee> notFoundCoffee = coffeeShopService.findCoffeeByName("Espresso1");
        Optional<Coffee> foundCoffee = coffeeShopService.findCoffeeByName("Espresso");

        List<Optional<Coffee>> list = List.of(notFoundCoffee, foundCoffee);
        list.forEach(System.out::println);
    }
}
