package com.smartbrick.model.events.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartbrick.model.events.in.debezium.ProjectContactDebeziumEvent;
import com.smartbrick.model.events.out.Contact;

import java.util.UUID;

public class ProjectContact {

    private UUID id;
    @JsonProperty("project_id")
    private UUID projectId;
    @JsonProperty("contact_type")
    private String contactType;
    private String title;
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
