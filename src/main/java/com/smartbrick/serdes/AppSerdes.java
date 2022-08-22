package com.smartbrick.serdes;

import com.smartbrick.model.events.in.DebeziumKey;
import com.smartbrick.model.events.in.debezium.ProjectAttributeValueDebeziumEvent;
import com.smartbrick.model.events.in.debezium.ProjectContactDebeziumEvent;
import com.smartbrick.model.events.in.debezium.ProjectDebeziumEvent;
import com.smartbrick.model.events.in.debezium.ProjectMediaDebeziumEvent;
import com.smartbrick.model.events.out.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.Map;

public class AppSerdes extends Serdes {

    public static Serde<DebeziumKey> DEBEZIUM_KEY_SERDE = appSerde(DebeziumKey.class, wrapper());
    public static Serde<ProjectDebeziumEvent> PROJECT_DEBEZIUM_EVENT_SERDE
            = appSerde(ProjectDebeziumEvent.class, wrapper());
    public static Serde<ProjectAttributeValueDebeziumEvent> PROJECT_ATTRIBUTE_VALUE_DEBEZIUM_EVENT_SERDE
            = appSerde(ProjectAttributeValueDebeziumEvent.class, wrapper());
    public static Serde<ProjectDetails> PROJECT_DETAILS_SERDE
            = appSerde(ProjectDetails.class, wrapper());
    public static Serde<AttributeValue> ATTRIBUTE_VALUE_SERDE = appSerde(AttributeValue.class, wrapper());
    public static Serde<ProjectAttributeMaps> PROJECT_ATTRIBUTES_SERDE = appSerde(ProjectAttributeMaps.class, wrapper());
    public static Serde<ProjectFull> PROJECT_FULL_SERDE = appSerde(ProjectFull.class, wrapper());
    public static Serde<ProjectContactDebeziumEvent> PROJECT_CONTACT_DEBEZIUM_EVENT_SERDE
            = appSerde(ProjectContactDebeziumEvent.class, wrapper());
    public static Serde<Contact> CONTACT_SERDE = appSerde(Contact.class, wrapper());
    public static Serde<ProjectContactMap> PROJECT_CONTACTS_SERDE = appSerde(ProjectContactMap.class, wrapper());
    public static Serde<ProjectMediaDebeziumEvent> PROJECT_MEDIA_DEBEZIUM_EVENT_SERDE
            = appSerde(ProjectMediaDebeziumEvent.class, wrapper());
    public static Serde<Media> MEDIA_SERDE = appSerde(Media.class, wrapper());
    public static Serde<ProjectMediaMap> PROJECT_MEDIA_SERDE = appSerde(ProjectMediaMap.class, wrapper());

    static <T, R extends WrapperSerde<T>> Serde<T> appSerde(Class<T> clazz, R serde) {
        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, clazz);
        serde.configure(serdeConfigs, false);
        return serde;
    }

    static <T> WrapperSerde<T> wrapper() {
        return new WrapperSerde<>(new JsonSerializer<>(), new JsonDeserializer<>());
    }

}
