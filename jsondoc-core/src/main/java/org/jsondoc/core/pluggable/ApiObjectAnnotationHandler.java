package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pojo.ApiObjectDoc;

/**
 * @author Daniel Ostermeier
 */
public interface ApiObjectAnnotationHandler {
    boolean canHandle(AnnotatedElement candidate, Annotation annotation);
    void handle(AnnotatedElement element, ApiObjectDoc apiObject);
}
