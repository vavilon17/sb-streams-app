package com.smartbrick.model.events.out;

import com.smartbrick.model.events.in.Project;

import java.util.UUID;

public class ProjectDetails {

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

    public static ProjectDetails fromProject(Project project) {
        ProjectDetails obj = new ProjectDetails();
        obj.updateCoreFields(project);
        return obj;
    }

    public void updateCoreFields(Project other) {
        this.id = UUID.fromString(other.getId());
        this.title = other.getTitle();
        this.tenantId = other.getTenantId();
        this.projectType = other.getProjectType();
        this.location = other.getLocation();
        this.status = other.getStatus();
        this.createdAt = other.getCreatedAt();
        this.updatedAt = other.getUpdatedAt();
        this.stage = other.getStage();
        this.order = other.getOrder();
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
}
