package com.flagwind.reflect;


import com.flagwind.commons.StringUtils;
import com.flagwind.reflect.entities.EntityField;
import com.flagwind.reflect.entities.EntityType;
import com.flagwind.reflect.entities.EntityTypeUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class EntityTypeHolder {
    private static final Map<Class<?>, EntityType> CACHE = Collections.synchronizedMap(new WeakHashMap<>());


    public static EntityType getEntityType(Class<?> clzss) {
        if (CACHE.containsKey(clzss)) {
            return CACHE.get(clzss);
        }
        synchronized (CACHE) {

            if (!CACHE.containsKey(clzss)) {
                EntityType entityType = EntityTypeUtils.getEntityType(clzss);
                CACHE.put(clzss, entityType);
            }
            return CACHE.get(clzss);
        }

    }

    public static EntityType forName(String className) throws ClassNotFoundException {
        return CACHE.entrySet().stream().filter(s -> StringUtils.equalsIgnoreCase(s.getKey().toString(), className)).map(s -> s.getValue()).findFirst()
                .orElse(getEntityType(Class.forName(className)));
    }

    public static List<EntityField> getFields(Class<?> clzss) {
        EntityType entityType = getEntityType(clzss);
        return entityType.getFields();
    }


    public static EntityField getField(Class<?> entityClass, String name) {
        return getFields(entityClass).stream().filter(g -> g.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
