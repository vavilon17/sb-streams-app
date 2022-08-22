package com.smartbrick.model.events.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ProjectMedia {

    private UUID id;
    @JsonProperty("project_id")
    private UUID projectId;
    private String section;
    private String type;
    @JsonProperty("file_path")
    private String filePath;
    private Integer order;

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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
