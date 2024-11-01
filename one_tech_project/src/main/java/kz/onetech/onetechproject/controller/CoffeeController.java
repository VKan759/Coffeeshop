package kz.onetech.onetechproject.controller;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.service.CoffeeShopServiceDB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/coffee")
public class CoffeeController {
    private final CoffeeShopServiceDB coffeeShopServiceDB;

    public CoffeeController(CoffeeShopServiceDB coffeeShopServiceDB) {
        this.coffeeShopServiceDB = coffeeShopServiceDB;
    }

    @GetMapping("/{coffeeName}")
    public ResponseEntity<Coffee> getCoffee(@PathVariable("coffeeName") String coffeeName) {
        return coffeeShopServiceDB.findCoffeeByName(coffeeName).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Coffee> addCoffee(@RequestBody Coffee coffee) {
        coffeeShopServiceDB.addNewCoffee(coffee);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{coffeeName}")
                .buildAndExpand(coffee.getName())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(coffee);
    }
}
