package kz.onetech.onetechproject.repository.jpa;

import kz.onetech.onetechproject.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepositoryDB extends JpaRepository<OrderItem, Integer> {
}
