package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.core.util.JSONDocSupport.getBodyObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.util.Parameter;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcRequestBody implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof RequestBody && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Parameter parameter = (Parameter)element;

        ApiBodyObjectDoc body = new ApiBodyObjectDoc();
        String[] objectDetails = getBodyObject(parameter.getMethod(), parameter.getIndex());
        body.setObject(objectDetails[0]);
        body.setMapKeyObject(objectDetails[1]);
        body.setMapValueObject(objectDetails[2]);
        body.setMultiple(String.valueOf(isMultiple(parameter.getParameterType())));
        body.setMap(objectDetails[3]);
        doc.setBodyobject(body);
    }
}


