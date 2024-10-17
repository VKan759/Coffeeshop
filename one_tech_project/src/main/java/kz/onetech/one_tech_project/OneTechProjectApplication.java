package kz.onetech.one_tech_project;

import kz.onetech.one_tech_project.config.AppConfig;
import kz.onetech.one_tech_project.model.Coffee;
import kz.onetech.one_tech_project.model.OrderItem;
import kz.onetech.one_tech_project.service.CoffeeShopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OneTechProjectApplication {

    public static void main(String[] args) {
        // Настройка контекста
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Получение сервисного класса через
        CoffeeShopService coffeeShopService = context.getBean(CoffeeShopService.class);

        // Добавление кофе в меню
        Coffee espresso = new Coffee("Espresso", 250);
        Coffee cappuccino = new Coffee("Cappuccino", 300);
        coffeeShopService.addNewCoffee(espresso);
        coffeeShopService.addNewCoffee(cappuccino);

        // Создание заказа
        OrderItem item1 = new OrderItem(espresso, 2);
        OrderItem item2 = new OrderItem(cappuccino, 1);

        coffeeShopService.placeOrder(Arrays.asList(item1, item2));

        context.close();
    }
}
