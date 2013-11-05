package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Daniel Ostermeier
 */
@Documented
@Target(value= {TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface ApiVersion {

    int UNDEFINED = -1;

    int since() default 0;

    int until() default UNDEFINED;
}
