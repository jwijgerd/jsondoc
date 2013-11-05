package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pojo.ApiObjectDoc;

/**
 * The ApiObjectAnnotationHandler defines the base API for implementations of
 * plugins that extract details about objects used in the API.
 * <p/>
 * Registered implementations of this interface will be triggered when
 * their {@link #canHandle(AnnotatedElement, Annotation)} method returns true,
 * and will receive the current {@link ApiObjectDoc} object being constructed.
 *
 * @author Daniel Ostermeier
 */
public interface ApiObjectAnnotationHandler {

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
     * in the provided {@link ApiObjectDoc} instance.
     *
     * @param element the element that triggered this callback.
     * @param doc     the api document object in which information can be stored.
     */
    void handle(AnnotatedElement element, ApiObjectDoc doc);
}
