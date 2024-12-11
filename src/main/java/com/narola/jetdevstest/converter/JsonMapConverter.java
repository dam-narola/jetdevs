package com.narola.jetdevstest.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting map to JSON string", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, HashMap.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON string to map", e);
        }
    }
}

