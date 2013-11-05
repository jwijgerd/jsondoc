package org.jsondoc.springmvc.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pluggable.Parameter;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.util.JSONDocSupport;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcRequestParamParamHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof RequestParam && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Parameter parameter = (Parameter)element;

        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String type = JSONDocSupport.getParamObjects(parameter.getParameterType(), parameter.getGenericParameterType());

        ApiParamDoc apiParam = new ApiParamDoc();
        apiParam.setName(annotation.value());
        apiParam.setDescription(annotation.value());
        apiParam.setType(type);
        apiParam.setRequired(String.valueOf(annotation.required()));
        apiParam.setAllowedvalues(new String[0]);

        doc.getQueryparameters().add(apiParam);

        // TODO: support PathVariable declarations?
        // TODO: read PathVariable declarations directly from the path?
    }
}
