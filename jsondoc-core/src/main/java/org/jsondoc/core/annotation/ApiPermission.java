package org.jsondoc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Daniel Ostermeier
 */
@Documented
@Target(value= ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPermission {

    String name();

    String description() default "";
}
