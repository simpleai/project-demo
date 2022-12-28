package com.xiaoruiit.project.demo.learn.response.beanCopy;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.TimeZone;

public enum JsonMapper {
    INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper();

    private JsonMapper() {
        VisibilityChecker<?> visibilityChecker = this.mapper.getSerializationConfig().getDefaultVisibilityChecker();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setVisibility(visibilityChecker.withFieldVisibility(Visibility.ANY).withCreatorVisibility(Visibility.NONE).withGetterVisibility(Visibility.NONE).withSetterVisibility(Visibility.NONE).withIsGetterVisibility(Visibility.NONE));
        this.mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        this.mapper.registerModule(timeModule);
    }

    public ObjectMapper mapper() {
        return this.mapper;
    }
}
