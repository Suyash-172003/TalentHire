package com.talenthire.notification.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.talenthire.notification.dto.AssessmentAssignedEvent;

@Configuration
public class AssessmentKafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

    @Bean
    public ConsumerFactory<String, AssessmentAssignedEvent>
    assessmentConsumerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServer
        );

        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "assessment-notification-group"
        );

        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class
        );

        JsonDeserializer<AssessmentAssignedEvent> deserializer =
                new JsonDeserializer<>(
                        AssessmentAssignedEvent.class
                );

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
    public ConcurrentKafkaListenerContainerFactory<
            String, AssessmentAssignedEvent>
    assessmentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<
                String, AssessmentAssignedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                assessmentConsumerFactory()
        );

        return factory;
    }
}
