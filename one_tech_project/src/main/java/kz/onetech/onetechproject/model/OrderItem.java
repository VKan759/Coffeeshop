package kz.onetech.onetechproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private int id;
    private Coffee coffee;
    private int quantity;
    private int orderId;
}
