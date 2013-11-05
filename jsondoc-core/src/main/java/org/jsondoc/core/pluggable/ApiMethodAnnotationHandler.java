package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * @author Daniel Ostermeier
 */
public interface ApiMethodAnnotationHandler {
    boolean canHandle(AnnotatedElement candidate, Annotation annotation);
    void handle(AnnotatedElement element, ApiMethodDoc doc);
}
