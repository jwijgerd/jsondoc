package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pojo.ApiDoc;

/**
 *
 * @author Daniel Ostermeier
 */
public interface ApiAnnotationHandler {

    boolean canHandle(AnnotatedElement candidate, Annotation annotation);
    void handle(AnnotatedElement element, ApiDoc doc);
}
