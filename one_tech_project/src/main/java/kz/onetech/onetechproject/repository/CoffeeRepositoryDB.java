package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class CoffeeRepositoryDB implements CoffeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public CoffeeRepositoryDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCoffee(Coffee coffee) {
        int update = jdbcTemplate.update("insert into coffee (name, price) values (?,?)", coffee.getName(), coffee.getPrice()
        );
        if (update > 0) {
            log.info("Кофе успешно добавлено.");
        } else {
            log.info("Не удалось добавить кофе.");
        }
    }

    @Override
    public Optional<Coffee> getCoffeeByName(String name) {
        Coffee coffee = jdbcTemplate.queryForObject(
                "SELECT * FROM coffee WHERE name = ?",
                new Object[]{name},
                (rs, rowNum) -> new Coffee(
                        rs.getString("name"),
                        rs.getDouble("price")
                )
        );
        return Optional.ofNullable(coffee);

    }

    @Override
    public void removeCoffee(String name) {
        int update = jdbcTemplate.update("delete from coffee where name = ?", name
        );
        if (update > 0) {
            log.info("Кофе успешно удалено.");
        } else {
            log.info("Не удалось удалить кофе.");
        }
    }
}
