package kz.onetech.onetechproject.service;

import kz.onetech.onetechproject.model.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BarService {
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
