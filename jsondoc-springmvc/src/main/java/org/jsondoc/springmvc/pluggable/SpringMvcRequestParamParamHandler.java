package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.core.pojo.ApiParamType.QUERY;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pluggable.ApiParamAnnotationHandler;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocSupport;
import org.jsondoc.core.util.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcRequestParamParamHandler implements ApiParamAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof RequestParam && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiParamDoc doc) {

        Parameter parameter = (Parameter)element;

        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String type = JSONDocSupport.getParamObjects(parameter.getParameterType(), parameter.getGenericParameterType());

        doc.setName(annotation.value());
        doc.setDescription(annotation.value());
        doc.setType(type);
        doc.setRequired(String.valueOf(annotation.required()));
        doc.setParamType(QUERY);
    }
}
