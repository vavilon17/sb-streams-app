package com.smartbrick.streams;

import com.smartbrick.model.events.out.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

import static com.smartbrick.config.KafkaConfig.PROJECT_FULL_STORE_NAME;
import static com.smartbrick.serdes.AppSerdes.*;

@Component
public class ProjectFullTopology {

    @Value("${kafka.topic.project_attribute_value.table}")
    private String projectAttributeValueTableTopic;

    @Value("${kafka.topic.project_contact.table}")
    private String projectContactTableTopic;

    @Value("${kafka.topic.project_media.table}")
    private String projectMediaTableTopic;

    @Value("${kafka.topic.project.table}")
    private String projectTableTopic;

    @Value("${kafka.topic.project.full}")
    private String projectFullTopic;

    @Autowired
    void joinData(StreamsBuilder streamsBuilder) {
        // join project fields
        KTable<UUID, ProjectFull> KtProjectFull = streamsBuilder.table(projectFullTopic,
                Consumed.with(Serdes.UUID(), PROJECT_FULL_SERDE),
                Materialized.as(PROJECT_FULL_STORE_NAME)
        );

        KStream<UUID, ProjectDetails> KsProjects = streamsBuilder.stream(projectTableTopic,
                Consumed.with(Serdes.UUID(), PROJECT_DETAILS_SERDE));

        ValueJoiner<ProjectDetails, ProjectFull, ProjectFull> projDetailsJoiner = (projDetails, projFull) -> {
            projFull.updateFromDetails(projDetails);
            return projFull;
        };

        KsProjects.join(KtProjectFull, projDetailsJoiner).to(projectFullTopic,
                Produced.with(Serdes.UUID(), PROJECT_FULL_SERDE));

        // join project attributes
        setupForAttributes(streamsBuilder, KtProjectFull);

        // join project contacts
        setupForContacts(streamsBuilder, KtProjectFull);

        // join project media
        setupForMedia(streamsBuilder, KtProjectFull);
    }

    void setupForAttributes(StreamsBuilder streamsBuilder, KTable<UUID, ProjectFull> KtProjectFull) {
        KTable<UUID, ProjectAttributeMaps> KtAttributesAggr = streamsBuilder.stream(projectAttributeValueTableTopic,
                        Consumed.with(Serdes.UUID(), ATTRIBUTE_VALUE_SERDE))
                .filter((k, v) -> v != null && v.getId() != null) // temporary, to fix inconsistency // TODO remove
                .map((k, v) -> new KeyValue<>(v.getProjectId(), ProjectAttributeMaps.fromAttrValue(v)))
                .groupByKey(Grouped.with(Serdes.UUID(), PROJECT_ATTRIBUTES_SERDE))
                .reduce((existingAttributes, newAttributes) -> {
                    newAttributes.getAttributes().forEach((k, v) -> {
                        if (v.getValue() == null) {
                            existingAttributes.getAttributes().remove(k);
                        } else {
                            existingAttributes.getAttributes().put(k, v);
                        }
                    });
                    return existingAttributes;
                });

        ValueJoiner<ProjectFull, ProjectAttributeMaps, ProjectFull> joinerAttributes = (projectFull, projectAttributes) -> {
            projectFull.setAttributes(projectAttributes.getAttributes());
            return projectFull;
        };

        KtProjectFull.join(KtAttributesAggr, joinerAttributes)
                .toStream()
                .to(projectFullTopic, Produced.with(Serdes.UUID(), PROJECT_FULL_SERDE));
    }

    void setupForContacts(StreamsBuilder streamsBuilder, KTable<UUID, ProjectFull> KtProjectFull) {
        KTable<UUID, ProjectContactMap> KtContactsAggr = streamsBuilder.stream(projectContactTableTopic,
                        Consumed.with(Serdes.UUID(), CONTACT_SERDE))
                .map((k, v) -> new KeyValue<>(v.getProjectId(), ProjectContactMap.fromContact(v)))
                .groupByKey(Grouped.with(Serdes.UUID(), PROJECT_CONTACTS_SERDE))
                .reduce((existingContacts, newContacts) -> {
                    newContacts.getContacts().forEach((k, v) -> {
                        if (v.getValue() == null) {
                            existingContacts.getContacts().remove(k);
                        } else {
                            existingContacts.getContacts().put(k, v);
                        }
                    });
                    return existingContacts;
                });

        ValueJoiner<ProjectFull, ProjectContactMap, ProjectFull> joinerContacts = (projectFull, projectContacts) -> {
            projectFull.setContacts(projectContacts.getContacts());
            return projectFull;
        };

        KtProjectFull.join(KtContactsAggr, joinerContacts)
                .toStream()
                .to(projectFullTopic, Produced.with(Serdes.UUID(), PROJECT_FULL_SERDE));
    }

    void setupForMedia(StreamsBuilder streamsBuilder, KTable<UUID, ProjectFull> KtProjectFull) {
        KTable<UUID, ProjectMediaMap> KtMediaAggr = streamsBuilder.stream(projectMediaTableTopic,
                        Consumed.with(Serdes.UUID(), MEDIA_SERDE))
                .map((k, v) -> new KeyValue<>(v.getProjectId(), ProjectMediaMap.fromMedia(v)))
                .groupByKey(Grouped.with(Serdes.UUID(), PROJECT_MEDIA_SERDE))
                .reduce((existingMedia, newMedia) -> {
                    newMedia.getMedia().forEach((k, v) -> {
                        if (v.getFilePath() == null) {
                            existingMedia.getMedia().remove(k);
                        } else {
                            existingMedia.getMedia().put(k, v);
                        }
                    });
                    return existingMedia;
                });

        ValueJoiner<ProjectFull, ProjectMediaMap, ProjectFull> joinerMedia = (projectFull, projectMediaMap) -> {
            projectFull.setMedia(new ArrayList<>(projectMediaMap.getMedia().values()));
            return projectFull;
        };

        KtProjectFull.join(KtMediaAggr, joinerMedia)
                .toStream()
                .to(projectFullTopic, Produced.with(Serdes.UUID(), PROJECT_FULL_SERDE));
    }
}
