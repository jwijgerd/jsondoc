package org.jsondoc.core.pluggable;

import static org.jsondoc.core.util.JSONDocSupport.getFieldObject;
import static org.jsondoc.core.util.JSONDocSupport.getReturnObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiFieldHandler implements ApiObjectAnnotationHandler {
    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiObjectField;
    }

    @Override
    public void handle(AnnotatedElement element, ApiObjectDoc apiObject) {

        if (element instanceof Field) {
            handle((Field)element, apiObject);
        } else {
            handle((Method)element, apiObject);
        }
    }

    private void handle(Field field, ApiObjectDoc apiObject) {

        ApiObjectField annotation = field.getAnnotation(ApiObjectField.class);

        ApiObjectFieldDoc fieldDoc = new ApiObjectFieldDoc();

        fieldDoc.setName(field.getName());
        fieldDoc.setDescription(annotation.description());

        String[] typeChecks = getFieldObject(field);
        fieldDoc.setType(typeChecks[0]);
        fieldDoc.setMultiple(String.valueOf(isMultiple(field.getType())));
        fieldDoc.setFormat(annotation.format());
        fieldDoc.setAllowedvalues(annotation.allowedvalues());
        fieldDoc.setMapKeyObject(typeChecks[1]);
        fieldDoc.setMapValueObject(typeChecks[2]);
        fieldDoc.setMap(typeChecks[3]);

        apiObject.getFields().add(fieldDoc);
    }

    private void handle(Method method, ApiObjectDoc apiObject) {

        ApiObjectField annotation = method.getAnnotation(ApiObjectField.class);

        ApiObjectFieldDoc fieldDoc = new ApiObjectFieldDoc();

        fieldDoc.setName(method.getName());
        fieldDoc.setDescription(annotation.description());

        String[] typeChecks = getReturnObject(method);
        fieldDoc.setType(typeChecks[0]);
        fieldDoc.setMultiple(String.valueOf(isMultiple(method.getReturnType())));
        fieldDoc.setFormat(annotation.format());
        fieldDoc.setAllowedvalues(annotation.allowedvalues());
        fieldDoc.setMapKeyObject(typeChecks[1]);
        fieldDoc.setMapValueObject(typeChecks[2]);
        fieldDoc.setMap(typeChecks[3]);

        apiObject.getFields().add(fieldDoc);
    }
}
