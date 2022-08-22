package com.smartbrick.streams;

import com.smartbrick.model.events.in.debezium.ProjectAttributeValueDebeziumEvent;
import com.smartbrick.model.events.out.AttributeValue;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.smartbrick.config.DebeziumConfig.MUTATION_OPS;
import static com.smartbrick.config.DebeziumConfig.UPSERT_OPS;
import static com.smartbrick.serdes.AppSerdes.*;

@Component
public class ProjectAttributeTopology {

    @Value("${kafka.topic.project_attribute_value.debezium}")
    private String projectAttributeValueTopic;

    @Value("${kafka.topic.project_attribute_value.table}")
    private String projectAttributeValueTableTopic;

    @Autowired
    void projectAttributePipeline(StreamsBuilder streamsBuilder) {
        KStream<UUID, ProjectAttributeValueDebeziumEvent> KS0 = streamsBuilder
                .stream(
                        projectAttributeValueTopic,
                        Consumed.with(DEBEZIUM_KEY_SERDE, PROJECT_ATTRIBUTE_VALUE_DEBEZIUM_EVENT_SERDE)
                ).filter((k, v) -> v != null && MUTATION_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k.getId(), v));

        // upsert
        KS0.filter((k, v) -> UPSERT_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k, AttributeValue.fromProjectAttributeValue(v.getAfter())))
                .to(projectAttributeValueTableTopic, Produced.with(Serdes.UUID(), ATTRIBUTE_VALUE_SERDE));

//        // delete
        KS0.filter((k, v) -> v.getOp().equals("d"))
                .map((k, v) -> new KeyValue<>(k, AttributeValue.empty(v.getBefore())))
                .to(projectAttributeValueTableTopic, Produced.with(Serdes.UUID(), ATTRIBUTE_VALUE_SERDE));
    }
}
