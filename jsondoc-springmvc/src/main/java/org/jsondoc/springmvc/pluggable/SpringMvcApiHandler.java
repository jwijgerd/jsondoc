package org.jsondoc.springmvc.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pluggable.ApiAnnotationHandler;
import org.jsondoc.springmvc.StringUtils;
import org.springframework.stereotype.Controller;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcApiHandler implements ApiAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate.isAnnotationPresent(Controller.class) && candidate instanceof Class;
    }

    @Override
    public void handle(AnnotatedElement element, ApiDoc doc) {
        Class<?> clazz = (Class<?>)element;
        doc.setName(StringUtils.splitCamelCase(clazz.getSimpleName()));
        doc.setDescription(StringUtils.splitCamelCase(clazz.getSimpleName()));
    }
}
