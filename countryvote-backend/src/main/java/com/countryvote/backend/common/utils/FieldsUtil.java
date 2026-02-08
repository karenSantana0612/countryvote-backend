package com.countryvote.backend.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldsUtil {

    private FieldsUtil() {}

    public static String normalizeFieldName(String field, String defaultFields) {
        String raw = (field == null || field.isBlank()) ? defaultFields : field;

        List<String> fields = Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .distinct()
                .collect(Collectors.toList());

        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        if (fields.size() > 10){
            throw new IllegalArgumentException("Fields cannot be more than 10" + fields.size());
        }
        return String.join(",", fields);
    }
}
