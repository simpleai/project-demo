package com.xiaoruiit.project.demo.infrastructure.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    public LocalDateTimeDeserializer() {
    }

    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Long timestamp = jsonParser.getLongValue();
        return LocalDateTime.ofEpochSecond(timestamp / 1000L, 0, ZoneOffset.ofHours(8));
    }
}
