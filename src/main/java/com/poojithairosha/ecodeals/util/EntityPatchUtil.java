package com.poojithairosha.ecodeals.util;

import java.lang.reflect.Field;

public class EntityPatchUtil {

    public static <T> void applyPatch(T entity, String fieldName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field '" + fieldName + "' does not exist in " + entity.getClass().getSimpleName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field '" + fieldName + "' in " + entity.getClass().getSimpleName(), e);
        }
    }

}
