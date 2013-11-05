package org.jsondoc.springmvc.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcApiErrorHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Method && annotation instanceof RequestMapping;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Method method = (Method)element;

        for (Class<?> exception : method.getExceptionTypes()) {
            if (exception.isAnnotationPresent(ResponseStatus.class)) {
                ResponseStatus status = exception.getAnnotation(ResponseStatus.class);
                ApiErrorDoc errorDoc = new ApiErrorDoc(String.valueOf(status.value().value()), status.reason());
                doc.getApierrors().add(errorDoc);
            }
        }

    }
}
