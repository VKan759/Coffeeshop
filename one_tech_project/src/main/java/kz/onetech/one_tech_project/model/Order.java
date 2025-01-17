package kz.onetech.one_tech_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private List<OrderItem> items;
    private double totalAmount;
}
