package com.smartbrick.streams;

import com.smartbrick.model.events.in.debezium.ProjectDebeziumEvent;
import com.smartbrick.model.events.out.ProjectDetails;
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
public class ProjectTopology {

    @Value("${kafka.topic.project.debezium}")
    private String projectTopic;

    @Value("${kafka.topic.project.table}")
    private String projectTableTopic;


    @Autowired
    void projectPipeline(StreamsBuilder streamsBuilder) {
        // `project` events
        KStream<UUID, ProjectDebeziumEvent> KS0 = streamsBuilder
                .stream(projectTopic, Consumed.with(DEBEZIUM_KEY_SERDE, PROJECT_DEBEZIUM_EVENT_SERDE))
                .filter((k, v) -> v != null && MUTATION_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k.getId(), v));

        // upsert
        KS0.filter((k, v) -> UPSERT_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k, ProjectDetails.fromProject(v.getAfter())))
                .to(projectTableTopic, Produced.with(Serdes.UUID(), PROJECT_DETAILS_SERDE));

        // delete
        KS0.filter((k, v) -> v.getOp().equals("d"))
                .map((k, v) -> new KeyValue<>(k, (ProjectDetails) null))
                .to(projectTableTopic, Produced.with(Serdes.UUID(), PROJECT_DETAILS_SERDE));
    }
}



