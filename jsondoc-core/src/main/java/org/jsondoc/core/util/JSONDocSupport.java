package org.jsondoc.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import org.jsondoc.core.annotation.ApiObject;
import org.reflections.ReflectionUtils;

public final class JSONDocSupport {

    public static final String UNDEFINED = "undefined";

    private JSONDocSupport() {
        // private constructor for utility code.
    }

    public static String getParamObjects(Class<?> type, Type genericType) {
        return analyse(type, genericType)[0];
    }

    public static String getParamObjects(Method method, Integer index) {
        Class<?> type = method.getParameterTypes()[index];
        Type genericType = method.getGenericParameterTypes()[index];

        return analyse(type, genericType)[0];
    }

    public static String[] getBodyObject(Method method, Integer index) {
        Class<?> type = method.getParameterTypes()[index];
        Type genericType = method.getGenericParameterTypes()[index];

        return analyse(type, genericType);
    }

    public static String[] getReturnObject(Method method) {

        Class<?> type = method.getReturnType();
        Type genericType = method.getGenericReturnType();

        return analyse(type, genericType);
    }

    public static String[] getFieldObject(Field field) {
        Type genericType = field.getGenericType();
        Class<?> type = field.getType();

        return analyse(type, genericType);
    }

    private static String[] analyse(Class<?> type, Type genericType) {

        if (Map.class.isAssignableFrom(type)) {
            Class<?> mapKeyClazz = null;
            Class<?> mapValueClazz = null;

            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)genericType;
                Type mapKeyType = parameterizedType.getActualTypeArguments()[0];
                Type mapValueType = parameterizedType.getActualTypeArguments()[1];
                mapKeyClazz = (Class<?>)mapKeyType;
                mapValueClazz = (Class<?>)mapValueType;
            }
            return new String[]{
                    getObjectNameFromAnnotatedClass(type),
                    (mapKeyClazz != null) ? mapKeyClazz.getSimpleName().toLowerCase() : null,
                    (mapValueClazz != null) ? mapValueClazz.getSimpleName().toLowerCase() : null,
                    "map"
            };

        }
        if (Collection.class.isAssignableFrom(type)) {
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)genericType;
                Type actualType = parameterizedType.getActualTypeArguments()[0];
                if (actualType instanceof Class) {
                    Class<?> clazz = (Class<?>)actualType;
                    return new String[]{getObjectNameFromAnnotatedClass(clazz), null, null, null};
                }
            }
            return new String[]{UNDEFINED, null, null, null};
        }
        if (type.isArray()) {
            return new String[]{
                    getObjectNameFromAnnotatedClass(type.getComponentType()), null, null, null
            };
        }
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)genericType;
            if (parameterizedType.getActualTypeArguments().length == 1) {
                if (parameterizedType.getActualTypeArguments()[0] instanceof Class) {
                    Class<?> typeArg = (Class<?>)parameterizedType.getActualTypeArguments()[0];

                    if (typeArg.isAnnotationPresent(ApiObject.class)) {
                        return new String[]{getObjectNameFromAnnotatedClass(typeArg), null, null, null};
                    }
                }
            }
        }
        return new String[]{getObjectNameFromAnnotatedClass(type), null, null, null};
    }

    public static String getObjectNameFromAnnotatedClass(Class<?> clazz) {
        Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
        if (annotatedClass.isAnnotationPresent(ApiObject.class)) {
            return annotatedClass.getAnnotation(ApiObject.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public static boolean isMultiple(Method method) {
        return Collection.class.isAssignableFrom(method.getReturnType()) || method.getReturnType().isArray();
    }

    public static boolean isMultiple(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz) || clazz.isArray();
    }
}
