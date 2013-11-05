package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.ApiObjectDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiObjectHandler implements ApiObjectAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiObject && candidate instanceof Class;
    }

    @Override
    public void handle(AnnotatedElement element, ApiObjectDoc apiObject) {
        ApiObject annotation = element.getAnnotation(ApiObject.class);
        apiObject.setName(annotation.name());
        apiObject.setDescription(annotation.description());
    }
}
