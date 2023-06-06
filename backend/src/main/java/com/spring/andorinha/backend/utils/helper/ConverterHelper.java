package com.spring.andorinha.backend.utils.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class ConverterHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <R, E> E convertRecordToEntity(R record, Class<E> entityClass) {
        return objectMapper.convertValue(record, entityClass);
    }

    public static <E, R> R convertEntityToRecord(E entity, Class<R> recordClass) {
        return objectMapper.convertValue(entity, recordClass);
    }

    public static <R, E> List<E> convertListRecordsToListEntities(List<R> records, Class<E> entityClass) {
        return objectMapper.convertValue(records, new TypeReference<List<E>>() {});
    }

    public static <E, R> List<R> convertListEntitiesToListRecords(List<E> entities, Class<R> recordClass) {
        return objectMapper.convertValue(entities, new TypeReference<List<R>>() {});
    }
}
