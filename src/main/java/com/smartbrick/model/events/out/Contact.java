package com.smartbrick.model.events.out;

import com.smartbrick.model.events.in.ProjectContact;

import java.util.UUID;

public class Contact {

    private UUID id;
    private UUID projectId;
    private String contactType;
    private String title;
    private String value;

    public static Contact fromProjectContact(ProjectContact contact) {
        Contact c = new Contact();
        c.setId(contact.getId());
        c.setProjectId(contact.getProjectId());
        c.setContactType(contact.getContactType());
        c.setTitle(contact.getTitle());
        c.setValue(contact.getValue());
        return c;
    }

    public static Contact empty(ProjectContact contact) {
        Contact c = new Contact();
        c.setId(contact.getId());
        c.setProjectId(contact.getProjectId());
        return c;
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
