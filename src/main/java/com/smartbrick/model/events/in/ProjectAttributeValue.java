package com.smartbrick.model.events.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ProjectAttributeValue {

    private UUID id;
    @JsonProperty("project_id")
    private UUID projectId;
    @JsonProperty("attribute_id")
    private UUID attributeId;
    @JsonProperty("attribute_key")
    private String attributeKey;
    private String value;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(UUID attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
