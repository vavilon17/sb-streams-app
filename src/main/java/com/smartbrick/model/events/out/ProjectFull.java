package com.smartbrick.model.events.out;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProjectFull {

    private UUID id;
    private String title;
    private String tenantId;
    private String projectType;
    private String location;
    private String status;
    private Long createdAt;
    private Long updatedAt;
    private String stage;
    private Integer order;
    private Map<UUID, AttributeValue> attributes; // TODO refactor this to List<>...
    private Map<UUID, Contact> contacts; // TODO refactor this to List<>...

    private List<Media> media;

    @Deprecated
    public static ProjectFull fromDetails(ProjectDetails details) {
        ProjectFull res = new ProjectFull();
        res.setId(details.getId());
        res.setTitle(details.getTitle());
        res.setTenantId(details.getTenantId());
        res.setProjectType(details.getProjectType());
        res.setLocation(details.getLocation());
        res.setStatus(details.getStatus());
        res.setCreatedAt(details.getCreatedAt());
        res.setUpdatedAt(details.getUpdatedAt());
        res.setStage(details.getStage());
        res.setOrder(details.getOrder());
        return res;
    }

    public void updateFromDetails(ProjectDetails projectDetails) {
        this.setTitle(projectDetails.getTitle());
        this.setTenantId(projectDetails.getTenantId());
        this.setProjectType(projectDetails.getProjectType());
        this.setLocation(projectDetails.getLocation());
        this.setStatus(projectDetails.getStatus());
        this.setCreatedAt(projectDetails.getCreatedAt());
        this.setUpdatedAt(projectDetails.getUpdatedAt());
        this.setStage(projectDetails.getStage());
        this.setOrder(projectDetails.getOrder());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Map<UUID, AttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<UUID, AttributeValue> attributes) {
        this.attributes = attributes;
    }

    public Map<UUID, Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Map<UUID, Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}
