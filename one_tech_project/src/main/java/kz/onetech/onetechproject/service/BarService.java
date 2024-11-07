package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки заказов на баре.
 * <p>
 * Слушает сообщения из Kafka-топика "orders-for-bar", имитируя
 * процесс приготовления заказа. При получении нового заказа выполняет
 * задержку, представляющую время обработки, и затем регистрирует сообщение
 * о готовности.
 */
@Service
@Slf4j
public class BarService {

    /**
     * Получает заказы для бара из Kafka-топика "orders-for-bar".
     * <p>
     * Метод подписан на Kafka-топик и автоматически вызывается при поступлении нового сообщения.
     * Выполняется проверка на {@code null}, затем имитируется процесс приготовления с помощью задержки.
     * Логирует процесс принятия и завершения заказа.
     *
     * @param orderItemDTO объект заказа, содержащий детали заказа {@link OrderItemDTO}.
     * @throws InterruptedException     если поток обработки был прерван.
     * @throws IllegalArgumentException если {@code orderItemDTO} равен {@code null}.
     */
    @KafkaListener(topics = "orders-for-bar")
    public void receiveOrder(OrderItemDTO orderItemDTO) throws InterruptedException {
        if (orderItemDTO == null) {
            throw new IllegalArgumentException("orderItemDTO не может быть null");
        }
        log.info("{} принят в работу...", orderItemDTO);
        Thread.sleep(5000);
        log.info("Готов к выдаче: {}", orderItemDTO);
    }
}
