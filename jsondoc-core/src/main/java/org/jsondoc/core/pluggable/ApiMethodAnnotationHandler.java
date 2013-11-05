package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * The ApiMethodAnnotationHandler defines the base API for implementations of
 * RESTful endpoint level plugins.
 * <p/>
 * Registered implementations of this interface will be triggered when
 * their {@link #canHandle(AnnotatedElement, Annotation)} method returns true,
 * and will receive the current {@link ApiMethodDoc} object being constructed.
 *
 * @author Daniel Ostermeier
 */
public interface ApiMethodAnnotationHandler {

    /**
     * Return true if this handler is able to process the current annotated
     * element.
     *
     * @param candidate  the annotated element.
     * @param annotation the annotation.
     * @return true if this handler wants to do something.
     */
    boolean canHandle(AnnotatedElement candidate, Annotation annotation);

    /**
     * Handle the annotated element, recording any extracted information
     * in the provided {@link ApiMethodDoc} instance.
     *
     * @param element the element that triggered this callback.
     * @param doc     the api document object in which information can be stored.
     */
    void handle(AnnotatedElement element, ApiMethodDoc doc);
}
