package org.jsondoc.ebuddy.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.jsondoc.core.pluggable.ApiObjectAnnotationHandler;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
import org.jsondoc.ebuddy.annotation.EbuddyApiObjectOptions;

/**
 * This object defines the handler for the {@link EbuddyApiObjectOptions} annotation.
 *
 * @author Daniel Ostermeier
 */
public class EbuddyApiObjectOptionsHandler implements ApiObjectAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Class && annotation instanceof EbuddyApiObjectOptions;
    }

    @Override
    public void handle(AnnotatedElement element, ApiObjectDoc doc) {
        Class<?> clazz = (Class<?>)element;
        EbuddyApiObjectOptions options = element.getAnnotation(EbuddyApiObjectOptions.class);
        if (options.locallyDefined()) {
            handleLocallyDefined(clazz, doc);
        }
    }

    /**
     * Handle the processing of {@link EbuddyApiObjectOptions#locallyDefined()} being true.
     *
     * @param clazz the class that defines the set of properties to be retained.
     * @param doc   the documentation model to be updated.
     */
    private void handleLocallyDefined(Class<?> clazz, ApiObjectDoc doc) {

        Collection<String> namesToRetain = new HashSet<String>();

        // Gather the names of the declared properties on our object.
        for (Field field : clazz.getDeclaredFields()) {
            namesToRetain.add(getPropertyName(field));
        }
        for (Method method : clazz.getDeclaredMethods()) {
            namesToRetain.add(getPropertyName(method));
        }

        // Remove all those properties that are currently defined within
        // the documentation that are not part of the declared properties.
        Iterable<ApiObjectPropertyDoc> fields = new ArrayList<ApiObjectPropertyDoc>(doc.getFields());
        for (ApiObjectPropertyDoc field : fields) {
            if (!namesToRetain.contains(field.getActualName())) {
                doc.getFields().remove(field);
            }
        }
    }

    private String getPropertyName(Member member) {
        String propertyName;
        if (member.getName().startsWith("get")) {
            propertyName = member.getName().substring(3);
        } else if (member.getName().startsWith("set")) {
            propertyName = member.getName().substring(3);
        } else if (member.getName().startsWith("is")) {
            propertyName = member.getName().substring(2);
        } else {
            propertyName = member.getName();
        }
        propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
        return propertyName;
    }
}
