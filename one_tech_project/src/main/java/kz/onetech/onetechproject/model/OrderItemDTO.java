package kz.onetech.onetechproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private int id;

    private Additive additive;

    private Size size;

    private String coffeeName;

    private int quantity;

    private int orderId;
}
