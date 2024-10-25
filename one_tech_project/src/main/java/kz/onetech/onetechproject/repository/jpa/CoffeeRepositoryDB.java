package kz.onetech.onetechproject.repository.jpa;

import kz.onetech.onetechproject.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeeRepositoryDB extends JpaRepository<Coffee, Integer> {
    @Query("Select c from Coffee c where c.name = :name")
    Optional<Coffee> findCoffeeByName(@Param("name") String name);
}
