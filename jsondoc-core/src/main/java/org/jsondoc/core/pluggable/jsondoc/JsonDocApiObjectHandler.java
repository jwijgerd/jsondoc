package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pluggable.ApiObjectAnnotationHandler;
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
    public void handle(AnnotatedElement element, ApiObjectDoc doc) {
        ApiObject annotation = element.getAnnotation(ApiObject.class);
        Class<?> clazz = (Class<?>)element;
        if (hasText(annotation.name())) {
            doc.setName(annotation.name());
        }
        if (!hasText(doc.getName())) {
            doc.setName(clazz.getSimpleName());
        }
        if (hasText(annotation.category())) {
            doc.setCategory(annotation.category());
        }
        if (hasText(annotation.description())) {
            doc.setDescription(annotation.description());
        }
    }
}
