package com.sanver.basics.json.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class DeserializeBasedOnType {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws JsonProcessingException {
        var json = """
                {
                    "eventType" : "UPDATED",
                    "updatedBy" : "user"
                }
                """;
        var objectMapper = new ObjectMapper();
        UpdatedEvent deserializedValue = (UpdatedEvent) objectMapper.readValue(json, BaseEvent.class);
        logger.info("{}", deserializedValue);
    }
}

