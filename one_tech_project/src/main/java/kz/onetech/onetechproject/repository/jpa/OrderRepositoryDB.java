package kz.onetech.onetechproject.repository.jpa;

import kz.onetech.onetechproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryDB extends JpaRepository<Order, Integer> {

}
