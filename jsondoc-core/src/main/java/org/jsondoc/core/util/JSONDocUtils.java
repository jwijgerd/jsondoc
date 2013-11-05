package org.jsondoc.core.util;

import static org.jsondoc.core.util.JSONDocSupport.getBodyObject;
import static org.jsondoc.core.util.JSONDocSupport.getFieldObject;
import static org.jsondoc.core.util.JSONDocSupport.getParamObjects;
import static org.jsondoc.core.util.JSONDocSupport.getReturnObject;
import static org.jsondoc.core.util.JSONDocSupport.isMultiple;
import static org.reflections.util.ClasspathHelper.forPackage;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public final class JSONDocUtils {

    private JSONDocUtils() {
        // private constructor for utility code.
    }

    public static JSONDoc createJsonDoc(String pkg, String version, String basePath) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(forPackage(pkg))
        );
        JSONDoc apiDoc = new JSONDoc(version, basePath);
        apiDoc.setApis(createApiDocs(reflections.getTypesAnnotatedWith(Api.class)));
        apiDoc.setObjects(createApiObjectDocs(reflections.getTypesAnnotatedWith(ApiObject.class)));
        return apiDoc;
    }

    public static Set<ApiDoc> createApiDocs(Iterable<Class<?>> classes) {
        Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
        for (Class<?> controller : classes) {
            apiDocs.add(createApiDoc(controller));
        }
        return apiDocs;
    }

    public static Set<ApiObjectDoc> createApiObjectDocs(Iterable<Class<?>> classes) {
        Set<ApiObjectDoc> pojoDocs = new TreeSet<ApiObjectDoc>();
        for (Class<?> pojo : classes) {
            ApiObjectDoc pojoDoc = createApiObjectDoc(pojo);
            if (pojoDoc != null) {
                pojoDocs.add(pojoDoc);
            }
        }
        return pojoDocs;
    }

    public static List<ApiMethodDoc> createApiMethodDocs(Class<?> controller) {
        List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
        Method[] candidates = controller.getMethods();
        for (Method candidate : candidates) {
            if (candidate.isAnnotationPresent(ApiMethod.class)) {
                ApiMethodDoc apiMethodDoc = createApiMethodDoc(candidate);
                apiMethodDoc.setHeaders(createApiHeaderDocs(candidate));
                apiMethodDoc.setUrlparameters(createApiParamDocs(candidate));
                apiMethodDoc.setBodyobject(createApiBodyObjectDoc(candidate));

                if (candidate.isAnnotationPresent(ApiResponseObject.class)) {
                    apiMethodDoc.setResponse(
                            createApiResponseObjectDoc(candidate)
                    );
                }

                if (candidate.isAnnotationPresent(ApiErrors.class)) {
                    apiMethodDoc.setApierrors(createApiErrorDocs(candidate));
                }
                apiMethodDocs.add(apiMethodDoc);
            }
        }
        return apiMethodDocs;
    }

    public static ApiBodyObjectDoc createApiBodyObjectDoc(Method method) {
        Integer index = getApiBodyObjectIndex(method);
        if (index != -1) {
            Class<?> parameter = method.getParameterTypes()[index];
            boolean multiple = isMultiple(parameter);
            String[] bodyObject = getBodyObject(method, index);
            return new ApiBodyObjectDoc(
                    bodyObject[0], bodyObject[1], bodyObject[2], String.valueOf(multiple), bodyObject[3]
            );
        }
        return null;
    }

    public static ApiDoc createApiDoc(Class<?> clazz) {
        Api api = clazz.getAnnotation(Api.class);
        if (api == null) {
            throw new IllegalArgumentException("Unable to create ApiDoc object from a class that is not " +
                                                       "annotated with the @Api annotation.");
        }
        ApiDoc apiDoc = new ApiDoc();
        apiDoc.setDescription(api.description());
        apiDoc.setName(api.name());
        apiDoc.setVersion(createApiVersionDoc(clazz));
        apiDoc.setMethods(createApiMethodDocs(clazz));
        return apiDoc;
    }

    public static List<ApiErrorDoc> createApiErrorDocs(Method method) {
        List<ApiErrorDoc> apiMethodDocs = new ArrayList<ApiErrorDoc>();
        if (method.isAnnotationPresent(ApiErrors.class)) {
            ApiErrors annotation = method.getAnnotation(ApiErrors.class);
            for (ApiError apiError : annotation.apierrors()) {
                apiMethodDocs.add(new ApiErrorDoc(apiError.code(), apiError.description()));
            }
        }
        return apiMethodDocs;
    }

    public static List<ApiHeaderDoc> createApiHeaderDocs(AnnotatedElement element) {
        List<ApiHeaderDoc> docs = new ArrayList<ApiHeaderDoc>();
        if (element.isAnnotationPresent(ApiHeaders.class)) {
            ApiHeaders annotation = element.getAnnotation(ApiHeaders.class);
            for (ApiHeader apiHeader : annotation.headers()) {
                docs.add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description()));
            }
        }
        return docs;
    }

    public static ApiMethodDoc createApiMethodDoc(Method method) {
        ApiMethod annotation = method.getAnnotation(ApiMethod.class);
        ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
        apiMethodDoc.setPath(annotation.path());
        apiMethodDoc.setDescription(annotation.description());
        apiMethodDoc.setVerb(annotation.verb());
        apiMethodDoc.setConsumes(Arrays.asList(annotation.consumes()));
        apiMethodDoc.setProduces(Arrays.asList(annotation.produces()));
        apiMethodDoc.setVersion(createApiVersionDoc(method));
        return apiMethodDoc;
    }

    public static ApiObjectDoc createApiObjectDoc(Class<?> clazz) {

        if (!clazz.isAnnotationPresent(ApiObject.class)) {
            return null;
        }

        ApiObject annotation = clazz.getAnnotation(ApiObject.class);
        if (!annotation.show()) {
            return null;
        }

        List<ApiObjectFieldDoc> fieldDocs = new ArrayList<ApiObjectFieldDoc>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(ApiObjectField.class) != null) {
                fieldDocs.add(createApiObjectFieldDoc(field));
            }
        }

        Class<?> c = clazz.getSuperclass();
        if (c != null) {
            if (c.isAnnotationPresent(ApiObject.class)) {
                ApiObjectDoc objDoc = createApiObjectDoc(c);
                if (objDoc != null) {
                    fieldDocs.addAll(objDoc.getFields());
                }
            }
        }
        return new ApiObjectDoc(annotation.name(), annotation.description(), fieldDocs, createApiVersionDoc(clazz));
    }

    public static ApiObjectFieldDoc createApiObjectFieldDoc(Field field) {
        ApiObjectField annotation = field.getAnnotation(ApiObjectField.class);

        ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
        apiPojoFieldDoc.setName(field.getName());
        apiPojoFieldDoc.setDescription(annotation.description());
        apiPojoFieldDoc.setVersion(createApiVersionDoc(field));
        String[] typeChecks = getFieldObject(field);
        apiPojoFieldDoc.setType(typeChecks[0]);
        apiPojoFieldDoc.setMultiple(String.valueOf(isMultiple(field.getType())));
        apiPojoFieldDoc.setFormat(annotation.format());
        apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
        apiPojoFieldDoc.setMapKeyObject(typeChecks[1]);
        apiPojoFieldDoc.setMapValueObject(typeChecks[2]);
        apiPojoFieldDoc.setMap(typeChecks[3]);
        return apiPojoFieldDoc;
    }

    public static List<ApiParamDoc> createApiParamDocs(Method method) {

        List<ApiParamDoc> docs = new ArrayList<ApiParamDoc>();
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();

        // for each parameter:
        for (int i = 0; i < parametersAnnotations.length; i++) {

            ApiParamDoc apiParamDoc = null;
            ApiVersionDoc apiVersionDoc = null;

            // for each annotation on the parameter:
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                Annotation parameterAnnotation = parametersAnnotations[i][j];

                // if the annotation is one that we are interested in:
                if (parameterAnnotation instanceof ApiParam) {
                    ApiParam annotation = (ApiParam)parameterAnnotation;
                    apiParamDoc = createApiParamDoc(annotation, getParamObjects(method, i));
                }
            }

            if (apiParamDoc != null) {
                docs.add(apiParamDoc);
            }
        }
        return docs;
    }

    public static ApiParamDoc createApiParamDoc(ApiParam annotation, String type) {
        return new ApiParamDoc(
                annotation.name(),
                annotation.description(),
                type,
                String.valueOf(annotation.required()),
                annotation.allowedvalues(),
                annotation.format()
        );
    }

    public static ApiResponseObjectDoc createApiResponseObjectDoc(Method method) {

        String[] objectDetails = getReturnObject(method);
        return new ApiResponseObjectDoc(
                objectDetails[0],
                objectDetails[1],
                objectDetails[2],
                String.valueOf(isMultiple(method)),
                objectDetails[3]
        );
    }

    public static ApiVersionDoc createApiVersionDoc(AnnotatedElement element) {
        if (element.isAnnotationPresent(ApiVersion.class)) {
            ApiVersion apiVersion = element.getAnnotation(ApiVersion.class);
            return new ApiVersionDoc(apiVersion.since(), apiVersion.until());
        }
        return null;
    }

    private static Integer getApiBodyObjectIndex(Method method) {
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof ApiBodyObject) {
                    return i;
                }
            }
        }
        return -1;
    }
}
