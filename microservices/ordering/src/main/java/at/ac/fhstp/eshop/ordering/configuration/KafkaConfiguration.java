package at.ac.fhstp.eshop.ordering.configuration;

import at.ac.fhstp.eshop.ordering.order.OrderCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final KafkaTopicNames kafkaTopicNames;

    public KafkaConfiguration(KafkaTopicNames kafkaTopicNames) {
        this.kafkaTopicNames = kafkaTopicNames;
    }

    // in production, this would be managed by ops team by creating and configuring the topic via terraform or similar
    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(kafkaTopicNames.orderCreated())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, OrderCreatedEvent> orderCreatedEventProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS, "OrderCreatedEvent:" + OrderCreatedEvent.class.getName());

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate() {
        return new KafkaTemplate<>(orderCreatedEventProducerFactory());
    }
}
