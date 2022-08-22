package com.smartbrick.model.events.out;

import com.smartbrick.model.events.in.ProjectAttributeValue;

import java.util.UUID;

public class AttributeValue {

    private UUID id;
    private UUID projectId;
    private UUID attributeId;
    private String attributeKey;
    private String value;

    public static AttributeValue fromProjectAttributeValue(ProjectAttributeValue pav) {
        AttributeValue obj = new AttributeValue();
        obj.updateCoreFields(pav);
        return obj;
    }

    public static AttributeValue empty(ProjectAttributeValue pav) {
        AttributeValue obj = new AttributeValue();
        obj.setId(pav.getId());
        obj.setProjectId(pav.getProjectId());
        return obj;
    }

    public void updateCoreFields(ProjectAttributeValue other) {
        this.id = other.getId();
        this.projectId = other.getProjectId();
        this.attributeId = other.getAttributeId();
        this.attributeKey = other.getAttributeKey();
        this.value = other.getValue();
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
