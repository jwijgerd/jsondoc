package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pluggable.ApiAnnotationHandler;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiStatus;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiHandler implements ApiAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Class && annotation instanceof Api;
    }

    @Override
    public void handle(AnnotatedElement element, ApiDoc doc) {
        Api annotation = element.getAnnotation(Api.class);
        Class<?> clazz = (Class<?>)element;
        if (hasText(annotation.name())) {
            doc.setName(annotation.name());
        }
        if (!hasText(doc.getName())) {
            doc.setName(clazz.getSimpleName());
        }
        if (hasText(annotation.description())) {
            doc.setDescription(annotation.description());
        }
        if (annotation.status() != ApiStatus.UNDEFINED) {
            doc.setStatus(annotation.status());
        }
    }
}
