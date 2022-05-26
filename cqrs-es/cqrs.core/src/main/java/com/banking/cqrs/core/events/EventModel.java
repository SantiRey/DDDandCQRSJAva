package com.banking.cqrs.core.events;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "eventStore")
public class EventModel {
    @Id
    private String id;
    Date timeStamp;
    String aggregateIdentifier;
    String aggregateType;
    int version;
    String eventType;
    BaseEvent eventData;

}
