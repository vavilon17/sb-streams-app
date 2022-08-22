package com.smartbrick.streams;

import com.smartbrick.model.events.in.debezium.ProjectContactDebeziumEvent;
import com.smartbrick.model.events.out.Contact;
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
public class ProjectContactTopology {

    @Value("${kafka.topic.project_contact.debezium}")
    private String projectContactTopic;

    @Value("${kafka.topic.project_contact.table}")
    private String projectContactTableTopic;

    @Autowired
    void projectContactTopology(StreamsBuilder streamsBuilder) {
        KStream<UUID, ProjectContactDebeziumEvent> KS0 = streamsBuilder
                .stream(
                        projectContactTopic,
                        Consumed.with(DEBEZIUM_KEY_SERDE, PROJECT_CONTACT_DEBEZIUM_EVENT_SERDE)
                ).filter((k, v) -> v != null && MUTATION_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k.getId(), v));

        // upsert
        KS0.filter((k, v) -> UPSERT_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k, Contact.fromProjectContact(v.getAfter())))
                .to(projectContactTableTopic, Produced.with(Serdes.UUID(), CONTACT_SERDE));

//        // delete
        KS0.filter((k, v) -> v.getOp().equals("d"))
                .map((k, v) -> new KeyValue<>(k, Contact.empty(v.getBefore())))
                .to(projectContactTableTopic, Produced.with(Serdes.UUID(), CONTACT_SERDE));
    }

}
