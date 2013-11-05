package org.jsondoc.springmvc.pluggable;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.springmvc.ListUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcApiHeaderHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof RequestMapping && candidate instanceof Method;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {

        Method method = (Method)element;

        RequestMapping baseMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);

        List<String> headers = ListUtils.merge(baseMapping.headers(), methodMapping.headers());
        List<ApiHeaderDoc> headerDocs = newArrayList();
        for (String header : headers) {
            headerDocs.add(new ApiHeaderDoc(header, header));
        }

        // TODO: if the header is already there, overwrite its contents...
        doc.getHeaders().addAll(headerDocs);
    }
}
