package org.jsondoc.core.util;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Since the jdk does not provide a Parameter object, here it is.  It is backed
 * by a {@link Method} instance and parameter index.
 *
 * @author Daniel Ostermeier
 */
public class Parameter implements AnnotatedElement {

    private final Method method;
    private final int index;

    public Parameter(Method method, int index) {
        this.method = method;
        this.index = index;
    }

    /**
     * @return the method this parameter is defined in.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return the parameters index within the methods full list of parameters.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the formal parameter type of the parameter.
     * @see Method#getParameterTypes()
     */
    public Class<?> getParameterType() {
        return method.getParameterTypes()[index];
    }

    public Type getGenericParameterType() {
        return method.getGenericParameterTypes()[index];
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> type) {
        return getAnnotation(type) != null;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> type) {
        for (Annotation annotation : getDeclaredAnnotations()) {
            if (annotation.annotationType() == type) {
                return (T)annotation;
            }
        }
        return null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return method.getParameterAnnotations()[index];
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return method.getParameterAnnotations()[index];
    }

    public static List<Parameter> parametersFrom(Method method) {
      List<Parameter> parameters = newArrayList();
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            parameters.add(new Parameter(method, i));
        }
        return parameters;
    }
}