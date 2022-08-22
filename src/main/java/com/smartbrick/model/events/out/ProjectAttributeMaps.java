package com.smartbrick.model.events.out;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectAttributeMaps {

    private UUID projectId;
    private Map<UUID, AttributeValue> attributes;

    public static ProjectAttributeMaps fromAttrValue(AttributeValue attributeValue) {
        ProjectAttributeMaps obj = new ProjectAttributeMaps();
        obj.setProjectId(attributeValue.getProjectId());
        obj.setAttributes(new HashMap<>());
        obj.getAttributes().put(attributeValue.getId(), attributeValue);
        return obj;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public Map<UUID, AttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<UUID, AttributeValue> attributes) {
        this.attributes = attributes;
    }
}
