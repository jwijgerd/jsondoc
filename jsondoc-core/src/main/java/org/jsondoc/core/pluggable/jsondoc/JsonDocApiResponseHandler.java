package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.JSONDocSupport.getReturnObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;
import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiResponseHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiResponseObject && candidate instanceof Method;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Method method = (Method)element;
        ApiResponseObjectDoc response = doc.getResponse();
        if (response == null) {
            response = new ApiResponseObjectDoc();
            doc.setResponse(response);
        }

        String[] objectDetails = getReturnObject(method);
        if (hasText(response.getObject())) {
            response.setObject(objectDetails[0]);
        }
        if (hasText(response.getMapKeyObject())) {
            response.setMapKeyObject(objectDetails[1]);
        }
        if (hasText(response.getMapValueObject())) {
            response.setMapValueObject(objectDetails[2]);
        }
        if (hasText(response.getMultiple())) {
            response.setMultiple(String.valueOf(isMultiple(method)));
        }
        if (hasText(response.getMap())) {
            response.setMap(objectDetails[3]);
        }
    }
}
