package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.JSONDocSupport.getBodyObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;
import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.util.Parameter;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiBodyObjectHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiBodyObject && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Parameter parameter = (Parameter)element;

        ApiBodyObjectDoc apiBodyObject = doc.getBodyobject();
        if (apiBodyObject == null) {
            apiBodyObject = new ApiBodyObjectDoc();
            doc.setBodyobject(apiBodyObject);
        }

        // Most of our information is inspected, so if someone else has already defined
        // content, lets not overwrite it.
        String[] bodyObject = getBodyObject(parameter.getMethod(), parameter.getIndex());
        if (!hasText(apiBodyObject.getObject())) {
            apiBodyObject.setObject(bodyObject[0]);
        }
        if (!hasText(apiBodyObject.getMapKeyObject())) {
            apiBodyObject.setMapKeyObject(bodyObject[1]);
        }
        if (!hasText(apiBodyObject.getMapValueObject())) {
            apiBodyObject.setMapValueObject(bodyObject[2]);
        }
        if (!hasText(apiBodyObject.getMultiple())) {
            apiBodyObject.setMultiple(String.valueOf(isMultiple(parameter.getParameterType())));
        }
        if (!hasText(apiBodyObject.getMap())) {
            apiBodyObject.setMap(bodyObject[3]);
        }
    }
}
