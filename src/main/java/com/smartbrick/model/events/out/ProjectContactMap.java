package com.smartbrick.model.events.out;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectContactMap {

    private UUID projectId;
    private Map<UUID, Contact> contacts;

    public static ProjectContactMap fromContact(Contact contact) {
        ProjectContactMap obj = new ProjectContactMap();
        obj.setProjectId(contact.getProjectId());
        obj.setContacts(new HashMap<>());
        obj.getContacts().put(contact.getId(), contact);
        return obj;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public Map<UUID, Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Map<UUID, Contact> contacts) {
        this.contacts = contacts;
    }
}
