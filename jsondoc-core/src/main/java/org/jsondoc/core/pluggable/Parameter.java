package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Daniel Ostermeier
 */

public class Parameter implements AnnotatedElement {

    private final Method method;
    private final Class<?> parameterType;
    private final Annotation[] annotations;
    private final Type genericParameterType;
    private final int index;
//    private final String name;

    public Parameter(Method method, int index) {
        this.method = method;
        this.index = index;

        parameterType = method.getParameterTypes()[index];
        genericParameterType = method.getGenericParameterTypes()[index];
        annotations = method.getParameterAnnotations()[index];
//        name = method.getTypeParameters()[index].getName();
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public Type getGenericParameterType() {
        return genericParameterType;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> type) {
        return getAnnotation(type) != null;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == type) {
                return (T)annotation;
            }
        }
        return null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return annotations;
    }

/*
    public String getName() {
        return name;
    }
*/

    public int getIndex() {
        return index;
    }
}