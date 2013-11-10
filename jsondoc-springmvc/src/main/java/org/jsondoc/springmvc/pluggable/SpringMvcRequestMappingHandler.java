package org.jsondoc.springmvc.pluggable;

import static com.google.common.collect.Lists.newArrayList;
import static org.jsondoc.core.util.ListUtils.merge;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcRequestMappingHandler implements ApiMethodAnnotationHandler {

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

        List<RequestMethod> methods = merge(baseMapping.method(), methodMapping.method());
        doc.setVerb(ApiVerb.valueOf(methods.get(0).name()));

        doc.setConsumes(merge(baseMapping.consumes(), methodMapping.consumes()));
        doc.setProduces(merge(baseMapping.produces(), methodMapping.produces()));

        List<String> headers = merge(baseMapping.headers(), methodMapping.headers());
        List<ApiHeaderDoc> headerDocs = newArrayList();
        for (String header : headers) {
            ApiHeaderDoc headerDoc = new ApiHeaderDoc();
            headerDoc.setName(header);
            headerDoc.setDescription(header);
            headerDocs.add(headerDoc);
        }

        // Exceptions are not a type on there own, so drop this code here.
        for (Class<?> exception : method.getExceptionTypes()) {
            if (exception.isAnnotationPresent(ResponseStatus.class)) {
                ResponseStatus status = exception.getAnnotation(ResponseStatus.class);
                ApiErrorDoc errorDoc = new ApiErrorDoc();
                errorDoc.setCode(String.valueOf(status.value().value()));
                errorDoc.setDescription(status.reason());
                doc.getApierrors().add(errorDoc);
            }
        }

        // TODO: if the header is already there, overwrite its contents...
        doc.getHeaders().addAll(headerDocs);
    }

    private static String readPath(RequestMapping baseMapping, RequestMapping methodMapping) {
        return readPath(baseMapping) + readPath(methodMapping);
    }

    private static String readPath(RequestMapping mapping) {
        String path = "";
        if (mapping != null) {
            if (mapping.value().length > 0) {
                path += mapping.value()[0];
            }
        }
        return path;
    }
}
