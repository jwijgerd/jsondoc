package org.jsondoc.core.pluggable;

import static com.google.common.collect.ImmutableListMultimap.Builder;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Multimaps.index;
import static org.jsondoc.core.util.Parameter.parametersFrom;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.jsondoc.core.pluggable.jsondoc.JsonDocApiBodyObjectHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiErrorHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiHeaderHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiMethodHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiObjectHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiParamHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiPropertyHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiResponseHandler;
import org.jsondoc.core.pluggable.jsondoc.JsonDocApiVersionHandler;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.Parameter;
import org.jsondoc.core.visitor.SortAlphabeticallyVisitor;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocGenerator {

    public static final Function<ApiObjectDoc,String> KEY_FUNCTION = new Function<ApiObjectDoc,String>() {
        @Override
        public String apply(ApiObjectDoc apiObjectDoc) {
               return apiObjectDoc.getCategory();
        }
    };
    public static final Predicate<ApiObjectDoc> API_OBJECT_DOC_PREDICATE = new Predicate<ApiObjectDoc>() {
        @Override
        public boolean apply(@Nullable ApiObjectDoc input) {
            return input != null && input.getCategory() != null;
        }
    };
    private final List<ApiAnnotationHandler> apiHandlers = newLinkedList();
    private final List<ApiMethodAnnotationHandler> apiMethodHandlers = newLinkedList();
    private final List<ApiObjectAnnotationHandler> apiObjectHandlers = newLinkedList();
    private final List<ApiParamAnnotationHandler> apiParamHandlers = newLinkedList();

    private final List<ApiAnnotationHandler> customApiHandlers = newLinkedList();
    private final List<ApiMethodAnnotationHandler> customApiMethodHandlers = newLinkedList();
    private final List<ApiObjectAnnotationHandler> customApiObjectHandlers = newLinkedList();
    private final List<ApiParamAnnotationHandler> customApiParamHandlers = newLinkedList();

    public JsonDocGenerator() {

        // Setup the default annotation handlers.

        apiHandlers.add(new JsonDocApiHandler());
        apiHandlers.add(new JsonDocApiVersionHandler());

        apiMethodHandlers.add(new JsonDocApiMethodHandler());
        apiMethodHandlers.add(new JsonDocApiHeaderHandler());
        apiMethodHandlers.add(new JsonDocApiBodyObjectHandler());
        apiMethodHandlers.add(new JsonDocApiErrorHandler());
        apiMethodHandlers.add(new JsonDocApiResponseHandler());
        apiMethodHandlers.add(new JsonDocApiVersionHandler());

        apiParamHandlers.add(new JsonDocApiParamHandler());

        apiObjectHandlers.add(new JsonDocApiObjectHandler());
        apiObjectHandlers.add(new JsonDocApiPropertyHandler());
        apiObjectHandlers.add(new JsonDocApiVersionHandler());
    }

    public void register(ApiAnnotationHandler handler) {
        customApiHandlers.add(handler);
    }

    public void register(ApiMethodAnnotationHandler handler) {
        customApiMethodHandlers.add(handler);
    }

    public void register(ApiParamAnnotationHandler handler) {
        customApiParamHandlers.add(handler);
    }

    public void register(ApiObjectAnnotationHandler handler) {
        customApiObjectHandlers.add(handler);
    }

    public JSONDoc createJsonDoc(String version, String base, Class<?> clazz, Class<?>... otherClasses) {
        return createJsonDoc(base, version, Lists.asList(clazz, otherClasses));
    }

    public JSONDoc createJsonDoc(String version, String basePath, Iterable<Class<?>> classes) {
        JSONDoc jsonDoc = new JSONDoc(version, basePath);
        jsonDoc.setApis(createApiDocs(classes));
        jsonDoc.setObjects(createObjectDocs(classes));
        return jsonDoc;
    }

    public List<ApiDoc> createApiDocs(Class<?> clazz, Class<?>... otherClasses) {
        return createApiDocs(Lists.asList(clazz, otherClasses));
    }

    public List<ApiDoc> createApiDocs(Iterable<Class<?>> classes) {
        List<ApiDoc> apiDocs = newArrayList();
        for (Class<?> clazz : classes) {
            ApiDoc apiDoc = createApiDoc(clazz);
            if (apiDoc.isValid()) {
                apiDocs.add(apiDoc);
            }
        }
        return apiDocs;
    }

    public ApiDoc createApiDoc(Class<?> clazz) {

        // Process parents first as they give the most 'general' results.
        ApiDoc apiDoc = clazz.getSuperclass() != null ? createApiDoc(clazz.getSuperclass()) : new ApiDoc();

        // Process class level annotations.
        applyApiHandlers(clazz, apiDoc);
        if (apiDoc.isValid()) {
            for (Method method : clazz.getDeclaredMethods()) {
                ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
                applyApiMethodHandlers(method, apiMethodDoc);
                if (apiMethodDoc.isValid()) {
                    apiDoc.addMethod(apiMethodDoc);
                    for (Parameter parameter : parametersFrom(method)) {
                        ApiParamDoc apiParamDoc = new ApiParamDoc();
                        applyApiParamHandlers(parameter, apiParamDoc);
                        if (apiParamDoc.isValid()) {
                            switch (apiParamDoc.getParamType()) {
                                case PATH:
                                    apiMethodDoc.getPathparameters().add(apiParamDoc);
                                    break;
                                case QUERY:
                                    apiMethodDoc.getQueryparameters().add(apiParamDoc);
                                    break;
                                case UNDEFINED:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return apiDoc;
    }

    public Map<String,Collection<ApiObjectDoc>> createObjectDocs(Class<?> clazz, Class<?>... otherClasses) {
        return createObjectDocs(Lists.asList(clazz, otherClasses));
    }

    public Map<String,Collection<ApiObjectDoc>> createObjectDocs(Iterable<Class<?>> classes) {
        return new Builder<String,ApiObjectDoc>()
                 .putAll(index(filter(transform(classes, getCreateObjectDocFunction()),API_OBJECT_DOC_PREDICATE),
                         KEY_FUNCTION))
                 .orderValuesBy(SortAlphabeticallyVisitor.OBJECT_COMPARATOR)
                 .orderKeysBy(SortAlphabeticallyVisitor.COLLATOR)
                 .build().asMap();
    }

    private Function<Class<?>,ApiObjectDoc> getCreateObjectDocFunction() {
        return new Function<Class<?>,ApiObjectDoc>() {
            @Override
            public ApiObjectDoc apply(Class<?> aClass) {
                ApiObjectDoc objectDoc = createObjectDoc(aClass);
                return objectDoc.isValid() ? objectDoc : null;
            }
        };
    }

    public ApiObjectDoc createObjectDoc(Class<?> clazz) {

        // Process parents first as they give the most 'general' results.
        ApiObjectDoc apiObjectDoc = clazz.getSuperclass() != null ? createObjectDoc(clazz.getSuperclass()) : new ApiObjectDoc();

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
            applyApiHandlers(element, apiDoc, annotation, customApiHandlers);
        }
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiHandlers(element, apiDoc, annotation, apiHandlers);
        }
    }

    private void applyApiHandlers(AnnotatedElement element, ApiDoc apiDoc, Annotation annotation, Iterable<ApiAnnotationHandler> apiHandlers) {
        for (ApiAnnotationHandler apiHandler : apiHandlers) {
            if (apiHandler.canHandle(element, annotation)) {
                apiHandler.handle(element, apiDoc);
            }
        }
    }

    private void applyApiMethodHandlers(AnnotatedElement element, ApiMethodDoc apiMethodDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiMethodHandlers(element, apiMethodDoc, annotation, customApiMethodHandlers);
        }
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiMethodHandlers(element, apiMethodDoc, annotation, apiMethodHandlers);
        }
    }

    private void applyApiMethodHandlers(AnnotatedElement element,
                                                ApiMethodDoc apiMethodDoc,
                                                Annotation annotation,
                                                Iterable<ApiMethodAnnotationHandler> apiMethodHandlers) {
        for (ApiMethodAnnotationHandler handler : apiMethodHandlers) {
            if (handler.canHandle(element, annotation)) {
                handler.handle(element, apiMethodDoc);
            }
        }
    }

    private void applyApiParamHandlers(AnnotatedElement element, ApiParamDoc apiParamDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiParamHandlers(element, apiParamDoc, annotation, customApiParamHandlers);
        }
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiParamHandlers(element, apiParamDoc, annotation, apiParamHandlers);
        }
    }

    private void applyApiParamHandlers(AnnotatedElement element,
                                               ApiParamDoc apiParamDoc,
                                                Annotation annotation,
                                                Iterable<ApiParamAnnotationHandler> apiMethodHandlers) {
        for (ApiParamAnnotationHandler handler : apiMethodHandlers) {
            if (handler.canHandle(element, annotation)) {
                handler.handle(element, apiParamDoc);
            }
        }
    }


    private void applyApiObjectHandlers(AnnotatedElement element, ApiObjectDoc apiObjectDoc) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiObjectHandlers(element, apiObjectDoc, annotation, customApiObjectHandlers);
        }
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            applyApiObjectHandlers(element, apiObjectDoc, annotation, apiObjectHandlers);
        }
    }

    private void applyApiObjectHandlers(AnnotatedElement element,
                                                ApiObjectDoc apiObjectDoc,
                                                Annotation annotation,
                                                Iterable<ApiObjectAnnotationHandler> apiObjectHandlers) {
        for (ApiObjectAnnotationHandler apiHandler : apiObjectHandlers) {
            if (apiHandler.canHandle(element, annotation)) {
                apiHandler.handle(element, apiObjectDoc);
            }
        }
    }
}
