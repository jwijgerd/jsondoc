package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.core.pojo.ApiParamType.PATH;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.pluggable.ApiParamAnnotationHandler;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocSupport;
import org.jsondoc.core.util.Parameter;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcPathVariableHandler implements ApiParamAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof PathVariable && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiParamDoc doc) {

        Parameter parameter = (Parameter)element;

        PathVariable annotation = parameter.getAnnotation(PathVariable.class);
        String type = JSONDocSupport.getParamObjects(parameter.getParameterType(), parameter.getGenericParameterType());

        doc.setName(annotation.value());
        doc.setDescription(annotation.value());
        doc.setType(type);
        doc.setRequired(String.valueOf(true));
        doc.setParamType(PATH);

        // TODO: read PathVariable declarations directly from the path?
    }
}

