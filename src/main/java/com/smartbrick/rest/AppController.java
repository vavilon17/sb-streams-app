package com.smartbrick.rest;

import com.smartbrick.model.events.out.ProjectFull;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.smartbrick.config.KafkaConfig.PROJECT_FULL_STORE_NAME;

@RestController
public class AppController {

    @Autowired
    private StreamsBuilderFactoryBean factoryBean;

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectFull> getProject(@PathVariable UUID id) {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<UUID, ProjectFull> projects = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(PROJECT_FULL_STORE_NAME, QueryableStoreTypes.keyValueStore())
        );
        ProjectFull obj = projects.get(id);
        return  obj != null ? ResponseEntity.ok(obj) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
