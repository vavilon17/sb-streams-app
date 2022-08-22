package com.smartbrick.model.events.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smartbrick.model.events.in.ProjectMedia;

import java.util.UUID;

@JsonIgnoreProperties("deleted")
public class Media {
    private UUID id;
    private UUID projectId;
    private String section;
    private String type;
    private String filePath;
    private Integer order;

    public static Media formProjectMedia(ProjectMedia projectMedia) {
        Media m = new Media();
        m.setId(projectMedia.getId());
        m.setProjectId(projectMedia.getProjectId());
        m.setSection(projectMedia.getSection());
        m.setType(projectMedia.getType());
        m.setFilePath(projectMedia.getFilePath());
        m.setOrder(projectMedia.getOrder());
        return m;
    }

    public static Media empty(ProjectMedia projectMedia) {
        Media m = new Media();
        m.setId(projectMedia.getId());
        m.setProjectId(projectMedia.getProjectId());
        return m;
    }

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
