package at.ac.fhstp.eshop.shipping;

import at.ac.fhstp.eshop.shipping.configuration.KafkaTopicNames; // TODO: fix package visibilities -> .shipping.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({KafkaTopicNames.class})
public class ShippingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingApplication.class, args);
	}

}
