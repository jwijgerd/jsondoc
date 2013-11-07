package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.JSONDocSupport.getFieldObject;
import static org.jsondoc.core.util.JSONDocSupport.getReturnObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;
import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        applyContentToDoc(propertyDoc, annotation, typeChecks, field.getType());
    }

    private void handle(Method method, ApiObjectDoc apiObject) {

        ApiObjectProperty annotation = method.getAnnotation(ApiObjectProperty.class);

        String propertyName = null;

        ApiObjectPropertyDoc propertyDoc = apiObject.getField(propertyName);
        if (propertyDoc == null) {
            propertyDoc = new ApiObjectPropertyDoc();
            propertyDoc.setActualName(propertyName);
            propertyDoc.setName(propertyName);
            apiObject.getFields().add(propertyDoc);
        }

        String[] typeChecks = getReturnObject(method);
        applyContentToDoc(propertyDoc, annotation, typeChecks, method.getReturnType());
    }

    private void applyContentToDoc(ApiObjectPropertyDoc fieldDoc, ApiObjectProperty annotation, String[] typeChecks, Class<?> type) {
        if (hasText(annotation.name())) {
            fieldDoc.setName(annotation.name());
        }
        if (hasText(annotation.description())) {
            fieldDoc.setDescription(annotation.description());
        }
        if (!hasText(fieldDoc.getType())) {
            fieldDoc.setType(typeChecks[0]);
        }
        if (!hasText(fieldDoc.getMultiple())) {
            fieldDoc.setMultiple(String.valueOf(isMultiple(type)));
        }
        if (!hasText(fieldDoc.getFormat())) {
            fieldDoc.setFormat(annotation.format());
        }
        if (!hasText(fieldDoc.getMapKeyObject())) {
            fieldDoc.setMapKeyObject(typeChecks[1]);
        }
        if (!hasText(fieldDoc.getMapValueObject())) {
            fieldDoc.setMapValueObject(typeChecks[2]);
        }
        if (!hasText(fieldDoc.getMap())) {
            fieldDoc.setMap(typeChecks[3]);
        }
        fieldDoc.addAllowedvalues(annotation.allowedvalues());
    }

}
