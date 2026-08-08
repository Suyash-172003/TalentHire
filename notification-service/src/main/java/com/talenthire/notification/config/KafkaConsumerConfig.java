package com.talenthire.notification.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.talenthire.notification.dto.ApplicationAppliedEvent;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, ApplicationAppliedEvent> consumerFactory() {

	    Map<String, Object> props = new HashMap<>();

	    props.put(
	            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
	            "localhost:9092"
	    );

	    props.put(
	            ConsumerConfig.GROUP_ID_CONFIG,
	            "notification-group"
	    );

	    props.put(
	            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
	            StringDeserializer.class
	    );

	    props.put(
	            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
	            JsonDeserializer.class
	    );

	    JsonDeserializer<ApplicationAppliedEvent> deserializer =
	            new JsonDeserializer<>(ApplicationAppliedEvent.class);

	   
	    deserializer.addTrustedPackages(
	            "com.talenthire.notification.dto"
	    );

	  
	    deserializer.setUseTypeHeaders(false);

	    return new DefaultKafkaConsumerFactory<>(
	            props,
	            new StringDeserializer(),
	            deserializer
	    );
	}
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ApplicationAppliedEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ApplicationAppliedEvent>
                factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}