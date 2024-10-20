package kz.onetech.onetechproject.config;

import kz.onetech.onetechproject.repository.CoffeeRepository;
import kz.onetech.onetechproject.repository.CoffeeRepositoryImpl;
import kz.onetech.onetechproject.repository.OrderRepository;
import kz.onetech.onetechproject.repository.OrderRepositoryImpl;
import kz.onetech.onetechproject.service.CoffeeShopService;
import kz.onetech.onetechproject.service.CoffeeShopServiceImpl;
import org.springframework.context.annotation.*;

import java.util.HashMap;
import java.util.ArrayList;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "kz.onetech.one_tech_project")
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

    @Primary
    @Bean
    public CoffeeShopService coffeeShopService(CoffeeRepository coffeeRepository, OrderRepository orderRepository) {
        return new CoffeeShopServiceImpl(coffeeRepository, orderRepository);
    }
}

