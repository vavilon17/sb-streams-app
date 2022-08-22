package com.smartbrick.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProjectAttributesConsumer {

    @KafkaListener(topics = "project_attribute_value.table", groupId = "2")
    public void listenWithHeaders(ConsumerRecord<String, String> message) {
        System.out.println("MSG: key=" + message.key() + ", val= " + message.value());
    }

}
