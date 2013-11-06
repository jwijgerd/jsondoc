package org.jsondoc.core.pluggable;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nullable;

import org.jsondoc.core.pluggable.jsondoc.JsonDocApiBodyObjectHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiErrorHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiFieldHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiHeaderHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiMethodHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiObjectHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiParamHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiResponseHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiVersionHandler;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;

import com.google.common.collect.Lists;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocGenerator {

    private final List<ApiAnnotationHandler> apiHandlers = newLinkedList();
    private final List<ApiMethodAnnotationHandler> apiMethodHandlers = newLinkedList();
    private final List<ApiObjectAnnotationHandler> apiObjectHandlers = newLinkedList();

    private final List<ApiAnnotationHandler> customApiHandlers = newLinkedList();
    private final List<ApiMethodAnnotationHandler> customApiMethodHandlers = newLinkedList();
    private final List<ApiObjectAnnotationHandler> customApiObjectHandlers = newLinkedList();

    public JsonDocGenerator() {

        // Setup the default annotation handlers.

        apiHandlers.add(new JsonDocApiHandler());
        apiHandlers.add(new JsonDocApiVersionHandler());

        apiMethodHandlers.add(new JsonDocApiMethodHandler());
        apiMethodHandlers.add(new JsonDocApiHeaderHandler());
        apiMethodHandlers.add(new JsonDocApiBodyObjectHandler());
        apiMethodHandlers.add(new JsonDocApiParamHandler());
        apiMethodHandlers.add(new JsonDocApiErrorHandler());
        apiMethodHandlers.add(new JsonDocApiResponseHandler());
        apiMethodHandlers.add(new JsonDocApiVersionHandler());

        apiObjectHandlers.add(new JsonDocApiObjectHandler());
        apiObjectHandlers.add(new JsonDocApiFieldHandler());
        apiObjectHandlers.add(new JsonDocApiVersionHandler());
    }

    public void register(ApiAnnotationHandler handler) {
        customApiHandlers.add(handler);
    }

    public void register(ApiMethodAnnotationHandler handler) {
        customApiMethodHandlers.add(handler);
    }

    public void register(ApiObjectAnnotationHandler handler) {
        customApiObjectHandlers.add(handler);
    }

    public JSONDoc createJsonDoc(String version, String base, Class<?> clazz, Class<?>... otherClasses) {
        return createJsonDoc(base, version, Lists.asList(clazz, otherClasses));
    }

    public JSONDoc createJsonDoc(String version, String basePath, Iterable<Class<?>> classes) {
        JSONDoc jsonDoc = new JSONDoc(version, basePath);
        jsonDoc.setApis(newHashSet(createApiDocs(classes)));
        jsonDoc.setObjects(newHashSet(createObjectDocs(classes)));
        return jsonDoc;
    }

    public Iterable<ApiDoc> createApiDocs(Class<?> clazz, Class<?>... otherClasses) {
        return createApiDocs(Lists.asList(clazz, otherClasses));
    }

    public Iterable<ApiDoc> createApiDocs(Iterable<Class<?>> classes) {
        List<ApiDoc> apiDocs = newArrayList();
        for (Class<?> clazz : classes) {
            ApiDoc apiDoc = createApiDoc(clazz);
            if (apiDoc != null) {
                apiDocs.add(apiDoc);
            }
        }
        return apiDocs;
    }

    public ApiDoc createApiDoc(Class<?> clazz) {

        // Process parents first as they give the most 'general' results.
        ApiDoc apiDoc = clazz.getSuperclass() != null ? createApiDoc(clazz.getSuperclass()) : null;

        // Process class level annotations.
        apiDoc = applyApiHandlers(clazz, apiDoc);
        if (apiDoc != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                ApiMethodDoc apiMethodDoc = applyApiMethodHandlers(method, null);

                if (apiMethodDoc != null) {
                    apiDoc.addMethod(apiMethodDoc);

                    for (Parameter parameter : compileParameters(method)) {
                        applyApiMethodHandlers(parameter, apiMethodDoc);
                    }
                }
            }
        }
        return apiDoc;
    }

    public Iterable<ApiObjectDoc> createObjectDocs(Class<?> clazz, Class<?>... otherClasses) {
        return createObjectDocs(Lists.asList(clazz, otherClasses));
    }

    public Iterable<ApiObjectDoc> createObjectDocs(Iterable<Class<?>> classes) {
        List<ApiObjectDoc> apiDocs = newArrayList();
        for (Class<?> clazz : classes) {
            ApiObjectDoc objectDoc = createObjectDoc(clazz);
            if (objectDoc != null) {
                apiDocs.add(objectDoc);
            }
        }
        return apiDocs;
    }

    public ApiObjectDoc createObjectDoc(Class<?> clazz) {

        // Process parents first as they give the most 'general' results.
        ApiObjectDoc apiObjectDoc = clazz.getSuperclass() != null ? createObjectDoc(clazz.getSuperclass()) : null;

        // Process class level annotations.
        apiObjectDoc = applyApiObjectHandlers(clazz, apiObjectDoc);
        if (apiObjectDoc != null) {
            for (Field field : clazz.getDeclaredFields()) {
                applyApiObjectHandlers(field, apiObjectDoc);
            }
            for (Method method : clazz.getDeclaredMethods()) {
                applyApiObjectHandlers(method, apiObjectDoc);
            }
        }
        return apiObjectDoc;
    }

    private ApiDoc applyApiHandlers(AnnotatedElement element, @Nullable ApiDoc apiDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            apiDoc = applyApiHandlers(element, apiDoc, annotation, customApiHandlers);
            apiDoc = applyApiHandlers(element, apiDoc, annotation, apiHandlers);
        }
        // Hack: the ApiVersion annotation is triggering the handling for non Api instances :(
        if (apiDoc != null && apiDoc.getName() == null) {
            return null;
        }
        return apiDoc;
    }

    private ApiDoc applyApiHandlers(AnnotatedElement element, ApiDoc apiDoc, Annotation annotation, Iterable<ApiAnnotationHandler> apiHandlers) {
        for (ApiAnnotationHandler apiHandler : apiHandlers) {
            if (apiHandler.canHandle(element, annotation)) {
                apiDoc = ensureNotNull(apiDoc);
                apiHandler.handle(element, apiDoc);
            }
        }
        return apiDoc;
    }

    private ApiMethodDoc applyApiMethodHandlers(AnnotatedElement element, @Nullable ApiMethodDoc apiMethodDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            apiMethodDoc = applyApiMethodHandlers(element, apiMethodDoc, annotation, customApiMethodHandlers);
            apiMethodDoc = applyApiMethodHandlers(element, apiMethodDoc, annotation, apiMethodHandlers);
        }
        return apiMethodDoc;
    }

    private ApiMethodDoc applyApiMethodHandlers(AnnotatedElement element,
                                                ApiMethodDoc apiMethodDoc,
                                                Annotation annotation,
                                                Iterable<ApiMethodAnnotationHandler> apiMethodHandlers) {
        for (ApiMethodAnnotationHandler handler : apiMethodHandlers) {
            if (handler.canHandle(element, annotation)) {
                apiMethodDoc = ensureNotNull(apiMethodDoc);
                handler.handle(element, apiMethodDoc);
            }
        }
        if (apiMethodDoc != null && apiMethodDoc.getPath() == null) {
            return null;
        }
        return apiMethodDoc;
    }

    private ApiObjectDoc applyApiObjectHandlers(AnnotatedElement element, @Nullable ApiObjectDoc apiObjectDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            apiObjectDoc = applyApiObjectHandlers(element, apiObjectDoc, annotation, customApiObjectHandlers);
            apiObjectDoc = applyApiObjectHandlers(element, apiObjectDoc, annotation, apiObjectHandlers);
        }
        // Hack: the ApiVersion annotation is triggering the handling for non ApiObject instances :(
        if (apiObjectDoc != null && apiObjectDoc.getName() == null) {
            return null;
        }
        return apiObjectDoc;
    }

    private ApiObjectDoc applyApiObjectHandlers(AnnotatedElement element,
                                                ApiObjectDoc apiObjectDoc,
                                                Annotation annotation,
                                                Iterable<ApiObjectAnnotationHandler> apiObjectHandlers) {
        for (ApiObjectAnnotationHandler apiHandler : apiObjectHandlers) {
            if (apiHandler.canHandle(element, annotation)) {
                apiObjectDoc = ensureNotNull(apiObjectDoc);
                apiHandler.handle(element, apiObjectDoc);
            }
        }
        return apiObjectDoc;
    }

    private ApiMethodDoc ensureNotNull(ApiMethodDoc doc) {
        if (doc != null) {
            return doc;
        }
        return new ApiMethodDoc();
    }

    private ApiDoc ensureNotNull(ApiDoc doc) {
        if (doc != null) {
            return doc;
        }
        return new ApiDoc();
    }

    private ApiObjectDoc ensureNotNull(ApiObjectDoc doc) {
        if (doc != null) {
            return doc;
        }
        return new ApiObjectDoc();
    }

    private List<Parameter> compileParameters(Method method) {

        List<Parameter> parameters = newArrayList();
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            parameters.add(new Parameter(method, i));
        }
        return parameters;
    }
}
