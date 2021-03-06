package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.JSONDocSupport.getFieldObject;
import static org.jsondoc.core.util.JSONDocSupport.getParamObjects;
import static org.jsondoc.core.util.JSONDocSupport.getReturnObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;
import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiObjectProperty;
import org.jsondoc.core.pluggable.ApiObjectAnnotationHandler;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiPropertyHandler implements ApiObjectAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiObjectProperty;
    }

    @Override
    public void handle(AnnotatedElement element, ApiObjectDoc doc) {

        if (element instanceof Field) {
            handle((Field)element, doc);
        } else {
            handle((Method)element, doc);
        }
    }

    private void handle(Field field, ApiObjectDoc apiObject) {

        ApiObjectProperty annotation = field.getAnnotation(ApiObjectProperty.class);

        ApiObjectPropertyDoc propertyDoc = apiObject.getField(field.getName());
        if (propertyDoc == null) {
            propertyDoc = new ApiObjectPropertyDoc();
            propertyDoc.setActualName(field.getName());
            propertyDoc.setName(field.getName());
            apiObject.getFields().add(propertyDoc);
        }

        String[] typeChecks = getFieldObject(field);
        applyAnnotation(propertyDoc, annotation, typeChecks, field.getType());
    }

    private void handle(Method method, ApiObjectDoc apiObject) {

        ApiObjectProperty annotation = method.getAnnotation(ApiObjectProperty.class);

        String propertyName = getPropertyName(method);

        ApiObjectPropertyDoc propertyDoc = apiObject.getField(propertyName);
        if (propertyDoc == null) {
            propertyDoc = new ApiObjectPropertyDoc();
            propertyDoc.setActualName(propertyName);
            propertyDoc.setName(propertyName);
            apiObject.getFields().add(propertyDoc);
        }

        String[] typeChecks;
        Class<?> type;
        if (method.getReturnType() != Void.TYPE) {
            typeChecks = getReturnObject(method);
            type = method.getReturnType();
        } else {
            typeChecks = getParamObjects(method, 0);
            type = method.getParameterTypes()[0];
        }
        applyAnnotation(propertyDoc, annotation, typeChecks, type);
    }

    private String getPropertyName(Member member) {
        String propertyName;
        if (member.getName().startsWith("get")) {
            propertyName = member.getName().substring(3);
        } else if (member.getName().startsWith("set")) {
            propertyName = member.getName().substring(3);
        } else if (member.getName().startsWith("is")) {
            propertyName = member.getName().substring(2);
        } else {
            propertyName = member.getName();
        }
        propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
        return propertyName;
    }

    private void applyAnnotation(ApiObjectPropertyDoc doc, ApiObjectProperty annotation, String[] typeChecks, Class<?> type) {
        if (hasText(annotation.name())) {
            doc.setName(annotation.name());
        }
        if (hasText(annotation.description())) {
            doc.setDescription(annotation.description());
        }
        if (!hasText(doc.getType())) {
            if (hasText(annotation.type())) {
                doc.setType(annotation.type());
            } else {
                doc.setType(typeChecks[0]);
            }
        }
        if (!hasText(doc.getMultiple())) {
            doc.setMultiple(String.valueOf(isMultiple(type)));
        }
        if (!hasText(doc.getFormat())) {
            doc.setFormat(annotation.format());
        }
        if (!hasText(doc.getMapKeyObject())) {
            doc.setMapKeyObject(typeChecks[1]);
        }
        if (!hasText(doc.getMapValueObject())) {
            doc.setMapValueObject(typeChecks[2]);
        }
        if (!hasText(doc.getMap())) {
            doc.setMap(typeChecks[3]);
        }
        if (annotation.allowedvalues().length > 0) {
            doc.addAllowedvalues(annotation.allowedvalues());
        } else {
            if (type.isEnum()) {
                List<String> allowedValues = new ArrayList<String>();
                Object[] enumValues = type.getEnumConstants();
                for (Object enumValue : enumValues) {
                    allowedValues.add(enumValue.toString());
                }
                doc.setAllowedvalues(allowedValues);
            }
        }
    }

}
