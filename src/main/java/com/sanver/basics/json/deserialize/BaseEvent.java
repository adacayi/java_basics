package com.sanver.basics.json.deserialize;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "eventType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreatedEvent.class, name = "CREATED"),
        @JsonSubTypes.Type(value = UpdatedEvent.class, name = "UPDATED"),
})
public abstract class BaseEvent {
    private EventType eventType;
}
