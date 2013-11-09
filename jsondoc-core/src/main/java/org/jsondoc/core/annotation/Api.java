package org.jsondoc.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.jsondoc.core.pojo.ApiStatus.UNDEFINED;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jsondoc.core.pojo.ApiStatus;

/**
 * This annotation is to be used on your "service" class, for example controller classes in Spring MVC.
 *
 * @author Fabio Maffioletti
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Api {

    /**
     * Specify the name of the API. If not specified, it will be inferred from the type
     * being annotated.
     *
     * @return the name of the API.
     */
    String name() default "";

	/**
     * Specify a description of the API.  If not specified, it will be inferred from the
     * type being annotated.
     *
	 * @return a description of what the API does.
	 */
	String description() default "";

    ApiStatus status() default UNDEFINED;
}
