package org.jsondoc.core.pluggable;

import static org.jsondoc.core.util.JSONDocSupport.getBodyObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;

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

        boolean multiple = isMultiple(parameter.getParameterType());
        String[] bodyObject = getBodyObject(parameter.getMethod(), parameter.getIndex());

        ApiBodyObjectDoc apiBodyObject = new ApiBodyObjectDoc();

        apiBodyObject.setObject(bodyObject[0]);
        apiBodyObject.setMapKeyObject(bodyObject[1]);
        apiBodyObject.setMapValueObject(bodyObject[2]);
        apiBodyObject.setMultiple(String.valueOf(multiple));
        apiBodyObject.setMap(bodyObject[3]);

        doc.setBodyobject(apiBodyObject);
    }
}
