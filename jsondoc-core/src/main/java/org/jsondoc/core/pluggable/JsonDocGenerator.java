package org.jsondoc.core.pluggable;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nullable;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;

import com.google.common.collect.Lists;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocGenerator {

    private final List<ApiAnnotationHandler> apiHandlers = newArrayList();
    private final List<ApiMethodAnnotationHandler> apiMethodHandlers = newArrayList();
    private final List<ApiObjectAnnotationHandler> apiObjectHandlers = newArrayList();

    public JsonDocGenerator() {
        register(new JsonDocApiHandler());
        register((ApiAnnotationHandler)new JsonDocApiVersionHandler());

        register((ApiMethodAnnotationHandler)new JsonDocApiVersionHandler());
        register(new JsonDocApiMethodHandler());
        register(new JsonDocApiHeaderHandler());
        register(new JsonDocApiBodyObjectHandler());
        register(new JsonDocApiParamHandler());
        register(new JsonDocApiErrorHandler());
        register(new JsonDocApiResponseHandler());

        register(new JsonDocApiObjectHandler());
        register(new JsonDocApiFieldHandler());
    }

    public void register(ApiAnnotationHandler handler) {
        apiHandlers.add(handler);
    }

    public void register(ApiMethodAnnotationHandler handler) {
        apiMethodHandlers.add(handler);
    }

    public void register(ApiObjectAnnotationHandler handler) {
        apiObjectHandlers.add(handler);
    }

    public Iterable<ApiDoc> createApiDocs(Class<?> clazz, Class<?>... otherClasses) {
        return createApiDocs(Lists.asList(clazz, otherClasses));
    }

    public Iterable<ApiDoc> createApiDocs(Iterable<Class<?>> classes) {
        List<ApiDoc> apiDocs = newArrayList();
        for (Class<?> clazz : classes) {
            apiDocs.add(createApiDoc(clazz));
        }
        return apiDocs;
    }

    public ApiDoc createApiDoc(Class<?> clazz) {

        // Process parents first as they give the most 'general' results.
        ApiDoc apiDoc = clazz.getSuperclass() != null ? createApiDoc(clazz.getSuperclass()) : new ApiDoc();

        // Process class level annotations.
        applyApiHandlers(clazz, apiDoc);

        for (Method method : clazz.getDeclaredMethods()) {
            ApiMethodDoc apiMethodDoc = applyApiMethodHandlers(method, null);

            if (apiMethodDoc != null) {
                apiDoc.addMethod(apiMethodDoc);

                for (Parameter parameter : compileParameters(method)) {
                    applyApiMethodHandlers(parameter, apiMethodDoc);
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
            apiDocs.add(createObjectDoc(clazz));
        }
        return apiDocs;
    }

    public ApiObjectDoc createObjectDoc(Class<?> clazz) {

        // Process parents first as they give the most 'general' results.
        ApiObjectDoc apiObjectDoc = clazz.getSuperclass() != null
                ? createObjectDoc(clazz.getSuperclass())
                : new ApiObjectDoc();

        // Process class level annotations.
        applyApiObjectHandlers(clazz, apiObjectDoc);

        for (Field field : clazz.getDeclaredFields()) {
            applyApiObjectHandlers(field, apiObjectDoc);
        }
        for (Method method : clazz.getDeclaredMethods()) {
            applyApiObjectHandlers(method, apiObjectDoc);
        }
        return apiObjectDoc;
    }

    private void applyApiHandlers(AnnotatedElement element, ApiDoc apiDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            for (ApiAnnotationHandler apiHandler : apiHandlers) {
                if (apiHandler.canHandle(element, annotation)) {
                    apiHandler.handle(element, apiDoc);
                }
            }
        }
    }

    private ApiMethodDoc applyApiMethodHandlers(AnnotatedElement element, @Nullable ApiMethodDoc apiMethodDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            for (ApiMethodAnnotationHandler handler : apiMethodHandlers) {
                if (handler.canHandle(element, annotation)) {
                    apiMethodDoc = ensureNotNull(apiMethodDoc);
                    handler.handle(element, apiMethodDoc);
                }
            }
        }
        return apiMethodDoc;
    }

    private void applyApiObjectHandlers(AnnotatedElement element, ApiObjectDoc apiObjectDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            for (ApiObjectAnnotationHandler apiHandler : apiObjectHandlers) {
                if (apiHandler.canHandle(element, annotation)) {
                    apiHandler.handle(element, apiObjectDoc);
                }
            }
        }
    }

    private ApiMethodDoc ensureNotNull(ApiMethodDoc doc) {
        if (doc != null) {
            return doc;
        }
        return new ApiMethodDoc();
    }


    private List<Parameter> compileParameters(Method method) {

        List<Parameter> parameters = newArrayList();
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            parameters.add(new Parameter(method, i));
        }
        return parameters;
    }
}
