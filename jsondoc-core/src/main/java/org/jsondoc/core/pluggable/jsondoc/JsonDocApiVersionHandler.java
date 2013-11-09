package org.jsondoc.core.pluggable.jsondoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pluggable.ApiAnnotationHandler;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pluggable.ApiObjectAnnotationHandler;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
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
        if (element instanceof Class) {
            doc.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
        }
        if (element instanceof Member) {
            Member member = (Member)element;
            ApiObjectPropertyDoc property = doc.getField(member.getName());
            if (property == null) {
                property = new ApiObjectPropertyDoc();
                property.setActualName(member.getName());
                property.setName(member.getName());
                doc.getFields().add(property);
            }
            property.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
        }
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiVersion annotation = element.getAnnotation(ApiVersion.class);
        if (doc.getVersion() == null) {
            doc.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
        }
    }

    @Override
    public void handle(AnnotatedElement element, ApiDoc doc) {
        ApiVersion annotation = element.getAnnotation(ApiVersion.class);
        if (doc.getVersion() == null) {
            doc.setVersion(new ApiVersionDoc(annotation.since(), annotation.until()));
        }
    }
}
