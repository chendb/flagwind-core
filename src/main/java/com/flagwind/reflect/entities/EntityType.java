package com.flagwind.reflect.entities;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EntityType
{
	private String name;
	private Class instanceType;
	private ArrayList<EntityField> fields = new ArrayList<>();

	public EntityType(Class instanceType)
	{
		this.instanceType = instanceType;
		this.name = instanceType.getName();
	}

	public ArrayList<EntityField> getFields()
	{
		return fields;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Class getInstanceType()
	{
		return instanceType;
	}

	public void setInstanceType(Class instanceType)
	{
		this.instanceType = instanceType;
	}

	public <T> T createInstance() throws Exception
	{
		return (T) getInstanceType().newInstance();
	}

	
    /**
     * 是否有该注解
     *
     * @param annotationClass
     * @return
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        boolean result = false;
        if (instanceType != null) {
            result = instanceType.isAnnotationPresent(annotationClass);
        }
        return result;
    }

    /**
     * 获取指定的注解
     *
     * @param annotationClass
     * @param <T>
     * @return
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        T result = null;
        if (instanceType != null) {
			result = (T) AnnotatedElementUtils.getMergedAnnotation(instanceType,annotationClass);
            // result = (T) instanceType.getAnnotation(annotationClass);
        }
       
        return result;
	}

	public <T extends Annotation> Set<T> getRepeatableAnnotations(Class<T> annotationClass) {
		Set<T> result = new HashSet<>();
		if (instanceType != null) {
			result = AnnotatedElementUtils.getMergedRepeatableAnnotations(instanceType, annotationClass);
		}
		return result;
	}

	@Deprecated
	public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
		T[] result = null;
		if (instanceType != null) {
			result = (T[]) instanceType.getAnnotationsByType(annotationClass);
		}
		return result;
	}
}
