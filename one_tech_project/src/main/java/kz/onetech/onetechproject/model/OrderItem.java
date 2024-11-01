package kz.onetech.onetechproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "order_item_table")
@Entity
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Additive additive;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Size size = Size.DEFAULT;

    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

    private int quantity;

    private double orderItemPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private double calculateOrderItemPrice() {
        return quantity * (coffee.getPrice() * size.getSizeCoefficient()
                + (additive == null ? 0 : additive.getPrice()));
    }

    @PrePersist
    private void setNewOrderItemPrice() {
        this.orderItemPrice = calculateOrderItemPrice();
    }

    public double getOrderItemPrice() {
        return calculateOrderItemPrice();
    }
}
