package kz.onetech.onetechproject.service;


import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.model.OrderItemDTO;

public class OrderItemMapper {

    private OrderItemMapper() {
        throw new UnsupportedOperationException("Утилитарный класс не может быть инстанцирован");
    }

    public static OrderItemDTO fromOrderItemToDTO(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("OrderItem is empty");
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
