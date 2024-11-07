package kz.onetech.onetechproject.controller;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.service.CoffeeShopServiceDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CoffeeControllerTest {

    @InjectMocks
    private CoffeeController coffeeController;

    @Mock
    private CoffeeShopServiceDB coffeeShopServiceDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCoffeeShouldReturnCoffeeIfExists() {
        String coffeeName = "Latte";
        Coffee coffee = new Coffee();
        coffee.setName(coffeeName);
        when(coffeeShopServiceDB.findCoffeeByName(coffeeName)).thenReturn(Optional.of(coffee));

        ResponseEntity<Coffee> response = coffeeController.getCoffee(coffeeName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coffee, response.getBody());
        verify(coffeeShopServiceDB, times(1)).findCoffeeByName(coffeeName);
    }

    @Test
    void getCoffeeShouldReturnNotFoundIfCoffeeDoesNotExist() {
        String coffeeName = "Espresso";
        when(coffeeShopServiceDB.findCoffeeByName(coffeeName)).thenReturn(Optional.empty());

        ResponseEntity<Coffee> response = coffeeController.getCoffee(coffeeName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(coffeeShopServiceDB, times(1)).findCoffeeByName(coffeeName);
    }
}
