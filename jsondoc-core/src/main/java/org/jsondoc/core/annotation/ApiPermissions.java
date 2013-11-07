package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Daniel Ostermeier
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiPermissions {

    ApiPermission[] permissions() default {};

}
