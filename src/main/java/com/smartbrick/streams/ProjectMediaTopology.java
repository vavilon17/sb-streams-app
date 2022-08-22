package com.smartbrick.streams;

import com.smartbrick.model.events.in.debezium.ProjectMediaDebeziumEvent;
import com.smartbrick.model.events.out.Media;
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
public class ProjectMediaTopology {

    @Value("${kafka.topic.project_media.debezium}")
    private String projectMediaTopic;

    @Value("${kafka.topic.project_media.table}")
    private String projectMediaTableTopic;

    @Autowired
    void projectMediaTopology(StreamsBuilder streamsBuilder) {
        KStream<UUID, ProjectMediaDebeziumEvent> KS0 = streamsBuilder
                .stream(
                        projectMediaTopic,
                        Consumed.with(DEBEZIUM_KEY_SERDE, PROJECT_MEDIA_DEBEZIUM_EVENT_SERDE)
                ).filter((k, v) -> v != null && MUTATION_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k.getId(), v));

        // upsert
        KS0.filter((k, v) -> UPSERT_OPS.contains(v.getOp()))
                .map((k, v) -> new KeyValue<>(k, Media.formProjectMedia(v.getAfter())))
                .to(projectMediaTableTopic, Produced.with(Serdes.UUID(), MEDIA_SERDE));

//        // delete
        KS0.filter((k, v) -> v.getOp().equals("d"))
                .map((k, v) -> new KeyValue<>(k, Media.empty(v.getBefore())))
                .to(projectMediaTableTopic, Produced.with(Serdes.UUID(), MEDIA_SERDE));
    }
}
