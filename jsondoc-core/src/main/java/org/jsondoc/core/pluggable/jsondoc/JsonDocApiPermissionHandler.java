package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiPermission;
import org.jsondoc.core.annotation.ApiPermissions;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiPermissionDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiPermissionHandler  implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Method && annotation instanceof ApiPermissions;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiPermissions permissionsAnnotation = element.getAnnotation(ApiPermissions.class);
        for (ApiPermission permissionAnnotation : permissionsAnnotation.permissions()) {
            // TODO: take care of duplicates.
            ApiPermissionDoc permissionDoc = doc.getPermission(permissionAnnotation.name());
            if (permissionDoc == null) {
                permissionDoc = new ApiPermissionDoc();
                permissionDoc.setName(permissionAnnotation.name());
                doc.getPermissions().add(permissionDoc);
            }
            if (hasText(permissionAnnotation.description())) {
                permissionDoc.setDescription(permissionAnnotation.description());
            }
        }
    }
}

