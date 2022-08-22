package com.smartbrick.model.events.out;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectMediaMap {
    private UUID projectId;
    private Map<UUID, Media> media;

    public static ProjectMediaMap fromMedia(Media media) {
        ProjectMediaMap obj = new ProjectMediaMap();
        obj.setProjectId(media.getProjectId());
        obj.setMedia(new HashMap<>());
        obj.getMedia().put(media.getId(), media);
        return obj;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public Map<UUID, Media> getMedia() {
        return media;
    }

    public void setMedia(Map<UUID, Media> media) {
        this.media = media;
    }
}
