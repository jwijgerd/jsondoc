package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.pluggable.ApiParamAnnotationHandler;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.util.JSONDocSupport;
import org.jsondoc.core.util.Parameter;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiParamHandler implements ApiParamAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiParam && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiParamDoc doc) {

        Parameter parameter = (Parameter)element;
        ApiParam annotation = parameter.getAnnotation(ApiParam.class);

        String type = JSONDocSupport.getParamObjects(parameter.getParameterType(), parameter.getGenericParameterType());

        if (hasText(annotation.name())) {
            doc.setName(annotation.name());
        }
        if (hasText(annotation.description())) {
            doc.setDescription(annotation.description());
        }
        if (hasText(annotation.format())) {
            doc.setFormat(annotation.format());
        }
        if (!hasText(doc.getType())) {
            doc.setType(type);
        }
        if (annotation.paramType() != ApiParamType.UNDEFINED) {
            doc.setParamType(annotation.paramType());
        }
        doc.addAllowedvalues(annotation.allowedvalues());
        doc.setRequired(String.valueOf(annotation.required()));
    }
}
