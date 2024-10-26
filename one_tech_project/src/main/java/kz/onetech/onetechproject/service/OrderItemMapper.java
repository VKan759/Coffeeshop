package kz.onetech.onetechproject.service;


import kz.onetech.onetechproject.model.Additive;
import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.model.OrderItemDTO;
import kz.onetech.onetechproject.model.Size;

public class OrderItemMapper {
    public static OrderItemDTO fromOrderItemToDTO(OrderItem orderItem) {
        if (orderItem == null) {
            throw new RuntimeException("OrderItem is empty");
        }
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .additive(orderItem.getAdditive())
                .size(orderItem.getSize())
                .coffeeName(orderItem.getCoffee().getName())
                .quantity(orderItem.getQuantity())
                .orderId(orderItem.getOrder().getId())
                .build();
    }
}
