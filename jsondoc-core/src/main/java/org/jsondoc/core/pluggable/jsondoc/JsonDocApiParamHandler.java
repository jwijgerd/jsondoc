package org.jsondoc.core.pluggable.jsondoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pluggable.Parameter;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocSupport;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiParamHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiParam && candidate instanceof Parameter;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        Parameter parameter = (Parameter)element;
        ApiParam annotation = parameter.getAnnotation(ApiParam.class);

        String type = JSONDocSupport.getParamObjects(parameter.getParameterType(), parameter.getGenericParameterType());
        ApiParamDoc paramDoc = new ApiParamDoc();
        paramDoc.setName(annotation.name());
        paramDoc.setDescription(annotation.description());
        paramDoc.setAllowedvalues(annotation.allowedvalues());
        paramDoc.setFormat(annotation.format());
        paramDoc.setType(type);
        paramDoc.setRequired(String.valueOf(annotation.required()));

        // TODO: ensure we do not have duplicates here.

        switch (annotation.paramType()) {
            case PATH:
                doc.getPathparameters().add(paramDoc);
                break;
            case QUERY:
                doc.getQueryparameters().add(paramDoc);
                break;
        }
    }
}
