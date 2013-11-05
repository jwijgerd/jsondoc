package org.jsondoc.springmvc.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pluggable.Parameter;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocSupport;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcPathVariableHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof PathVariable && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Parameter parameter = (Parameter)element;

        PathVariable annotation = parameter.getAnnotation(PathVariable.class);
        String type = JSONDocSupport.getParamObjects(parameter.getParameterType(), parameter.getGenericParameterType());

        ApiParamDoc apiParam = new ApiParamDoc();
        apiParam.setName(annotation.value());
        apiParam.setDescription(annotation.value());
        apiParam.setType(type);
        apiParam.setRequired(String.valueOf(true));
        apiParam.setAllowedvalues(new String[0]);

        doc.getPathparameters().add(apiParam);

        // TODO: read PathVariable declarations directly from the path?
    }
}

