package org.jsondoc.springmvc;

import static org.jsondoc.core.util.JSONDocSupport.getParamObjects;
import static org.reflections.ReflectionUtils.getAllMethods;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Daniel Ostermeier
 */
public class SpringMvcJSONDocUtils {

    public static JSONDoc createJsonDoc(String pkg, String version, String basePath) {

        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage(pkg)
                )
        );

        Set<ApiDoc> apiDocs = createApiDocs(reflections.getTypesAnnotatedWith(Controller.class));
        Set<ApiObjectDoc> apiObjectDocs = createApiObjectDocs(reflections.getTypesAnnotatedWith(ApiObject.class));

        JSONDoc apiDoc = new JSONDoc(version, basePath);
        apiDoc.setApis(apiDocs);
        apiDoc.setObjects(apiObjectDocs);
        return apiDoc;

    }

    public static Set<ApiDoc> createApiDocs(Iterable<Class<?>> classes) {
        Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
        for (Class<?> controller : classes) {
            ApiDoc apiDoc = createApiDoc(controller);
            apiDoc.setMethods(createApiMethodDocs(controller));
            apiDocs.add(apiDoc);
        }
        return apiDocs;
    }

    public static ApiDoc createApiDoc(Class<?> controller) {

        ApiDoc apiDoc = new ApiDoc();
        apiDoc.setName(controller.getSimpleName());
        apiDoc.setDescription(controller.getSimpleName());

        // augment with content from the Api.class if it is available?

        return apiDoc;
    }

    public static List<ApiMethodDoc> createApiMethodDocs(Class<?> controller) {

        List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();

        Set<Method> endpoints = getAllMethods(controller, ReflectionUtils.withAnnotation(RequestMapping.class));
        for (Method endpoint : endpoints) {

            ApiMethodDoc apiMethodDoc = createApiMethodDoc(endpoint);
            apiMethodDocs.add(apiMethodDoc);
        }



        return apiMethodDocs;
    }

    public static ApiMethodDoc createApiMethodDoc(Method method) {

        RequestMapping baseMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);

        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        apiMethodDoc.setPath(readPath(baseMapping, methodMapping));
        apiMethodDoc.setDescription(method.getName()); // TODO: split the name on camel casing.

        // TODO: do we need to handle multiple 'methods' defined for a single method?
        List<RequestMethod> methods = merge(baseMapping.method(), methodMapping.method());
        if (methods.size() != 1) {
            // unexpected...
        }
        apiMethodDoc.setVerb(ApiVerb.valueOf(methods.get(0).name()));

        apiMethodDoc.setConsumes(merge(baseMapping.consumes(), methodMapping.consumes()));
        apiMethodDoc.setProduces(merge(baseMapping.produces(), methodMapping.produces()));

        List<String> headers = merge(baseMapping.headers(), methodMapping.headers());
        List<ApiHeaderDoc> headerDocs = new ArrayList<ApiHeaderDoc>();
        for (String header : headers) {
            headerDocs.add(new ApiHeaderDoc(header, header));
        }
        // TODO: adjust the header doc instances to if we have ApiHeader annotations present.
        apiMethodDoc.setHeaders(headerDocs);

        apiMethodDoc.setUrlparameters(createApiParamDocs(method));
        apiMethodDoc.setApierrors(createApiErrorDocs(method));
        apiMethodDoc.setResponse(createApiResponseObjectDoc(method));
        apiMethodDoc.setBodyobject(createApiBodyObjectDoc(method));

        return apiMethodDoc;
    }

    private static ApiBodyObjectDoc createApiBodyObjectDoc(Method method) {
        return JSONDocUtils.createApiBodyObjectDoc(method);
    }

    public static ApiResponseObjectDoc createApiResponseObjectDoc(Method method) {
        return JSONDocUtils.createApiResponseObjectDoc(method);
    }

    public static List<ApiErrorDoc> createApiErrorDocs(Method method) {
        // A: extract as many details from the listed exceptions as possible.
        List<ApiErrorDoc> result = new ArrayList<ApiErrorDoc>();
        for (Class<?> exception : method.getExceptionTypes()) {
            // 1: look for annotation on the exception that defines the http response status.
            // 2: use its name as a description
            result.add(new ApiErrorDoc("???", exception.getSimpleName())); // TODO: split name on camel case..
        }

        // TODO: B: augment the details with those provided by the annotations.

        return result;
    }

    public static List<ApiParamDoc> createApiParamDocs(Method method) {

        List<ApiParamDoc> docs = new ArrayList<ApiParamDoc>();
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();

        // for each parameter:
        for (int i = 0; i < parametersAnnotations.length; i++) {
            // for each annotation on the parameter:
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                // if the annotation is one that we are interested in:

                // TODO: retrieve extra details from the ApiParam annotation is available?

                if (parametersAnnotations[i][j] instanceof RequestParam) {
                    RequestParam annotation = (RequestParam)parametersAnnotations[i][j];
                    docs.add(createApiParamDoc(annotation, getParamObjects(method, i)));
                }

                if (parametersAnnotations[i][j] instanceof PathVariable) {
                    PathVariable annotation = (PathVariable)parametersAnnotations[i][j];
                    docs.add(createApiParamDoc(annotation, getParamObjects(method, i)));
                }
            }
        }
        return docs;
    }

    private static ApiParamDoc createApiParamDoc(RequestParam annotation, String type) {
        return new ApiParamDoc(
                annotation.value(),
                annotation.value(),
                type,
                String.valueOf(annotation.required()),
                new String[]{},
                ""
        );
    }

    private static ApiParamDoc createApiParamDoc(PathVariable annotation, String type) {
        return new ApiParamDoc(
                annotation.value(),
                annotation.value(),
                type,
                String.valueOf(true),
                new String[]{},
                ""
        );
    }

    private static String readPath(RequestMapping baseMapping, RequestMapping methodMapping) {
        String path = "";
        if (baseMapping.value().length > 0){
            path = baseMapping.value()[0];
        }
        path += methodMapping.value()[0];
        return path;
    }

    private static <T> List<T> merge(T[] arrayA, T[] arrayB) {
        Set<T> result = new HashSet<T>();
        result.addAll(Arrays.asList(arrayA));
        result.addAll(Arrays.asList(arrayB));
        return new ArrayList<T>(result);
    }

    public static Set<ApiObjectDoc> createApiObjectDocs(Iterable<Class<?>> classes) {
        return JSONDocUtils.createApiObjectDocs(classes);
    }
}
