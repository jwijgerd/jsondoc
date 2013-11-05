package org.jsondoc.core.pluggable.jsondoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pluggable.ApiAnnotationHandler;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pluggable.ApiObjectAnnotationHandler;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiVersionHandler implements ApiAnnotationHandler,
        ApiMethodAnnotationHandler,
        ApiObjectAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiVersion;
    }

    @Override
    public void handle(AnnotatedElement element, ApiObjectDoc doc) {
        ApiVersion annotation = element.getAnnotation(ApiVersion.class);
        doc.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiVersion annotation = element.getAnnotation(ApiVersion.class);
        doc.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
    }

    @Override
    public void handle(AnnotatedElement element, ApiDoc doc) {
        ApiVersion annotation = element.getAnnotation(ApiVersion.class);
        doc.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
    }
}
