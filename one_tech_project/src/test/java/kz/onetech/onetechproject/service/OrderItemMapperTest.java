package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    @Test
    void testFromOrderItemToDTONullOrderItem() {
        assertThrows(IllegalArgumentException.class, () -> OrderItemMapper.fromOrderItemToDTO(null));
    }

    @Test
    void testFromOrderItemToDTOValidOrderItem() {
        Coffee coffee = new Coffee();
        coffee.setName("Espresso");

        Order order = new Order();
        order.setId(1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setAdditive(Additive.ALTERNATIVE_MILK);
        orderItem.setSize(Size.L);
        orderItem.setCoffee(coffee);
        orderItem.setQuantity(2);
        orderItem.setOrder(order);

        OrderItemDTO orderItemDTO = OrderItemMapper.fromOrderItemToDTO(orderItem);

        assertNotNull(orderItemDTO);
        assertEquals(orderItem.getId(), orderItemDTO.getId());
        assertEquals(orderItem.getAdditive(), orderItemDTO.getAdditive());
        assertEquals(orderItem.getSize(), orderItemDTO.getSize());
        assertEquals(coffee.getName(), orderItemDTO.getCoffeeName());
        assertEquals(orderItem.getQuantity(), orderItemDTO.getQuantity());
        assertEquals(order.getId(), orderItemDTO.getOrderId());
    }
}
