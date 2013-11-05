package org.jsondoc.springmvc.pluggable;

import static org.jsondoc.springmvc.ListUtils.merge;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.util.JSONDocUtils;
import org.jsondoc.springmvc.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcApiMethodHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof RequestMapping && candidate instanceof Method;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Method method = (Method)element;

        RequestMapping baseMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);

        doc.setPath(readPath(baseMapping, methodMapping));
        doc.setDescription(StringUtils.splitCamelCase(method.getName()));
        doc.setVersion(JSONDocUtils.createApiVersionDoc(method));

        List<RequestMethod> methods = merge(baseMapping.method(), methodMapping.method());
        if (methods.size() != 1) {
            // TODO: do we need to handle multiple 'methods' defined for a single method?
        }
        doc.setVerb(ApiVerb.valueOf(methods.get(0).name()));

        doc.setConsumes(merge(baseMapping.consumes(), methodMapping.consumes()));
        doc.setProduces(merge(baseMapping.produces(), methodMapping.produces()));
    }

    private static String readPath(RequestMapping baseMapping, RequestMapping methodMapping) {
        String path = "";
        if (baseMapping.value().length > 0) {
            path = baseMapping.value()[0];
        }
        path += methodMapping.value()[0];
        return path;
    }
}
