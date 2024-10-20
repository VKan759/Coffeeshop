package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.Coffee;
import kz.onetech.onetechproject.model.Order;
import kz.onetech.onetechproject.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
@Primary
public class OrderRepositoryDB implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addOrder(Order order) {
        String sql =
                """
                        insert into order_item_table (coffee_name, coffee_price, quantity, order_id, total_amount) values
                        (?, ?, ?, ?, ?)
                        """;
        order.getItems().forEach(orderItem -> {
            try {
                jdbcTemplate.update(sql,
                        orderItem.getCoffee().getName(),
                        orderItem.getCoffee().getPrice(),
                        orderItem.getQuantity(),
                        order.getId(),
                        order.getTotalAmount());
                log.info("Заказ добавлен");
            } catch (Exception e) {
                log.error("Ошибка добавления orderItem в DB");
            }
        });
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM order_item_table"; // Замените на имя вашей таблицы

        List<OrderItem> orderItems = jdbcTemplate.query(sql, new RowMapper<OrderItem>() {
            public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                String coffeeName = rs.getString("coffee_name");
                double coffeePrice = rs.getDouble("coffee_price");
                int quantity = rs.getInt("quantity");
                int orderId = rs.getInt("order_id");
                int id = rs.getInt("id");
                Coffee coffee = new Coffee();
                coffee.setName(coffeeName);
                coffee.setPrice(coffeePrice);
                return new OrderItem(id, coffee, quantity, orderId);
            }
        });

        Map<Integer, Order> orderMap = new HashMap<>();
        for (OrderItem item : orderItems) {
            Order order = orderMap.computeIfAbsent(item.getOrderId(), id -> new Order(id, new ArrayList<>(), 0));
            order.getItems().add(item);
            order.setTotalAmount(order.getTotalAmount() + (item.getQuantity() * item.getCoffee().getPrice()));
        }
        return new ArrayList<>(orderMap.values());
    }
}
