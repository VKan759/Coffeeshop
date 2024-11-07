package kz.onetech.onetechproject.service;


import kz.onetech.onetechproject.model.OrderItem;
import kz.onetech.onetechproject.model.OrderItemDTO;

/**
 * Утилитарный класс для преобразования объектов {@link OrderItem} в объекты {@link OrderItemDTO}.
 * <p>
 * Класс предоставляет статический метод для преобразования сущности заказа
 * в DTO, который можно использовать для передачи данных, например, в Kafka-сообщениях.
 * Класс не может быть инстанцирован.
 */
public class OrderItemMapper {

    /**
     * Приватный конструктор для предотвращения создания экземпляра класса.
     * Бросает исключение {@link UnsupportedOperationException} при попытке инстанцирования.
     */
    private OrderItemMapper() {
        throw new UnsupportedOperationException("Утилитарный класс не может быть инстанцирован");
    }

    /**
     * Преобразует объект {@link OrderItem} в {@link OrderItemDTO}.
     * <p>
     * Этот метод используется для создания DTO, содержащего информацию о заказе,
     * например, имя кофе, добавки, размер, количество и идентификатор заказа.
     *
     * @param orderItem объект {@link OrderItem} для преобразования.
     * @return объект {@link OrderItemDTO}, содержащий данные из {@code orderItem}.
     * @throws IllegalArgumentException если {@code orderItem} равен {@code null}.
     */
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
