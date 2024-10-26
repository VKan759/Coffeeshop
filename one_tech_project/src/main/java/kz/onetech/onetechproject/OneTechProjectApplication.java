package kz.onetech.onetechproject;

import kz.onetech.onetechproject.model.*;
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

        // Получение сервисного класса
        CoffeeShopServiceDB coffeeShopService = context.getBean(CoffeeShopServiceDB.class);

        // Добавление кофе в меню
        Coffee espresso = new Coffee();
        espresso.setName("Espresso");
        espresso.setPrice(250);

        Coffee cappuccino = new Coffee();
        cappuccino.setName("Cappuccino");
        cappuccino.setPrice(300);
        Coffee latte = new Coffee();
        latte.setName("Latte");
        latte.setPrice(400);
        List.of(espresso, cappuccino, latte).forEach(coffeeShopService::addNewCoffee);

        // Создание заказа
        OrderItem item1 = new OrderItem();
        item1.setCoffee(espresso);
        item1.setQuantity(2);
        item1.setAdditive(Additive.DECAF);
        item1.setSize(Size.L);

        OrderItem item2 = new OrderItem();
        item2.setCoffee(cappuccino);
        item2.setQuantity(3);

        OrderItem item3 = new OrderItem();
        item3.setCoffee(latte);
        item3.setQuantity(5);

        // Размещение заказа и показ заказов
        //После размещения заказа заказ отправляется в бар для изготовления.

        System.out.println("-----------------------Размещение заказа и показ заказов---------------------------------");
        coffeeShopService.placeOrder(Arrays.asList(item1, item2));
        coffeeShopService.placeOrder(List.of(item3));

        // Поиск заказа для предоставления чека посетителю
        System.out.println("-----------------------Поиск заказа для предоставления чека посетителю---------------------------------");
        Optional<Order> orderById = coffeeShopService.findOrderById(1);
        System.out.println(orderById);
        System.out.println("-----------------------Поиск заказа для предоставления чека посетителю---------------------------------");

        //Проверка AOP
        System.out.println("-----------------------Проверка AOP---------------------------------");
        Optional<Coffee> notFoundCoffee = coffeeShopService.findCoffeeByName("Espresso1");
        Optional<Coffee> foundCoffee = coffeeShopService.findCoffeeByName("Espresso");
        List<Optional<Coffee>> list = List.of(notFoundCoffee, foundCoffee);
        list.forEach(System.out::println);
        System.out.println("-----------------------Проверка AOP---------------------------------");
    }
}
