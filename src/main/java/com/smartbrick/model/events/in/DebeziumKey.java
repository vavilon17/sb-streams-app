package com.smartbrick.model.events.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DebeziumKey {

    @JsonProperty("id")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
