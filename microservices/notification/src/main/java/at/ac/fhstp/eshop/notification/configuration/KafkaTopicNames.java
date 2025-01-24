package at.ac.fhstp.eshop.notification.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.kafka.topics")
public record KafkaTopicNames(String orderCreated, String shipmentCreated) {
}