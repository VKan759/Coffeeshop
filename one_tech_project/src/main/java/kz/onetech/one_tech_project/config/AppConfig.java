package kz.onetech.one_tech_project.config;

import kz.onetech.one_tech_project.repository.CoffeeRepository;
import kz.onetech.one_tech_project.repository.CoffeeRepositoryImpl;
import kz.onetech.one_tech_project.repository.OrderRepository;
import kz.onetech.one_tech_project.repository.OrderRepositoryImpl;
import kz.onetech.one_tech_project.service.CoffeeShopService;
import kz.onetech.one_tech_project.service.CoffeeShopServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.ArrayList;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {

    @Bean
    public CoffeeRepository coffeeRepository() {
        return CoffeeRepositoryImpl.builder()
                .coffeeInventory(new HashMap<>())
                .build();
    }

    @Bean
    public OrderRepository orderRepository() {
        return OrderRepositoryImpl.builder()
                .orderList(new ArrayList<>())
                .build();
    }

    @Bean
    public CoffeeShopService coffeeShopService(CoffeeRepository coffeeRepository, OrderRepository orderRepository) {
        return new CoffeeShopServiceImpl(coffeeRepository, orderRepository);
    }
}

