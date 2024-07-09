package ru.mts.homework.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class TestConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "test", groupId = "consumer-1")
    public void consumeTest(String msg) {
        logger.info("Recieved {}", msg);
    }
}
