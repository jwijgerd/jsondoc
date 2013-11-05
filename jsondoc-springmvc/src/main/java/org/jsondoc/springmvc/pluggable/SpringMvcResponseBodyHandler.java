package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.core.util.JSONDocSupport.getReturnObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcResponseBodyHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ResponseBody && candidate instanceof Method;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Method method = (Method)element;
        ApiResponseObjectDoc response = new ApiResponseObjectDoc();
        String[] objectDetails = getReturnObject(method);
        response.setObject(objectDetails[0]);
        response.setMapKeyObject(objectDetails[1]);
        response.setMapValueObject(objectDetails[2]);
        response.setMultiple(String.valueOf(isMultiple(method)));
        response.setMap(objectDetails[3]);
        doc.setResponse(response);
    }
}

