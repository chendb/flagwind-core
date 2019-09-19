package com.flagwind.associative;

import com.flagwind.application.Application;
import com.flagwind.associative.annotation.Associative;
import com.flagwind.associative.annotation.Associative.TriggerType;
import com.flagwind.lang.ExtensibleObject;
import com.flagwind.reflect.EntityTypeHolder;
import com.flagwind.reflect.entities.EntityField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

public class AssociativeUtils {

    private static Log LOG = LogFactory.getLog(AssociativeUtils.class);

    public static void setFieldValue(TriggerType triggerType, ExtensibleObject extensibleObject, EntityField field,
            Object value) {


            Set<Associative> associatives = field.getRepeatableAnnotations(Associative.class);
            for (Associative associative : associatives) {
                if (associative.tigger().equals(triggerType)) {
                    String provider = associative.provider();
                    AssociativeEntry entry = new AssociativeEntry(associative.name(), provider, associative.extras());
                    if (entry.getAssociativeProvider() != null) {
                        entry.execute(extensibleObject, value);
                    } else {
                        LOG.warn(String.format("没有发现属性%s的Associative定义%s %s", field.getName(), provider,
                                associative.name()));
                    }
                }
            }


//        if (field.isAnnotationPresent(Associatives.class)) {
//            Associatives associatives = field.getAnnotation(Associatives.class);
//            for (Associative associative : associatives.value()) {
//                if (associative.tigger().equals(triggerType)) {
//                    String provider = associative.provider();
//                    if (StringUtils.isEmpty(provider)) {
//                        provider = associative.source();
//                    }
//                    AssociativeEntry entry = new AssociativeEntry(associative.name(), provider, associative.extras());
//                    if (entry.getAssociativeProvider() != null) {
//                        entry.execute(extensibleObject, value);
//                    } else {
//                        LOG.warn(String.format("没有发现属性%s的Associative定义%s %s", field.getName(), provider,
//                                associative.name()));
//                    }
//                }
//            }
//        }
//        if (field.isAnnotationPresent(Associative.class)) {
//            Associative associative = field.getAnnotation(Associative.class);
//            if (associative.tigger().equals(triggerType)) {
//                AssociativeEntry entry = new AssociativeEntry(associative);
//
//                if (entry.getAssociativeProvider() != null) {
//                    entry.execute(extensibleObject, value);
//                } else {
//                    LOG.warn(String.format("没有发现属性%s的Associative定义%s %s", field.getName(), associative.provider(),
//                            associative.name()));
//                }
//            }
//        }

    }

    public static void setFieldValue(TriggerType triggerType, ExtensibleObject extensibleObject, String propertyName,
            Object value) {
        EntityField field = EntityTypeHolder.getField(extensibleObject.getClass(), propertyName);
        setFieldValue(triggerType, extensibleObject, field, value);
    }


    public static <T> T getValue(String providerName,Object originalValue) {
        AssociativeProvider provider = Application.resolve(providerName);
        return (T) provider.associate(originalValue);
    }

    public static <T> T getValue(String providerName,Object originalValue,Object extras) {
        AssociativeProvider provider = Application.resolve(providerName);
        return (T) provider.associate(new Object[]{originalValue, extras});
    }
}